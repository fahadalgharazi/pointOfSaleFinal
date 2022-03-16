package tests

import org.scalatest.{FunSuite, color}
import store.model.checkout.SelfCheckout
import store.model.items.{BottleDeposit, Item, Modifier, Sale, SalesTax}




class Task2 extends FunSuite{
  def compareDoubles(d: Double, value: Double): Boolean = {
    Math.abs(d-value)<0.001
  }

  test("buying an item with a sale"){
    val checkout: SelfCheckout = new SelfCheckout()
    val item: Item = new Item("pineapple",3.5)
//    val item2: Item = new Item("apple",3.5)

    val sale: Modifier = new Sale(50)
    item.addModifier(sale)
//    item2.addModifer(sale)


    checkout.addItemToStore("283", item)
//    checkout.addItemToStore("284", item2)

    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()
    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()

    val cart: List[Item] = checkout.itemsInCart()
    assert(cart.size == 2)
    assert(cart.head.description() == "pineapple")
    assert(compareDoubles(cart.head.price, 1.75))
    assert(cart(1).description() == "pineapple")
    assert(compareDoubles(cart(1).price(),1.75))
    assert(compareDoubles(checkout.subtotal(),3.5))

  }

  test("extra_one_percent_tax"){
    val checkout: SelfCheckout = new SelfCheckout()
    val item: Item = new Item("pineapple",3.5)
    //    val item2: Item = new Item("apple",3.5)
//    val sale: Modifier = new Sale(50)
//    item.addModifier(sale)

    val saleTax: Modifier = new SalesTax(50)
    item.addModifier(saleTax)
    //    item2.addModifer(sale)


    checkout.addItemToStore("283", item)
    //    checkout.addItemToStore("284", item2)

    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()

    val cart: List[Item] = checkout.itemsInCart()
    assert(cart.size == 1)
    assert(cart.head.description() == "pineapple")
//    assert(compareDoubles(cart.head.price, 5.25))
    println(checkout.total())
    assert(compareDoubles(checkout.total(),5.25))

  }

  test("extra_penny_bottle_deposit"){
    val checkout: SelfCheckout = new SelfCheckout()
    val item: Item = new Item("pineapple",3.5)
    //    val item2: Item = new Item("apple",3.5)
    //    val sale: Modifier = new Sale(50)
    //    item.addModifier(sale)

    val bottle: Modifier = new BottleDeposit(0.05)
    item.addModifier(bottle)
    //    item2.addModifer(sale)


    checkout.addItemToStore("283", item)
    //    checkout.addItemToStore("284", item2)

    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()

    val cart: List[Item] = checkout.itemsInCart()
    assert(cart.size == 1)
    assert(cart.head.description() == "pineapple")
    //    assert(compareDoubles(cart.head.price, 5.25))
    println(checkout.total())
    assert(compareDoubles(checkout.total(),3.55))

  }

  test("items_wrong_with_no_modifiers"){
    val checkout: SelfCheckout = new SelfCheckout()
    val item: Item = new Item("pineapple",3.5)
    //    val item2: Item = new Item("apple",3.5)
    //    val sale: Modifier = new Sale(50)
    //    item.addModifier(sale)

    //    item2.addModifer(sale)


    checkout.addItemToStore("283", item)
    //    checkout.addItemToStore("284", item2)

    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()

    val cart: List[Item] = checkout.itemsInCart()
    assert(cart.size == 1)
    assert(cart.head.description() == "pineapple")
    //    assert(compareDoubles(cart.head.price, 5.25))
    println(checkout.total())
    assert(compareDoubles(checkout.total(),3.5))

  }

  test("only_one_modifier_allowed"){
    val checkout: SelfCheckout = new SelfCheckout()
    val item: Item = new Item("pineapple",3.5)
    //    val item2: Item = new Item("apple",3.5)
    //    val sale: Modifier = new Sale(50)
    //    item.addModifier(sale)

    val bottle: Modifier = new BottleDeposit(0.05)
    val saleTax: Modifier = new SalesTax(50)

    item.addModifier(bottle)
    item.addModifier(saleTax)

    //    item2.addModifer(sale)


    checkout.addItemToStore("283", item)
    //    checkout.addItemToStore("284", item2)

    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()

    val cart: List[Item] = checkout.itemsInCart()
    assert(cart.size == 1)
    assert(cart.head.description() == "pineapple")
    //    assert(compareDoubles(cart.head.price, 5.25))
    println(checkout.total())
    assert(compareDoubles(checkout.total(),5.30))

  }

  test("sales_dont_compound_correctly"){
    val checkout: SelfCheckout = new SelfCheckout()
    val item: Item = new Item("pineapple",3.5)
    //    val item2: Item = new Item("apple",3.5)
    //    val sale: Modifier = new Sale(50)
    //    item.addModifier(sale)

    val sale: Modifier = new Sale(50)
    val sale2: Modifier = new Sale(50)


    item.addModifier(sale)
    item.addModifier(sale2)

    //    item2.addModifer(sale)


    checkout.addItemToStore("283", item)
    //    checkout.addItemToStore("284", item2)

    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()

    val cart: List[Item] = checkout.itemsInCart()
    assert(cart.size == 1)
    assert(cart.head.description() == "pineapple")
    //    assert(compareDoubles(cart.head.price, 5.25))
    println(checkout.total())
    assert(compareDoubles(checkout.total(),0.875))

  }

  test("sale, tax, and bottle deposit on the same item"){
    val checkout: SelfCheckout = new SelfCheckout()
    val item: Item = new Item("pineapple",100)
    //    val item2: Item = new Item("apple",3.5)
    //    val sale: Modifier = new Sale(50)
    //    item.addModifier(sale)

    val saleTax: Modifier = new SalesTax(50)
    val sale: Modifier = new Sale(50)
    val bottle: Modifier = new BottleDeposit(0.25)

    item.addModifier(saleTax)
    item.addModifier(sale)
    item.addModifier(bottle)

    checkout.addItemToStore("283", item)

    checkout.numberPressed(2)
    checkout.numberPressed(8)
    checkout.numberPressed(3)
    checkout.enterPressed()

    val cart: List[Item] = checkout.itemsInCart()
    assert(cart.size == 1)
    assert(cart.head.description() == "pineapple")
    //    assert(compareDoubles(cart.head.price, 5.25))
//    println(checkout.total())
//    assert(compareDoubles(checkout.cart.head.price(),50))
//    println(checkout.cart.head.tax())
//    assert(compareDoubles(checkout.cart.head.tax(),25.25))
    assert(compareDoubles(checkout.total(),75.25))

  }
}
