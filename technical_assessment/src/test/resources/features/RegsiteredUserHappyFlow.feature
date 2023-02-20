Feature: Buying and Placing an order as registered user

  Scenario: Buy and Place an order
  Given Registered customer navigates to shopping website
  When Customer adds products to shopping cart
       |menu|category|subcategory|quantity|
       |Men |Tops    |Jackets    |2       |
       |Men |Bottoms |Pants      |1       |
  Then Customer successfully places the order by making payment
  And Customer gets Success message with order number on screen					
