package tests

import org.scalatest.FunSuite
import store.model.checkout.SelfCheckout
import store.model.items.Item

class Task1 extends FunSuite {

  def compareDoubles(d: Double, value: Double): Boolean = {
    Math.abs(d-value)< 0.001
  }

  test("cant_change_price & description_not_exact & overcharges_by_one_cent") {
    var testSelfCheckout: SelfCheckout = new SelfCheckout()

    var testItem: Item = new Item("test item", 100.0)

    testSelfCheckout.addItemToStore("123", testItem)
    var descriptions:List[String]=List()
    var prices:List[Double]=List()
    descriptions=descriptions:+"My description"
    prices=prices:+5.99
    descriptions=descriptions:+"Oreos"
    prices=prices:+4.50
    var items:List[Item]=List()
    for (index <- descriptions.indices){
      items=items:+new Item(descriptions(index),prices(index))

      assert(items(index).description()==descriptions(index),"testing description "+ "explected: "+descriptions(index)+" actually got: "+items(index).description())
      assert(Math.abs(items(index).price()-prices(index))<.0001,"testing constructor "+ "expected: "+prices(index)+" got: "+items(index).price())
      items(index).setBasePrice(prices(index)-5)
      assert(Math.abs(items(index).price()-(prices(index)-5))<.0001,"testing setBasePrice "+ "expected: "+(prices(index)-5)+" got: "+items(index).price())
    }

  }

  test("display broken") {
    var testSelfCheckout: SelfCheckout = new SelfCheckout()
    var testItem: Item = new Item("test item", 102.0)
    var testItem2: Item = new Item("test item2", 85.0)
    testSelfCheckout.addItemToStore("472", testItem)
    testSelfCheckout.addItemToStore("402", testItem2)
    testSelfCheckout.numberPressed(4)
    testSelfCheckout.numberPressed(7)
    testSelfCheckout.numberPressed(2)
    assert(testSelfCheckout.displayString() == "472")
  }

  test("always adds the same item") {
    var testSelfCheckout: SelfCheckout = new SelfCheckout()
    var testItem: Item = new Item("test item", 102.0)
    var testItem2: Item = new Item("test item2", 85.0)
    testSelfCheckout.addItemToStore("472", testItem)
    testSelfCheckout.addItemToStore("402", testItem2)
    testSelfCheckout.numberPressed(4)
    testSelfCheckout.numberPressed(7)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.enterPressed()
    testSelfCheckout.numberPressed(4)
    testSelfCheckout.numberPressed(7)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.enterPressed()
    //    val invalidItem:Item = new Item("error",0.0)
    //    var cart: List[Item] = List()
    assert(testSelfCheckout.itemsInCart() == List(testItem,testItem))
  }

  test("always adds the same item2") {
    var testSelfCheckout: SelfCheckout = new SelfCheckout()
    var testItem: Item = new Item("test item", 102.0)
    var testItem2: Item = new Item("test item2", 85.0)
    testSelfCheckout.addItemToStore("472", testItem)
    testSelfCheckout.addItemToStore("402", testItem2)
    testSelfCheckout.numberPressed(4)
    testSelfCheckout.numberPressed(7)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.enterPressed()
    testSelfCheckout.numberPressed(4)
    testSelfCheckout.numberPressed(0)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.enterPressed()

    assert(testSelfCheckout.itemsInCart() == List(testItem,testItem2))
  }

  test("breaks on invalid barcodes") {
    var testSelfCheckout: SelfCheckout = new SelfCheckout()
    testSelfCheckout.numberPressed(4)
    testSelfCheckout.numberPressed(7)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.enterPressed()
    assert(testSelfCheckout.itemsInCart().head.description() == "error")
    assert(Math.abs(testSelfCheckout.itemsInCart().head.price() - 0.0)< 0.01)
  }

  test("does not initially display empty") {
    var testSelfCheckout: SelfCheckout = new SelfCheckout()
    assert(testSelfCheckout.displayString() == "")
  }

  test("clear_doesnt_clear"){
    var testSelfCheckout: SelfCheckout = new SelfCheckout()
    var testItem: Item = new Item("test item", 102.0)
    var testItem2: Item = new Item("test item2", 85.0)
    testSelfCheckout.addItemToStore("472", testItem)
    testSelfCheckout.addItemToStore("402", testItem2)
    testSelfCheckout.numberPressed(4)
    testSelfCheckout.numberPressed(7)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.clearPressed()
    assert(testSelfCheckout.displayString() == "")
  }

  test("does_not_accept_leading_zeros"){
    var testSelfCheckout: SelfCheckout = new SelfCheckout()
    var testItem: Item = new Item("test item", 102.0)
    testSelfCheckout.addItemToStore("072", testItem)
    testSelfCheckout.numberPressed(0)
    testSelfCheckout.numberPressed(7)
    testSelfCheckout.numberPressed(2)
    testSelfCheckout.enterPressed()
    assert(testSelfCheckout.itemsInCart() == List(testItem))
  }

  //  test("test one") {
  //    var testSelfCheckout: SelfCheckout = new SelfCheckout()
  //    var testItem: Item = new Item("test item", 102.0)
  //    assert(testItem.description() == "test item")
  //    testItem.setBasePrice(85.00)
  //    assert(testItem.price() === 85.00)
  //
  ////    testSelfCheckout.addItemToStore("123", testItem)
  //    testSelfCheckout.numberPressed(123)
  //    assert(testSelfCheckout.displayString() == "123")
  ////    assert(testSelfCheckout.addItemToStore("142",testItem) === Map("142" ->testItem))
  //
  //  }
//test("task2 test one ") {
//  val checkout: SelfCheckout = new SelfCheckout()
//  val item: Item = new Item("pineapple", 3.5)
//  checkout.addItemToStore("283", item)
//  checkout.numberPressed(2)
//  checkout.numberPressed(8)
//  checkout.numberPressed(3)
//  checkout.enterPressed()
//  checkout.numberPressed(2)
//  checkout.numberPressed(8)
//  checkout.numberPressed(3)
//  checkout.enterPressed()
//
//  val cart: List[Item] = checkout.itemsInCart()
//  assert(cart.size == 2)
//  assert(cart.head.description() == "pineapple")
//  assert(compareDoubles(cart.head.price, 1.75))
//  assert(cart(1).description() == "pineapple")
//  assert(compareDoubles(cart(1).price(), 1.75))
//  }


}
