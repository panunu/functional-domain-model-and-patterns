package models

case class Address(streetAddress: String)

// Is there a better way for sum types or is this good enough?
sealed trait Customer {
  val id: CustomerIdentifier
}
case class CustomerIdentifier(id: String)
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
  def createAccount(username: Username): Option[Account] = {
    Some(Account(username, "generatedPassword"))
  }

  def createCustomer(id: CustomerIdentifier, address: Option[Address]): Option[Customer] = {
    Some(
      address match {
        case Some(address: Address) => CustomerWithAddress(id, address)
        case None => CustomerWithoutAddress(id)
      }
    )
  }

  // Functional composition with typing? Kleislilosowski?
  def register(username: String, customerIdentifier: String, address: String) = {
    // Bind pattern?
    createAccount(Username(username)) match {
      case Some(account: Account) => createCustomer(CustomerIdentifier(customerIdentifier), Some(Address(address)))
      case None => false
    }
  }
}