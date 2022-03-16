package store.model.items


class Item(var itemDescription:String, var Itemprice:Double) {

  // TODO: Complete this class according to the features listed in the HW document

  var modifierList: List[Modifier] = List()

  def description(): String = {
    itemDescription
  }

  def setBasePrice(newPrice: Double): Unit = {
    //changes the base price of the item
    Itemprice = newPrice
  }

  //  The "price" method should now apply all modifiers to the base price of the item by calling their "updatePrice" methods
  //  Tax must not be included in this price
  def price(): Double = {
    //loop through the modifers in the list chagning the price
    var itemPricePlaceHolder:Double = Itemprice
    var updater:Double = 0.0
    for (mod <- modifierList) {
//      Itemprice = mod.updatePrice(Itemprice)
        updater = mod.updatePrice(itemPricePlaceHolder)
        itemPricePlaceHolder = updater
    }
    itemPricePlaceHolder

  }

  def addModifier(modifier: Modifier): Unit = {
    //put the modifiers in a list
    modifierList = modifierList :+ modifier
  }
  //The following changes in the Item class:
  //  Add a method named "addModifier" that takes a Modifier as a parameter and returns Unit
  //    After a modifier is added with this method, that modifier should be applied to all future method calls????

  //  A method named "tax" that takes no parameters and returns the total tax applied to this item from all of its modifiers as a Double
  //    All taxes should be applied to the price of the item with modifiers applied (call price()). For example, If an item has a price of $100, a sale of 20%, a sales tax of 6%, and a second sales tax of 4%, then the tax for this item is $8. Don't make your customers pay tax for the base price of an item when they bought it on sale.

  def tax(): Double = {
    var totalTax: Double = 0.0
    for (mod <- modifierList) {
      totalTax = totalTax + mod.computeTax(this.price())
      println(totalTax)
//      println(totalTax)
    }

    totalTax
  }
}
abstract class Modifier() {
  //  A method named "updatePrice" that takes a Double as a parameter and returns a Double. The input represents the price of an item before the modifier is applied and the method returns the new price with the modifier applied (This method can be abstract, or contain a default implementation that can be overridden)

  def updatePrice(price: Double): Double

  //  A method named "computeTax" that takes a Double as a parameter and returns a Double. The input represents the price of an item (not the base price) and the method returns the amount of tax to be charged by the modifier (This method can be abstract, or contain a default implementation that can be overridden)
  def computeTax(taxPrice: Double): Double

}

//A constructor that takes a Double representing the percentage of the sale
class Sale(var salePrecentage: Double) extends Modifier {
  //  The updatePrice method should take the price of an item before the sale is applied and return the new price with the sale applied
  //  Example: If the sale is 20% and the input is 100.0, this method returns 80.0
  override def updatePrice(price: Double): Double = {
    var newPrice: Double = price * (1 - (salePrecentage/100))
    newPrice
  }

  //  The computeTax method of a sale should always return 0.0
  override def computeTax(taxPrice: Double): Double = {
    0.0
  }

}
//A class named SalesTax that inherits the Modifier abstract class with:
//  A constructor that takes a Double representing the percentage of the sales tax
//  Example: An input of 8.0 means the sales tax is 8% of the price of the item
//  The updatePrice method to return the input price unmodified
//  The computeTax method should return the amount of sales tax that should be charged based on the inputted price of the item

//abstract class SalesTax(var SalesTax:Double) extends Modifer {
class SalesTax(var taxPercentage: Double) extends Modifier {
  override def updatePrice(price: Double): Double = {
    //    var newUpdatedPrice = price/100
    //    newUpdatedPrice
    price
  }

  //  The computeTax method should return the amount of sales tax that should be charged based on the inputted price of the item
  override def computeTax(taxPrice: Double): Double = {
    var amountOfSalesTax = taxPrice *(taxPercentage / 100)
    amountOfSalesTax
  }

}

//A class named BottleDeposit that inherits the Modifier abstract class with:
//  A constructor that takes a Double representing the total amount of the deposit to be charged
//  The updatePrice method to return the input price unmodified
//  The computeTax method should return the deposit amount from the constructor
//  The amount of the deposit does not depend on the price of the item

class BottleDeposit(var totalDeposit: Double) extends Modifier {
  override def updatePrice(price: Double): Double = {
    price
  }

  //  The computeTax method should return the amount of sales tax that should be charged based on the inputted price of the item
  override def computeTax(taxPrice: Double): Double = {
    totalDeposit
  }
}









