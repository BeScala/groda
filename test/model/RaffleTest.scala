package model

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

/**
  * Created by Rutger Claes <rutger.claes@cegeka.be> on 23/11/16.
  */
class RaffleTest extends FlatSpec with Matchers {

  def randomName:String = Random.nextString( 5 )

  def randomName( size:Int ):List[String] = ( 0 until size ).map( _ => randomName ).toList

  def randomPrices = randomName( 1 + Random.nextInt( 10 ) )

  behavior of "Raffle"

  it should "not be runnable without sufficient people" in {
    val empty = EmptyRaffle( randomName, randomName( 3 ) )

    val one = empty.addPerson( randomName )
    one shouldNot be( a[ReadyToRunRaffle] )
    val two = one.addPerson( randomName )
    two shouldNot be( a[ReadyToRunRaffle] )
    val three = two.addPerson( randomName )

    three shouldBe a[ReadyToRunRaffle]
  }

  it should "produce an outcome" in {
    val raffle = EmptyRaffle( randomName, randomName( 3 ) )
      .addPerson( randomName )
      .addPerson( randomName )
      .addPerson( randomName )
      .addPerson( randomName )
      .addPerson( randomName )
      .addPerson( randomName )

    val finished =
      raffle match {
        case r:ReadyToRunRaffle => r.finish
        case _ => fail( "Should be ready to run" )
      }

    val (winners, prizes) = finished.outcome.unzip
    winners.toSet.size shouldBe prizes.size
    ( winners.toSet diff raffle.people ) shouldBe empty
    prizes.sorted shouldBe raffle.prizes.sorted

    finished.prizes shouldBe empty
  }

  it should "be open to new people" in {
    val empty = EmptyRaffle(randomName, randomPrices)
    val newName = randomName
    val notEmpty = empty.addPerson(newName)

    notEmpty shouldBe a[DraftRaffle]
  }

  it should "contain a new person after adding it" in {
    val empty = EmptyRaffle(randomName, randomPrices)
    val newName = randomName
    val notEmpty = empty.addPerson(newName)

    notEmpty.people shouldBe Set( newName )
  }

  it should "preserve name when adding person" in {
    val empty = EmptyRaffle( randomName, randomPrices )
    val notEmpty = empty.addPerson( randomName )

    notEmpty.name shouldBe empty.name
  }

  it should "preserve prices when adding person" in {
    val empty = EmptyRaffle( randomName, randomPrices )
    val notEmpty = empty.addPerson( randomName )

    notEmpty.prizes shouldBe empty.prizes
  }

}