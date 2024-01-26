E-commerce back-end developed with Spring Boot

User Registration and Login: Users can create an account by entering basic information such as first name, last name, email, and password. Logged-in users can view and edit their account information. Options for password reset or change can be provided.

Product Listing: Users can search for products based on attributes such as name, price, and category. Product detail pages may include information such as product descriptions, images, and stock status. Users can add products to their favorites or share them.

Shopping Cart Operations: Users can add products to their shopping cart and view its contents. The total price of items in the cart can be calculated in real-time. Users can update the quantity of items in the cart or remove items from it.

Order Creation: Users can convert the contents of their shopping carts into orders. When an order is created, payment options (credit card, bank transfer, etc.) can be offered. Users can be provided with order confirmation and order number.

Order History: Users can view their past orders, filtering by date or status. Detailed information for each order, including products, quantities, and prices, can be displayed.

Category Management: Users with an admin role can add new product categories, edit existing categories, or delete them. Products can be assigned to specific categories, and products can be listed by category.

Security and Authorization: Users can only view their own order and profile information. The admin role may be subject to special authorization and can perform specific functions.

Statistics and Reporting: Users with an admin role can view statistics and generate reports on daily, monthly, yearly, etc. sales, cancellations, and returns.

Technical Requirements: Code Quality (Design, Naming, etc.) API Documentation Validation Error Handling Logging Application Optimization Database Design and Optimization Application Dockerization Authorization and Role Management

At the same time, communication between discount and product service is established with gRPC
