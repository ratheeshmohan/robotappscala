package com.auz.robotappscala

case class StringCommandParser(commandLines:String) {

  def commands: Seq[Command[Robot]] = commandLines.split("\n").map(toCommand)

  private val msgPattern = "(PLACE){1} ([0-9]+),([0-9]+),([A-Za-z]+)".r

  def toCommand(cmd: String): Command[Robot] = cmd.trim.toUpperCase match {
    case "MOVE" => MoveCommand
    case "LEFT" => TurnLeftCommand
    case "RIGHT" => TurnRightCommand
    case "REPORT" => new ReportCommand((x, y, z) => println(s"$x,$y,$z"))
    case msgPattern(place, x, y, d) => {
      val direction: Option[Direction.Value] = d
      direction.map(dir => new PlaceCommand(Coordinates(Point(x.toInt, y.toInt), dir))).
        getOrElse(NullCommand)
    }
    case _ => NullCommand
  }
}
