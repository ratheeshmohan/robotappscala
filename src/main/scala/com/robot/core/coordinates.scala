package com.robot.core

/** class defining a point */
case class Point(x:Int,y:Int) {
  def addX(c: Int) = Point(x + c, y)

  def addY(c: Int) = Point(x, y + c)
}

/** class defining a coordinates in the board */

case class Coordinates (point:Point,direction:Direction.Value) {

  import Degrees._
  import Direction._
  //returns coordinates at x unit away from in same direction
  def atUnits(x: Int) = direction match {
    case North => Option(copy(point.addY(x)))
    case South => Option(copy(point.addY(-x)))
    case East => Option(copy(point.addX(x)))
    case West => Option(copy(point.addX(-x)))
    case _ => None
  }

  def atClockWise(degree: Degrees) = Option(copy(direction = direction.rightAt(degree)))

  def atAntiClockWise(degree: Degrees) = Option(copy(direction = direction.leftAt(degree)))
}

