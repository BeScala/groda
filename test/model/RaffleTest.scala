package model

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

/**
  * Created by Rutger Claes <rutger.claes@cegeka.be> on 23/11/16.
  */
trait RaffleBehavior { this}

class RaffleTest extends FlatSpec with Matchers {

  def randomName = Random.nextString( 5 )

  def randomPrices = ( 1 to Random.nextInt( 10 ) + 1 ).map( i => Random.nextString( 5 ) ).toList

  behavior of "EmptyRaffle"

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

  behavior of "DraftRaffle"

  it should "not become ready when not full" in {
    val draft = DraftRaffle( randomName, List( "a", "b", "c" ), Set( randomName ) )
    val result = draft.addPerson( randomName )

    result shouldBe a[DraftRaffle]
  }

  it should "actually add the person when not full" in {
    val draft = DraftRaffle( randomName, List( "a", "b", "c" ), Set( randomName ) )
    val secondName = randomName
    val result = draft.addPerson( secondName )

    result.people shouldBe draft.people + secondName
  }

  it should "preserve the name when not full" // TODO

  it should "preserve the prizes when not full" // TODO

  it should "become ready when full" in {
    val draft = DraftRaffle( randomName, List( "a", "b" ), Set( randomName ) )
    val result = draft.addPerson( randomName )

    result shouldBe a[ReadyToRunRaffle]
  }

  it should "actually add the person when full" in {
    val draft = DraftRaffle( randomName, List( "a", "b" ), Set( randomName ) )
    val secondName = randomName
    val result = draft.addPerson( secondName )

    result.people shouldBe draft.people + secondName
  }

  it should "preserve the name when full" // TODO

  it should "preserve the prizes when full" // TODO

}