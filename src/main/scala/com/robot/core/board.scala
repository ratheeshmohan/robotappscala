package com.robot.core

import scalaz.\/

/**
  * Board having rows & columns
  **/
final class Board private(val rows:Int,val columns:Int) {

  def contains(p: Point): Boolean = p match {
    case Point(x, y) => !(x < 0 || x > columns - 1 || y < 0 || y > rows - 1)
  }

  def getAt(x: Int, y: Int): Option[Point] = {
    val p = Point(x, y)
    if (contains(p)) Some(p) else None

  }

  override def equals(o: Any) = o match {
    case that: Board => that.rows == this.rows && that.columns == this.columns
    case _ => false
  }

  override def hashCode = 41 * (41 + rows) + columns
}

object Board {

  def apply(rows: Int, columns: Int): \/[String, Board] =
    if (rows > 0 && columns > 0) \/.right[String, Board](new Board(rows, columns))
    else \/.left[String, Board]("Rows/Columns should be >0")
}

