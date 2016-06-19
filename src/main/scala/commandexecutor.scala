package com.auz.robotappscala

object  CommandExecutor {
  def execute[T](item: T,
                 commands: Seq[Command[T]]) =
    commands.foldLeft(item)((z, c) => c.execute(z))
}
