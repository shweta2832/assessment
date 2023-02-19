Feature: Buying and Placing an order as registered user

  Scenario: Buy and Place an order
    Given User is on the website
	When User clicks on sign in button
	Then User enters email address and password
	Then User submit the details
	Then User is logged in
	Then User adds products to shopping cart
	|menu|category|subcategory|quantity|
	|Men|Tops|Jackets|2|
	|Men|Bottoms|Pants|1|
	Then User Verify the order summary on checkout page
	Then User Clicks on Proceed to checkout button
	And User Provides Delivery Address details
	|FirstName|LastName|Company|StreetAddress1|StreetAddress2|StreetAddress3|City|State|Zip|Country|Phone|saveAddress|
	|Shweta|Bansal|Random|Street Address1|Street Address2|Street Address3|Test|Florida|151001|United States|90|False|
	And User Selects Shipping method as "Best Way" for Delivery
	And User clicks on place order button
	And User Views Success message with order number on screen
	And User Verify the Submitted order under My Orders section
