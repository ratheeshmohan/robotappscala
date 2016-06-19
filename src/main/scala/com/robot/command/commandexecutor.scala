package com.robot.command

object  CommandExecutor {
  def execute[T](item: T,
                 commands: Seq[Command[T]]) =
    commands.foldLeft(item)((z, c) => c.execute(z))
}
