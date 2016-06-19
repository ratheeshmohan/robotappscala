package com.auz.robotappscala

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen}

object BoardSpec extends Properties("Board") {

  val positiveIntegers = Gen.choose(1, 1000)
  val integers = Arbitrary.arbitrary[Int]

  property("create with valid rows and column") = forAll { (a: Int, b: Int) =>
    if (a <= 0 || b <= 0) Board(a, b).isLeft else Board(a, b).isRight
  }

  property("must contains valid points") = forAll(positiveIntegers,
    positiveIntegers, integers, integers) { (r: Int, c: Int, x: Int, y: Int) =>
    if ((x >= 0 && x < c) && (y >= 0 && y < r))
      Board(r, c).map(_.contains(Point(x, y))).getOrElse(false)
    else
      Board(r, c).map(!_.contains(Point(x, y))).getOrElse(false)
  }

  property("must return valid points") = forAll(positiveIntegers,
    positiveIntegers, integers, integers) { (r: Int, c: Int, x: Int, y: Int) =>
    if ((x >= 0 && x < c) && (y >= 0 && y < r))
      Board(r, c).foldRight(true)((a, z) => a.getAt(x, y).isDefined && z)
    else
      Board(r, c).foldRight(true)((a, z) => a.getAt(x, y).isEmpty && z)
  }
}

