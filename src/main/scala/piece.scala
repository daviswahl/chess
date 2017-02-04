class Move(val u: Int, val d: Int, val l: Int, val r: Int) {}

sealed trait Color{}
case object Black extends Color{}
case object White extends Color{}

sealed trait Piece[Color] {
  def moves(p: Position, b: Board): List[Move]
}

// All moves are just placeholders
case class Pawn(c: Color) extends Piece[Color] {
  def moves(p: Position, b: Board) = c match {
    case Black => List(new Move(-1, 0, 0, 0))
    case White => List(new Move(1, 0, 0, 0))
  }
}

case class Rook(c: Color) extends Piece[Color] {
  def moves(p: Position, b: Board) = c match {
    case Black => List(new Move(-1, 0, 0, 0))
    case White => List(new Move(1, 0, 0, 0))
  }
}

case class Knight(c: Color) extends Piece[Color] {
  def moves(p: Position, b: Board) = c match {
    case Black => List(new Move(-1, 0, 0, 0))
    case White => List(new Move(1, 0, 0, 0))
  }
}

case class Bishop(c: Color) extends Piece[Color] {
  def moves(p: Position, b: Board) = c match {
    case Black => List(new Move(-1, 0, 0, 0))
    case White => List(new Move(1, 0, 0, 0))
  }
}

case class King(c: Color) extends Piece[Color] {
  def moves(p: Position, b: Board) = c match {
    case Black => List(new Move(-1, 0, 0, 0))
    case White => List(new Move(1, 0, 0, 0))
  }
}

case class Queen(c: Color) extends Piece[Color] {
  def moves(p: Position, b: Board) = c match {
    case Black => List(new Move(-1, 0, 0, 0))
    case White => List(new Move(1, 0, 0, 0))
  }
}
