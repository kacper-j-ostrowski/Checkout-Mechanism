# README #

This README would normally document whatever steps are necessary to get your application up and running.


### How do I get set up? ###

You can simply run it with: mvn spring-boot:run 

Sample data are placed in resources/data.sql.

You can use two REST services: "/itemsInBasket" (GET) and "/newItem" (POST, productCode in payload) 

!IMPORTANT -> you need to have installed maven and Java 8

### Project goal and assumptions ###
* The project was about creating checkout mechanism with two kind of promotions: quantity and combined promotion
* If product A has combined promotion with product B then product B cannot have any kind of promotion
* If product A has both combined and quantity promotion then we calculate which is more rental to customer and we choose this one

### Project tests ###

Test are placed in CheckoutMechanism / src / test / java / pl / ostrowski /


