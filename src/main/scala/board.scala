sealed trait Column extends Ordered[Column] {
  def value: Int
  def compare(that: Column): Int = this.value compare that.value
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

case class Position(val row: Row, val col: Column) {
  override def toString = (row, col).toString
}

sealed trait Direction
case object Up extends Direction
case object UpRight extends Direction
case object Right extends Direction
case object DownRight extends Direction
case object Down extends Direction
case object DownLeft extends Direction
case object Left extends Direction
case object UpLeft extends Direction

sealed trait Projection[Direction]
case class Diagonal(d: Direction) extends Projection[Direction]
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
      case Position(_,_) => false
    }
  }

  def column(c: Column): List[Tile] = tiles filter { t =>
    t.pos match {
      case Position(_, `c`) => true
      case Position(_,_) => false
    }
  }

  def tile(r: Row, c: Column): Tile = {
    val t = tiles filter { t: Tile =>
      t.pos match {
        case Position(`r`, `c`) => true
        case _ => false
      }
    }
    t.head
  }

  def project(p: Position, d: Longitudinal): List[Tile] = d match {
    case Longitudinal(Up) => column(p.col) filter (_.pos.row > p.row)
    case Longitudinal(Down) => column(p.col) filter (_.pos.row < p.row)
    case Longitudinal(_) => List()
  }

  def project(p: Position, d: Lateral): List[Tile] = {
    List()
  }

  def project(p: Position, d: Diagonal): List[Tile] = {
    List()
  }
}
