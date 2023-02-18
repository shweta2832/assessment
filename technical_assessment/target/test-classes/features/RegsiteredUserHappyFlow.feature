Feature: Buying and Placing an order as registered user

  Scenario: Buy and Place an order
    Given User is on Shopping Website page 
    When Clicks on Signin Option to Login
    And Provides the credentials emailaddress and password
    And Clicks Signin for logging into application
    Then User gets login successfully
    When User provides product details add them to shopping cart
    |menu|category|subcategory|quantity|
		|Men|Tops|Jackets|2|
		|Men|Bottoms|Pants|1|
		And Go to Cart page to verify added Products and Price
		Then User checkout the items
		And Provide the Delivery Address details
		|FirstName|LastName|Company|StreetAddress1|StreetAddress2|StreetAddress3|City|State|Zip|Country|Phone|saveAddress|
		|Shweta|Bansal|Random|Street Address1|Street Address2|Street Address3|Test|Florida|151001|United States|90|False|
		And Select Shipping method as "Best Way" for Delivery 
		Then User places the order
		And Verify if order is successfully placed
		And Verify the Submitted order under My Orders section

