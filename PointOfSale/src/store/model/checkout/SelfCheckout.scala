package store.model.checkout

import store.model.items.{Item, Modifier, Sale}

class SelfCheckout {

  var itemMap: Map[String,Item] = Map()
  var barCodePressed:String = ""
  var placeholderBarcode: String = ""
  val invalidItem:Item = new Item("error",0.0)
  var cart: List[Item] = List()
//  var state: State = new enterAgain(this)
var state: State = new enterAgain(this)



  def addItemToStore(barcode: String, item: Item): Unit = {
    itemMap = itemMap + (barcode -> item)
  }
  def numberPressed(number: Int): Unit = {
    barCodePressed = barCodePressed + number.toString()
    placeholderBarcode = barCodePressed

  }

  def clearPressed(): Unit = {
//    println("asdasd")
    this.state.clearPressed()
  }

  def enterPressed(): Unit = {
    this.state.enterPressed()
//    println("enter not in state")
  }

  def checkoutPressed(): Unit = {
    this.state.checkoutPressed()
    //    state = new enterAgain(this)
  }

  def cashPressed(): Unit = {
    this.state.cashPressed()
  }

  def creditPressed(): Unit = {
    this.state.creditPressed()
  }

  def loyaltyCardPressed(): Unit = {
    // TODO
  }

  def displayString(): String = {
    barCodePressed
  }

  def itemsInCart(): List[Item] = {
    cart
  }

  def subtotal(): Double = {
    var subtotal:Double = 0.0
    for (items <- cart){
      subtotal = subtotal + items.price()
    }
    subtotal
  }

  def tax(): Double = {
    var taxes:Double = 0.0
    for (items <- cart) {
      taxes = taxes + items.tax()
    }
    taxes
  }

  def total(): Double = {
    var totalAmount:Double = 0.0
    totalAmount = subtotal() + tax()
    totalAmount
  }

abstract class State(val checkout:SelfCheckout){
  def enterPressed(): Unit = {}

  def numberPressed(number: Int): Unit = {}

  def checkoutPressed(): Unit = {}

  def clearPressed(): Unit = {}

  def creditPressed(): Unit = {}

  def cashPressed(): Unit = {}
}

  class enterAgain(checkout: SelfCheckout) extends State(checkout) {
    override def enterPressed(): Unit = {
      barCodePressed = ""
      var addingItem = itemMap.getOrElse(placeholderBarcode,invalidItem)
      cart = cart:+ addingItem
//      println("placeholderBarcode")
//      println(placeholderBarcode)
    }

    override def numberPressed(number: Int): Unit = {
      barCodePressed = barCodePressed + number.toString()
      state = new cleared(checkout)

    }

    override def checkoutPressed(): Unit = {
      barCodePressed = "cash or credit"
      state = new checkedOut(checkout)
    }

    override def clearPressed(): Unit = {
//      this.store.state = new cleared(this.store)
//      println("to cleared")
//      this.state.clearPressed()
//var state: State = new enterAgain(this)
      barCodePressed = ""
      state = new cleared(checkout)

    }
  }
//  Ex: If an item is added to the machine with a barcode of "123" and the customer presses "1" "2" "3" "enter" "enter" "enter" then they are purchasing 3 items of this type. Once any number button or the clear button is pressed, this item cannot be added again in this way (ie. "1" "2" "3" "enter" "enter" "enter" "5" "clear" "enter" will purchase 3 of the item - not 4 - and the 4th item in the cart is "error" 0.0)
//  Note: The display is still cleared when enter is pressed

  class cleared(checkout: SelfCheckout) extends State(checkout) {
    override def enterPressed(): Unit = {
      barCodePressed = ""
//      var addingItem = itemMap.getOrElse(placeholderBarcode,invalidItem)
      cart = cart:+ invalidItem
      println("placeholderBarcode")
//      println(placeholderBarcode)
    }

    override def numberPressed(number: Int): Unit = {
      barCodePressed = barCodePressed + number.toString()
    }

    override def checkoutPressed(): Unit = {
      state = new checkedOut(checkout)
    }

    override def clearPressed(): Unit = {
//      println("placeholderBarcode")
      barCodePressed = ""
    }
  }

  class checkedOut(checkout: SelfCheckout) extends State(checkout) {
    override def enterPressed(): Unit = {
//      barCodePressed = ""
      //      var addingItem = itemMap.getOrElse(placeholderBarcode,invalidItem)
//      cart = cart:+ invalidItem
      println("checkedEntered")
      //      println(placeholderBarcode)
    }

    override def numberPressed(number: Int): Unit = {
      barCodePressed = barCodePressed + number.toString()
    }

    override def checkoutPressed(): Unit = {
      barCodePressed = "cash or credit"
    }

    override def cashPressed(): Unit = {
      cart = List()
       println("cash")
      barCodePressed = ""
      state = new enterAgain(checkout)

     }

    override def creditPressed(): Unit = {
       cart = List()
       println("credit")
       barCodePressed = ""
       state = new enterAgain(checkout)

     }
    override def clearPressed(): Unit = {
      //      println("placeholderBarcode")
//      barCodePressed = ""
    }
  }



    def prepareStore(): Unit = {
    // Similar to openMap in the Pale Blue Dot assignment, this method is not required and is
    // meant to help you run manual tests.
    //
    // This method is called by the GUI during setup. Use this method to prepare your
    // items and call addItemToStore to add their barcodes. Also add any sales/tax/etc to your
    // items.
    //
    // This method will not be called during testing and you should not call it in your tests.
    // Each test must setup its own items to ensure compatibility in AutoLab. However, you can
    // write a similar method in your Test Suite classes.

    // Example usage:
    val testItem: Item = new Item("test item", 100.0)
    addItemToStore("472", testItem)
  }

}
