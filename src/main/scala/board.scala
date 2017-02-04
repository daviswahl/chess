sealed trait Column extends Ordered[Row] {
  def value: Int
  def compare(that: Row): Int = this.value compare that.value
}
case object A extends Column { def value = 1 }
case object B extends Column { def value = 2 }
case object C extends Column { def value = 3 }
case object D extends Column { def value = 4 }
case object E extends Column { def value = 5 }
case object F extends Column { def value = 6 }
case object G extends Column { def value = 7 }
case object H extends Column { def value = 8 }

sealed trait Row extends Ordered[Row] {
  def value: Int
  def compare(that: Row): Int = this.value compare that.value
}
case object Eight extends Row { def value = 8 }
case object Seven extends Row { def value = 7 }
case object Six   extends Row { def value = 6 }
case object Five  extends Row { def value = 5 }
case object Four  extends Row { def value = 4 }
case object Three extends Row { def value = 3 }
case object Two   extends Row { def value = 2 }
case object One   extends Row { def value = 1 }

case class Position(r: Row, c: Column) {
  override def toString = (r, c).toString
}

class Tile(val pos: Position, val p: Option[Piece[Color]]) {
  override def toString = (pos, p getOrElse "").toString
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
      case Position(x, _) if x == r => true
      case Position(_,_) => false
    }
  }

  override def toString = {
    { for {
      r <- rows
    } yield row(r) toString
    } toString
  }
}
