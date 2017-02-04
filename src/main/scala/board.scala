case class Position(val row: Row, val col: Column) {
  override def toString = (row, col).toString
}

sealed trait Direction
case object Up extends Direction
case object R extends Direction
case object Down extends Direction
case object L extends Direction

sealed trait Projection[Direction]
case class Diagonal(lat: Direction, long: Direction) extends Projection[Direction]
case class Lateral(d: Direction) extends Projection[Direction]
case class Longitudinal(d: Direction) extends Projection[Direction]

class Tile(val pos: Position, val p: Option[Piece[Color]]) {
  override def toString = (pos, p getOrElse "Empty").toString
}

class Board {
  val rows =  List(One, Two, Three, Four, Five, Six, Seven, Eight)
  val cols =  List(A, B, C, D, E, F, G, H)

  val tiles = for {
    c <- cols
    r <- rows
  } yield { Position(r, c) match {
             // White
             case p @ Position(One, A | H) => new Tile(p, Some(new Rook(White)))
             case p @ Position(One, B | G) => new Tile(p, Some(new Knight(White)))
             case p @ Position(One, C | F) => new Tile(p, Some(new Bishop(White)))
             case p @ Position(One, D)     => new Tile(p, Some(new Queen(White)))
             case p @ Position(One, E)     => new Tile(p, Some(new King(White)))
             case p @ Position(Two, _)     => new Tile(p, Some(new Pawn(White)))

             // Black
             case p @ Position(Seven, _)     => new Tile(p, Some(new Pawn(Black)))
             case p @ Position(Eight, A | H) => new Tile(p, Some(new Rook(Black)))
             case p @ Position(Eight, B | G) => new Tile(p, Some(new Knight(Black)))
             case p @ Position(Eight, C | F) => new Tile(p, Some(new Bishop(Black)))
             case p @ Position(Eight, D)     => new Tile(p, Some(new King(Black)))
             case p @ Position(Eight, E)     => new Tile(p, Some(new Queen(Black)))

             // Empty
             case p @ Position(_, _) => new Tile(p, None)
           }
  }

  def row(r: Row): List[Tile] = tiles filter { t =>
    t.pos match {
      case Position(`r`, _) => true
      case _ => false
    }
  }

  def column(c: Column): List[Tile] = tiles filter { t =>
    t.pos match {
      case Position(_, `c`) => true
      case _ => false
    }
  }

  def tile(r: Row, c: Column): Tile = {
    println(r, c)
    val t = tiles filter { t: Tile =>
      t.pos match {
        case Position(`r`, `c`) => true
        case _ => false
      }
    }
    t.head
  }

  def project(p: Position, d: Longitudinal): List[Tile] = d match {
    case Longitudinal(Up)   => column(p.col) filter (_.pos.row > p.row)
    case Longitudinal(Down) => column(p.col) filter (_.pos.row < p.row)
    case _ => List()
  }

  def project(p: Position, d: Lateral): List[Tile] = d match {
    case Lateral(L)  => row(p.row) filter (_.pos.col < p.col)
    case Lateral(R) => row(p.row) filter (_.pos.col > p.col)
    case _ => List()
  }

  def project(p: Position, d: Diagonal): List[Tile] = d match {
    case Diagonal(x, y) => {
      val c = project(p, Lateral(y)) map (_.pos.col)
      val r = project(p, Longitudinal(x)) map (_.pos.row)
      r zip c map (t => tile(t._1, t._2))
    }
  }
}
