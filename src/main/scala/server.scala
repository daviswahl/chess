import com.twitter.finagle.http.{HttpMuxer, Request, Response}
import com.twitter.finagle.Http
import com.twitter.finagle.Service
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.Method
import com.twitter.util.{Await, Future}
import scala.io.Source

class BoardHandler extends Service[Request, Response] {
  def apply(req: Request): Future[Response] = {
    val r = req.response
    r.setStatusCode(200)
    r.setContentTypeJson
    r.setContentString(State.toJson(new Board))
    Future.value(r)
  }
}

class IndexHandler() extends Service[Request, Response] {
  def apply(req: Request): Future[Response] = {
    val r = req.response
    r.setStatusCode(200)
    r.setContentString("""
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div id="board"></div>
<script src="assets/application.js"></script>
</body>
</html>
""")
    Future.value(r)
  }
}

class AssetHandler(val asset: String) extends Service[Request, Response] {
  def apply(req: Request): Future[Response] = {
    val r = req.response
    r.setStatusCode(200)
    val fileContents = Source.fromFile("assets/" + asset).mkString
    r.setContentString(fileContents)
    Future.value(r)
  }
}

object HttpRouter {
  def byRequest[REQUEST](routes: PartialFunction[Request, Service[REQUEST, Response]]) =
    new RoutingService(
      new PartialFunction[Request, Service[REQUEST, Response]] {
        def apply(request: Request)       = routes(request)
        def isDefinedAt(request: Request) = routes.isDefinedAt(request)
      })
}

object MyApp extends App {

  val routingService =
    HttpRouter.byRequest { request =>
      (request.method, Path(request.path)) match {
        case Method.Get -> Root                    => new IndexHandler()
        case Method.Get -> Root / "assets" / asset => new AssetHandler(asset)
        case Method.Get -> Root / "board"  => new BoardHandler()
      }
    }

  HttpMuxer.addRichHandler("/", routingService)

  val server = Http.serve("0.0.0.0:8000", HttpMuxer)
  Await.ready(server)
}
