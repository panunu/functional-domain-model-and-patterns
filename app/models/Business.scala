package models

case class Address(streetAddress: String)

case class CustomerIdentifier(id: String)

// Is there a better way for sum types or is this good enough?
sealed trait Customer {
  val id: CustomerIdentifier
}
case class CustomerWithoutAddress(id: CustomerIdentifier) extends Customer
case class CustomerWithAddress(id: CustomerIdentifier, address: Address) extends Customer

case class Username(username: String)
case class Account(username: Username, password: String)

case class ProductCode(id: String)
case class Product(productCode: ProductCode, name: String)

sealed trait Subscription {
  val customer: Customer
  val product: Product
  val quantity: Int
}
case class UnpaidSubscription(customer: Customer, product: Product, quantity: Int) extends Subscription
case class PaidSubscription(customer: Customer, product: Product, quantity: Int, payment: Payment) extends Subscription

case class Payment(method: PaymentMethod)

sealed trait PaymentMethod {
  val number: String
}
case class Visa(number: String) extends PaymentMethod
case class MasterCard(number: String) extends PaymentMethod

object Business {
  type CreateAccount = (Username) => Option[Account]
  type CreateCustomer = (CustomerIdentifier, Option[Address]) => Option[Customer]
  type Register = CreateAccount => CreateCustomer

  // Higher level, process-related inputs.
  /*case class RegistrationInput()*/

  // Functional composition? Kleislilosowski? RWO?
  /*def register(username: String, customerIdentifier: String, address: String) = {
    // Bind pattern?
    createAccount(Username(username)) match {
      case Some(account: Account) => createCustomer(CustomerIdentifier(customerIdentifier), Some(Address(address)))
      case None => false
    }
  }*/
}