package model

import model.Raffle.Outcome

/**
  * Created by Rutger Claes <rutger.claes@cegeka.be> on 23/11/16.
  */

sealed trait Raffle {

  def name:String

  def prizes:List[String]

}

object Raffle {

  // winner name, price name
  type Outcome = (String,String)

  def empty( name:String, prizes:List[String] ) = EmptyRaffle( name, prizes )

}

case class EmptyRaffle( name:String, prizes:List[String] ) extends Raffle {

  def addPerson( person:String ):RaffleWithPeople = DraftRaffle( name, prizes, Set( person ) )

}

trait RaffleWithPeople extends Raffle {

  def people:Set[String]

  def addPerson( name:String ): RaffleWithPeople = {
    val incPeople = people + name
    if (incPeople.size >= prizes.size)
      ReadyToRunRaffle(name, prizes, incPeople)
    else
      withPeople( incPeople )
  }

  def withPeople( people:Set[String] ): RaffleWithPeople
}

case class DraftRaffle( name:String, prizes:List[String], people:Set[String] ) extends RaffleWithPeople {

  override def withPeople(people: Set[String]): RaffleWithPeople = copy( people = people )

}

case class ReadyToRunRaffle( name:String, prizes:List[String], people:Set[String] ) extends RaffleWithPeople {

  require( prizes.size >= people.size )

  override def withPeople(people: Set[String]): RaffleWithPeople = copy( people = people )

  private def choosePerson:String = people.toList.head // FIXME

  def run:RunningRaffle = RunningRaffle( name, prizes, people, List.empty )

  def finish:FinishedRaffle = ???

}

case class RunningRaffle( name:String, prizes:List[String], people:Set[String], outcome:List[Outcome] ) extends Raffle {

  def leftOverPrices = ???

  def run:RunningRaffle = ???

}

case class FinishedRaffle( name:String, prizes:List[String], people:Set[String], outcome:List[Outcome] ) extends Raffle