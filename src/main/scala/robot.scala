package com.auz.robotappscala

import scalaz.\/


/**
  * class representing Robot, intially this will be in unassigned state.
  * This class is expecting a strategy to handle movement of robot
  */

 case class Robot(val board:Option[Board] = None,
                 val coordinate:Option[Coordinates] = None,
                 val motionStrategy: MovingStyle[Robot] = new SimpleMove) {

  import Degrees._

  def this() = this(None, None)

  def assign(board: Board) = \/.right[String,Robot](this.copy(Some(board)))

  def unAssign() = \/.right[String,Robot](Robot())

  def place(coordinates: Coordinates) = motionStrategy.place(this, coordinates)

  def move() = motionStrategy.move(this)

  def turnRight(degrees: Degrees) = motionStrategy.turnRight(this, degrees)

  def turnLeft(degrees: Degrees) = motionStrategy.turnLeft(this, degrees)

  def assigned = board.isEmpty

  def placed = coordinate.isEmpty
}



