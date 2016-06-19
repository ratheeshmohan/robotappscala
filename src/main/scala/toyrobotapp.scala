package com.auz.robotappscala



object ToyRobotApp extends App {

  //Can be read from file/console
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
      REPORT"""

  Board(5, 5).flatMap(b => Robot().assign(b)).map(r =>
    CommandExecutor.execute[Robot](r, StringCommandParser(strCommands).commands))
}