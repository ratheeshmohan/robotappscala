package com.robot.core

import org.scalacheck.Gen
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import scalaz.\/

object RobotPBSpec extends Properties("RobotActions") {

  val positiveIntegers = Gen.choose(1, 1000)

  val defaultRobot = Board(5, 5).flatMap(Robot().assign).flatMap(r =>
    r.place(Coordinates(Point(1, 1), Direction.North))).
    foldRight(Robot())((a, z) => a)

  property("Move() should move itself one unit without changing the direction") = forAll(robots(2)) {
    (r: Robot) => {
      r.move().foldRight(true)((a, z) => {
        for {
          c <- r.coordinate
          c1 <- a.coordinate
        } yield c.direction == c1.direction &&
          (Math.abs(c.point.x - c1.point.x) == 1) ||
          (Math.abs(c.point.y - c1.point.y) == 1)
      }.getOrElse(false) && z)
    }
  }

  property("Move() robot from x to y then y to x should bring robot back to x") = forAll(robots(1)) {
    (r: Robot) => {
      for {
        r1 <- r.move()
        r2 <- r1.turnRight(Degrees.OneEighty)
        r3 <- r2.move()
        r4 <- r3.turnRight(Degrees.OneEighty)
      } yield {
        r.coordinate.flatMap { case c1 => for {c2 <- r4.coordinate
        } yield c1 == c2
        }
      }.getOrElse(false)
    }.foldRight(true)((a, z) => a && z)
  }

  property("Move() robot from x -> y -> z & z -> y -> x should have similar start/end coordinates ") = forAll(robots(2)) {
    (r: Robot) => {
      for {
        r1 <- r.move()
        r2 <- r1.move()
        s <- r2.turnRight(Degrees.OneEighty)
        s1 <- s.move()
        s2 <- s1.move()
        s3 <- s2.turnRight(Degrees.OneEighty)
      } yield {
        for {
          rc1 <- r.coordinate
          sc1 <- s3.coordinate
        } yield rc1 == sc1
      } getOrElse false
    }.foldRight(true)((a, z) => a && z)
  }

  property("Cyclic movement(+) should bring back robot to original position") = forAll(robots(2)) {
    (r: Robot) => testcyclicmovement(r, _.turnRight(Degrees.Ninety))
  }

  property("Cyclic movement(-) should bring back robot to original position") = forAll(robots(2)) {
    (r: Robot) => testcyclicmovement(r, _.turnLeft(Degrees.Ninety))
  }

  property("Outward Move() of robot placed on borders should be ignored") = forAll { (d: Int) => {
    testinvalidbordermoves(d, Point(d - 1, d - 1), Direction.North) &&
      testinvalidbordermoves(d, Point(d - 1, d - 1), Direction.East) &&
      testinvalidbordermoves(d, Point(0, 0), Direction.South) &&
      testinvalidbordermoves(d, Point(0, 0), Direction.West) &&
      testinvalidbordermoves(d, Point(0, d - 1), Direction.East) &&
      testinvalidbordermoves(d, Point(0, d - 1), Direction.South) &&
      testinvalidbordermoves(d, Point(d - 1, 0), Direction.North) &&
      testinvalidbordermoves(d, Point(d - 1, 0), Direction.West)
  }
  }

  //todo: make the cycle length random
  private def testcyclicmovement(r: Robot, turnF: Robot => \/[String, Robot]) = (for {
    r1 <- r.move()
    r2 <- turnF(r1)
    r3 <- r2.move()
    r4 <- turnF(r3)
    r5 <- r4.move()
    r6 <- turnF(r5)
    r7 <- r6.move()
    r8 <- turnF(r7)
  } yield {
    for {
      c <- r.coordinate
      c8 <- r8.coordinate
    } yield c == c8
  }.getOrElse(false)).foldRight(true)((a, z) => a && z)

  private def testinvalidbordermoves(n: Int, p: Point, d: Direction.Value): Boolean = (for {
    b <- Board(n, n)
    r <- Robot().assign(b)
    r1 <- r.place(Coordinates(Point(n - 1, n - 1), Direction.East))
    r2 <- r1.move()
  } yield r1.coordinate == r2.coordinate).foldRight(true)((a, z) => a && z)

  private def robots(posOffset: Int): Gen[Robot] = for {
    c <- Gen.choose(1, 100)
    x <- Gen.choose(posOffset, c - posOffset - 1)
    y <- Gen.choose(posOffset, c - posOffset - 1)
    d <- Gen.choose(0, 3)
  } yield Board(c, c).flatMap(Robot().assign).flatMap(r =>
    r.place(Coordinates(Point(x, y), Direction(d)))).
    fold(x => defaultRobot, x => x)
}
