package com.auz.robotappscala



object ToyRobotApp extends App {

  //Can be read from file/console
  /*
  val strCommands =
    """PLACE 0,0,NORTH
      MOVE
      REPORT
      PLACE 1,2,NORTH
      MOVE
      REPORT
      PLACE 0,0,NORTH
      LEFT
      REPORT
      PLACE 1,2,EAST
      MOVE
      MOVE
      LEFT
      MOVE
      REPORT"""*/
  val strCommands =
    """MOVE
      REPORT
      PLACE -1,2,NORTH
      MOVE
      REPORT
      PLACE 0,0,NORTH
      RIGHT
      REPORT
      PLACE 1,2,EAST
      MOVE
      MOVE
      LEFT
      MOVE
      REPORT"""
  executeCommand(Robot(), (5, 5), strCommands)

  def executeCommand(robot: Robot, board: (Int, Int), command: String) = {
    Board(board._1, board._2).flatMap(b => robot.assign(b)).map(r =>
      CommandExecutor.execute[Robot](r, StringCommandParser(command).commands))
  }
}