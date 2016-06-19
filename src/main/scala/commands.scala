package com.auz.robotappscala

import scalaz.\/

/*** Trait for executing the commands on any item
  * All the commands will execute specific actions on a item. If the action results in an error,
  * it returns the original item unchanged
  **/
sealed trait Command[T] {
  def execute(item: T): T
}

object Command {
  //execute a command and returns the change element, in case of error returns original element unchanged
  def execute[T](t: T, f: T => \/[String, T]): T = f(t).getOrElse(t)
}

object MoveCommand extends Command[Robot] {
  def execute(robot: Robot): Robot = Command.execute[Robot](robot, _.move)
}

object TurnLeftCommand extends Command[Robot] {
  def execute(robot: Robot): Robot = Command.execute[Robot](robot, _.turnLeft(Degrees.Ninety))
}

object TurnRightCommand extends Command[Robot] {
  def execute(robot: Robot): Robot = Command.execute[Robot](robot, _.turnRight(Degrees.Ninety))
}

object NullCommand extends Command[Robot] {
  def execute(robot: Robot): Robot = Command.execute[Robot](robot, \/.right[String,Robot](_))
}

class PlaceCommand(val cord:Coordinates) extends Command[Robot] {
  def execute(robot: Robot): Robot = Command.execute[Robot](robot, _.place(cord))
}

class ReportCommand(val f:(Int,Int,Direction.Value)=>Unit) extends Command[Robot] {
  def execute(robot: Robot): Robot = Command.execute[Robot](robot, r => {
    for {
      c <- robot.coordinate
    } f(c.point.x, c.point.y, c.direction)
    \/.right[String,Robot](r)
  })
}

