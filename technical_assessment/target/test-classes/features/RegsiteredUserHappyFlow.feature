Feature: Buying and Placing an order as registered user

  Scenario: Buy and Place an order
    Given User is on the website page
	When User clicks sign in option to login
	Then User enters email address and password
	Then Clicks Signin for logging into application
	Then User gets login successfully
	Then User adds products to shopping cart
	|menu|category|subcategory|quantity|
	|Men|Tops|Jackets|2|
	|Men|Bottoms|Pants|1|
	Then Verify the order summary on checkout page
	Then User Clicks Proceed to checkout
	And Provides Delivery Address details
	|FirstName|LastName|Company|StreetAddress1|StreetAddress2|StreetAddress3|City|State|Zip|Country|Phone|saveAddress|
	|Shweta|Bansal|Random|Street Address1|Street Address2|Street Address3|Test|Florida|151001|United States|90|False|
	And Selects Shipping method as "Best Way" for Delivery
	And Completes the place order process
	And Views Success message with order number on screen
	And Verify the Submitted order under My Orders section
