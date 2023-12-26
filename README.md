Amazon Shopping Backend
Welcome to the E-Shopping Backend repository! This project serves as the backend for an e-commerce platform, providing the necessary functionality to support online shopping activities.

Table of Contents
  > Introduction
  > Features
  > Getting Started
  > Prerequisites
  > Installation
  > Usage
  > API Endpoints
  > Configuration
  > HTML Email
  > Contributing
  > Contributing

Introduction
  E-Shopping Backend is designed to handle the server-side logic for an e-commerce application. It includes functionalities such as user authentication, product management, cart management, and order processing. This backend is built using Java Spring Boot ,designed to handle the server-side logic for an e-commerce application. It uses MySQL for data storage and integrates with tools such as Postman for API testing and pgAdmin for database management and Implimented Swagger as well.

Features

  > User authentication (signup, login)
  > Product management (add, update, delete products) by Admin
  > Cart management (add to cart, remove from cart, view cart)
  > Order processing (place an order, view order history)
  > Secure password storage
  > Getting Started
  > Prerequisites
  > Before you begin, ensure you have the following installed:
  
  npm (comes with Node.js)
  Maven
  MySQL workSpace
  Installation
  Clone the repository:
  
  
API Endpoints

POST [/api/auth/signup](http://localhost:8083/e-shopping/users/sign-up): Create a new user.
GET [/api/auth/login](http://localhost:8083/e-shopping/users/login): Authenticate and receive a JWT token.

Product & Admin Management
PATCH http://localhost:8083/e-shopping/users/admin/approve-admin?userName=raju01&userId=6 : Approve User as Admin (change userName and userId) accordingly
GET [/api/products](http://localhost:8083/e-shopping/admin/products?userName=ARJUN): Get all products.
POST [/api/products](http://localhost:8083/e-shopping/admin/product/add): Add a new product.

Cart Management
GET [/api/cart](http://localhost:8083/e-shopping/users/show-cart?uid=4): Get the user's shopping cart.
POST [/api/cart/add/:productId](http://localhost:8083/e-shopping/users/2?uid=6): Add a product to the cart.
DELETE [/api/cart/remove/:productId](http://localhost:8083/e-shopping/users/cart/remove-item?uid=4&pid=5): Remove a product from the cart.

Order Processing

POST [/api/orders](http://localhost:8083/e-shopping/users/place-order): Place a new order.
GET [/api/orders](http://localhost:8083/e-shopping/users/100/your-orders): Get the user's order history.
DELETE http://localhost:8083/e-shopping/users/orders/6/cancel-orders/6 : Cancel the Order


![Screenshot (106)](https://github.com/arjun-chand/E-shopping-Backend/assets/124900252/1e027dc9-a247-4678-afbd-dea7f23860c2)



![Screenshot (105)](https://github.com/arjun-chand/E-shopping-Backend/assets/124900252/a2b09b66-c6b8-4ae6-9749-bf480bcea7ac)


![Screenshot (100)](https://github.com/arjun-chand/E-shopping-Backend/assets/124900252/0700b200-90f7-4521-a2f8-d87cc4582bd9)
