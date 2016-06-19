package com.robot.core

/** Enumeration containing 4 directions .*/
object Direction extends Enumeration {
  val North = Value(0)
  val East = Value(1)
  val South = Value(2)
  val West = Value(3)

  import Degrees._

  sealed class DirectionEx(val d: Direction.Value) {

    def rightAt(degree: Degrees) = at(degree)(_ + _)

    def leftAt(degree: Degrees) = at(degree)(_ - _)

    private def at(degree: Degrees)(f: (Int, Int) => Int) = degree match {
      case Zero => d
      case ThreeSixty => d
      case Ninety => directionAt(f(d.id, 1))
      case OneEighty => directionAt(f(d.id, 2))
      case TwoSeventy => directionAt(f(d.id, 3))
    }

    private def directionAt(n: Int): Direction.Value = if (n < 0) Direction((n + 4) % 4) else Direction(n % 4)
  }

  implicit def extendDirection(d: Direction.Value): DirectionEx = new DirectionEx(d)

  implicit def tryParseString2Direction(dir: String): Option[Direction.Value] = dir.toUpperCase match {
    case "NORTH" => Some(Direction.North)
    case "EAST" => Some(Direction.East)
    case "SOUTH" => Some(Direction.South)
    case "WEST" => Some(Direction.West)
    case _ => None
  }
}
