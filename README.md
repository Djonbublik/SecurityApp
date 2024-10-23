Write a simple spring boot application that allows you to simulate payment for mobile services. It is assumed that it will have a set of http methods that accept requests from a third-party client (Postman, wget, curl, etc.), no web user interface (UI in html and js) is required in the application.

All necessary information about the user is stored in the database. For the main part, these are id, login, password hash, balance, payments made (id, date, phone number, amount). For the additional part, you will also need your full name, email, gender and date of birth.

The main part:
1) User registration. This is the only method in the application available without authorization. All other http methods use basic authorization. It is better to use a phone number as a login. After registration, 1000 rubles will be credited to the user's balance, other ways of replenishment are not provided.
2) Getting a cash balance. Based on the authorization data, return the current value of the account balance and the user's login.
3) Payment. The API method must accept the phone number to which the payment is made and the amount (with kopecks). If there is enough money in the user's account, we write it off and return a response that the payment was successful. We save information about the payment made to the database.

Additional part:
1) Editing user data. The user should be able to create/correct their full name, email, gender and date of birth. 
2) The history of operations. Return a list of payments made by the user (he is the payer). Operation ID, phone number, amount, number. Add pagination to this method. Conditionally, out of the available 35 records, 10 records should be received separately per page. 
3) Unit tests for implemented functionality.


When completing a task, it is recommended to use:
1) Java 21
2) Spring boot 3.2.x
3) Postgresql 16 (MySQL/MariaDB can be used)
