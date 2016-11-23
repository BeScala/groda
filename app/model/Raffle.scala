package model

import model.Raffle.Outcome

/**
  * Created by Rutger Claes <rutger.claes@cegeka.be> on 23/11/16.
  */

sealed trait Raffle {

  def name:String

  def people:Set[String]

}

object Raffle {

  type Outcome = (String,String)

  def empty( name:String, prizes:List[String] ) = EmptyRaffle( name, prizes )

}

case class EmptyRaffle( name:String, prizes:List[String] ) extends Raffle {

  def addPerson( person:String ) = DraftRaffle( name, prizes, Set( person ) )

  override def people = Set.empty

}

case class DraftRaffle( name:String, prizes:List[String], people:Set[String] ) extends Raffle {

  def addPerson( name:String ): Raffle = {
    val incPeople = people + name
    if (incPeople.size >= prizes.size)
      ReadyToRunRaffle(name, prizes, incPeople)
    else
      copy(people = incPeople)
  }

}

case class ReadyToRunRaffle( name:String, prizes:List[String], people:Set[String] ) extends Raffle {

  require( prizes.size >= people.size )

  def addPerson( name:String ) = copy( people = people + name )

  def run:(Outcome,Raffle) = ???

  def finish:FinishedRaffle = ???

}

case class FinishedRaffle( name:String, prizes:List[String], people:Set[String], outcome:List[Outcome] ) extends Raffle