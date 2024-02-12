Authors: 
James Marshall, Wolfy Fiorini, Kyle Brown, Hunter DeArment, and Conner Botte

Team project for the CS3250 Software Development Methods course at MSU Denver. 
This program creates and manages a database containing data about a hypothetical company's products. 

Desktop application:
![ims2](https://github.com/Wolfy319/Java-Inventory-Manager/assets/60371754/c32b628d-4946-4d48-9ec6-ffeddc176905)
Sign in page

![ims](https://github.com/Wolfy319/Java-Inventory-Manager/assets/60371754/c0a6a582-e770-4e42-91cf-367a010b92fb)
Inventory page

![sales](https://github.com/Wolfy319/Java-Inventory-Manager/assets/60371754/3fea0989-e19f-41ac-bb4d-3ff08b6748ed)
![sales2](https://github.com/Wolfy319/Java-Inventory-Manager/assets/60371754/b1a55cb9-98f7-47e6-afac-0d9bc2527149)
![sales3](https://github.com/Wolfy319/Java-Inventory-Manager/assets/60371754/13ba5291-ab6b-4214-a6aa-34d5f66bc079)
Sales overviews


Features of the program:
- Java desktop GUI application 
  - Linked to mySQL database
  - JavaFX GUI with MVC architecture
  - Secure user authentication/authorization and management
  - Authentication performed on login through database with password salting and hashing
  - Users can use CRUD operations on products which is updated in the database
  - Users can use CRUD operations on customer orders, which is updated in the database and dynamically updates products as well
  - App will generate visual reports on inventory, revenue, orders and customers 
- Angular web application
  - Mirrors desktop application functionality
  - Bootstrap template for CSS
  - Sends queries to backend, and updates with angular hooks
- Discord bot
  - Allows for orders to be submitted through a discord server's chat
  - Bot will email customers a receipt and summary when an order is submitted 
  - Customers will receive regular emails with recommendations for other products to buy
  - Orders are added or updated in mySQL database

Link to trello: https://trello.com/invite/b/oelGjI2g/43f649c50c9a668e4a9bf6954532e567/cs-3250-database-project
