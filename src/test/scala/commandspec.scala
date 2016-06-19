package com.auz.robotappscala

import org.scalatest.{FlatSpec, Matchers}

class CommandSpec extends FlatSpec with Matchers {

  val defaultRobot = Board(5, 5).flatMap(Robot().assign).flatMap(r =>
    r.place(Coordinates(Point(1, 1), Direction.North))).
    foldRight(Robot())((a, z) => a)

  val defaultUnplacedRobot = Board(5, 5).flatMap(Robot().assign).foldRight(Robot())((a, z) => a)


  "MoveCommand" should "return valid Robot after moving" in {
    MoveCommand.execute(defaultRobot) ne (null)
  }

  "PlaceCommand" should "place the robot on the board" in {
    val c = Coordinates(Point(1, 1), Direction.North)
    new PlaceCommand(c).execute(defaultUnplacedRobot).coordinate should ===(Some(Coordinates(Point(1, 1), Direction.North)))
  }

  "TurnLeftCommand" should "turn the robot on the board" in {
    TurnLeftCommand.execute(defaultRobot).coordinate should ===(Some(Coordinates(Point(1, 1), Direction.West)))
  }

  "TurnRightCommand" should "turn the robot on the board" in {
    TurnRightCommand.execute(defaultRobot).coordinate should ===(Some(Coordinates(Point(1, 1), Direction.East)))
  }

  "NullCommand" should "not perform any action on the passed Robot" in {
    NullCommand.execute(defaultRobot).coordinate should ===(defaultRobot.coordinate)
  }
}