package model

import model.Raffle.Outcome

import scala.util.Random

/**
  * Created by Rutger Claes <rutger.claes@cegeka.be> on 23/11/16.
  */

sealed trait Raffle {

  def name:String

  def prizes:List[String]

}

object Raffle {

  // winner name, prize name
  type Outcome = (String,String)

}

case class EmptyRaffle( name:String, prizes:List[String] ) extends Raffle {

  def addPerson( person:String ):RaffleWithPeople = DraftRaffle( name, prizes, Set( person ) )

}

trait RaffleWithPeople extends Raffle {

  def people:Set[String]

  def addPerson( name:String ): RaffleWithPeople

}

case class DraftRaffle private[model]( name:String, prizes:List[String], people:Set[String] ) extends RaffleWithPeople {

  require( prizes.size > people.size )

  override def addPerson(name: String): RaffleWithPeople = {
    val incPeople = people + name
    if (incPeople.size >= prizes.size )
      ReadyToRunRaffle(name, prizes, incPeople)
    else
      copy( people = incPeople )
  }
}

case class ReadyToRunRaffle private[model]( name:String, prizes:List[String], people:Set[String] ) extends RaffleWithPeople {

  require( prizes.size <= people.size )

  override def addPerson(name: String): RaffleWithPeople = copy( people = people + name )

  def finish:FinishedRaffle = {
    def run( people:Set[String], prizes:List[String], outcome:List[Outcome]  ):FinishedRaffle =
      prizes match {
      case Nil => FinishedRaffle( name, outcome )
      case _ => {
        val winner:String = people.toList( Random.nextInt( people.size ) )
        val prize = prizes.head

        run( people - winner, prizes.tail, ( winner, prize ) :: outcome )
      }

    }

    run( people, prizes, List.empty )
  }

}

case class FinishedRaffle private[model]( name:String, outcome:List[Outcome] ) extends Raffle {

  override def prizes: List[String] = List.empty

}