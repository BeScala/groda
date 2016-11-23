package model

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

/**
  * Created by Rutger Claes <rutger.claes@cegeka.be> on 23/11/16.
  */
class EmptyRaffle$Test extends FlatSpec with Matchers {

  def randomName = Random.nextString( 5 )

  def randomPrices = ( 1 to Random.nextInt( 10 ) + 1 ).map( i => Random.nextString( 5 ) ).toList

  behavior of "EmptyRaffle"

  it should "be open to new people" in {
    val empty = EmptyRaffle( randomName, randomPrices )
    val notEmpty = empty.addPerson( randomName )

    notEmpty shouldBe a[DraftRaffle]
  }

}