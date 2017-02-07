object JsonSerializers {
  import play.api.libs.json._
  implicit val pieceWrites = new Writes[Piece[Color]] {
    def writes(p: Piece[Color]) = Json.obj(
      "type" -> p.toString,
      "color" -> p.color.toString
    )
  }

  implicit val positionWrites = new  Writes[Position] {
    def writes(p: Position) = Json.obj(
      "column"    -> p.col.toString,
      "row"    -> p.row.toString
    )
  }

  implicit val tileWrites = new  Writes[Tile] {
    def writes(tile: Tile) = Json.obj(
      "position" -> tile.pos,
      "piece" -> tile.p
    )
  }

  implicit val boardWrites =  new Writes[Board]  {
    def writes(board: Board) = Json.obj(
      "tiles" -> board.tiles
    )
  }
}



object State {
  import JsonSerializers._
  import play.api.libs.json._

  def toJson(b: Board): String = Json.toJson(b).toString()
}
