package com.auz.robotappscala

import org.scalatest.{FlatSpec, Matchers}

class CommandSpec extends FlatSpec with Matchers {

  val defaultCoordinate = Coordinates(Point(1, 1), Direction.North)
  val defaultBoard = Board(5, 5)
  val defaultUnplacedRobot = Board(5, 5).flatMap(Robot().assign).foldRight(Robot())((a, z) => a)
  val defaultRobot = defaultBoard.flatMap(Robot().assign).flatMap(r =>
    r.place(defaultCoordinate)).
    foldRight(Robot())((a, z) => a)

  "MoveCommand" should "returns a valid Robot after moving it" in {
    MoveCommand.execute(defaultRobot) ne (null)
  }

  "MoveCommand" should "not perform any action on the Robot" in {
    MoveCommand.execute(defaultUnplacedRobot).coordinate should ===(None)
    MoveCommand.execute(defaultUnplacedRobot).board should ===(defaultUnplacedRobot.board)
  }

  "PlaceCommand" should "place the robot on a board" in {
    val c = Coordinates(Point(1, 1), Direction.North)
    new PlaceCommand(c).execute(defaultUnplacedRobot).coordinate should ===(Some(defaultCoordinate))
  }

  "TurnLeftCommand" should "turn the robot left on the board" in {
    TurnLeftCommand.execute(defaultRobot).coordinate should ===(Some(
      Coordinates(defaultCoordinate.point, Direction.West)))
  }

  "TurnRightCommand" should "turn the robot right on the board" in {
    TurnRightCommand.execute(defaultRobot).coordinate should ===(Some(
      Coordinates(defaultCoordinate.point, Direction.East)))
  }

  "NullCommand" should "not perform any action on the Robot" in {
    NullCommand.execute(defaultRobot).coordinate should ===(defaultRobot.coordinate)
  }
}