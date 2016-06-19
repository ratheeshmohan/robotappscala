package com.auz.robotappscala

import scalaz.\/

sealed class SimpleMove extends MovingStyle[Robot] {

  import Degrees._

  override def move(robot: Robot): \/[String, Robot] = {
    for {
      cord <- robot.coordinate
      newCord <- cord.atUnits(1)
    } yield place(robot, newCord)
  }.getOrElse(\/.left[String, Robot](""))

  override def place(robot: Robot, coordinates: Coordinates): \/[String, Robot] = act(robot,
    r => for {
      brd <- r.board
      p <- brd.getAt(coordinates.point.x, coordinates.point.y)
    } yield r.copy(coordinate = Some(coordinates)))

  override def turnRight(robot: Robot, degrees: Degrees): \/[String, Robot] = act(robot,
    turnBy(_, degrees)((c, d) => c.atClockWise(d)))


  override def turnLeft(robot: Robot, degrees: Degrees): \/[String, Robot] = act(robot,
    turnBy(_, degrees)((c, d) => c.atAntiClockWise(d)))


  private def turnBy(robot: Robot, degree: Degrees)
                    (f: (Coordinates, Degrees) => Option[Coordinates]): Option[Robot] = {
    for {
      c <- robot.coordinate
    } yield robot.copy(coordinate = f(c, degree))
  }

  private def act(robot: Robot, f: Robot => Option[Robot]): \/[String, Robot] = f(robot) match {
    case Some(r) => \/.right[String, Robot](r)
    case None => \/.left[String, Robot]("Invalid move")
  }
}

