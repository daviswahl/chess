sealed trait Column extends Ordered[Column] {
  def value: Int
  def compare(that: Column): Int = this.value compare that.value
  def +(that: Column): Option[Column] = Column.fromValue(that.value + this.value)

}
case object A extends Column { def value = 1 }
case object B extends Column { def value = 2 }
case object C extends Column { def value = 3 }
case object D extends Column { def value = 4 }
case object E extends Column { def value = 5 }
case object F extends Column { def value = 6 }
case object G extends Column { def value = 7 }
case object H extends Column { def value = 8 }

object Column {
  def fromValue(v: Int): Option[Column] = v match {
    case 1 => Some(A)
    case 2 => Some(B)
    case 3 => Some(C)
    case 4 => Some(D)
    case 5 => Some(E)
    case 6 => Some(F)
    case 7 => Some(G)
    case 8 => Some(H)
    case _ => None
  }
}

sealed trait Row extends Ordered[Row] {
  def value: Int
  def compare(that: Row): Int = this.value compare that.value
  def +(that: Row): Option[Row] = Row.fromValue(that.value + this.value)
}

case object Eight extends Row { def value = 8 }
case object Seven extends Row { def value = 7 }
case object Six   extends Row { def value = 6 }
case object Five  extends Row { def value = 5 }
case object Four  extends Row { def value = 4 }
case object Three extends Row { def value = 3 }
case object Two   extends Row { def value = 2 }
case object One   extends Row { def value = 1 }

object Row {
  def fromValue(v: Int): Option[Row] = v match {
    case 1 => Some(One)
    case 2 => Some(Two)
    case 3 => Some(Three)
    case 4 => Some(Four)
    case 5 => Some(Five)
    case 6 => Some(Six)
    case 7 => Some(Seven)
    case 8 => Some(Eight)
    case _ => None
  }
}
