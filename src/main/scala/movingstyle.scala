package com.auz.robotappscala

import scalaz.\/

/**
  * A strategy for an elements moving style. Result of any  movement can be valid or invalid.
  */

abstract class MovingStyle[T] {

  import Degrees._

  def move(element: T): \/[String, T]

  def place(element: T, coordinates: Coordinates): \/[String, T]

  def turnRight(element: T, degrees: Degrees): \/[String, T]

  def turnLeft(element: T, degrees: Degrees): \/[String, T]
}


