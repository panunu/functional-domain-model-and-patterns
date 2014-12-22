package services

import models._

class AccountService {
  def register(username: Username, id: CustomerIdentifier, address: Option[Address]) = {
    for {
      account <- createAccount(username)
      customer <- createCustomer(id, address)
    } yield (account, customer)
  }

  def createAccount: Business.CreateAccount = (username) => {
    Some(
      Account(
        username,
        "generatedPassword"
      )
    )
  }

  def createCustomer: Business.CreateCustomer = (id, address) => {
    Some(
      address match {
        case Some(address: Address) => CustomerWithAddress(id, address)
        case None => CustomerWithoutAddress(id)
      }
    )
  }
}
