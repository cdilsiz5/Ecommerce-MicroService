# E-commerce Microservices Application

## Description
This is an e-commerce application implemented as a set of microservices. The application supports operations like creating, reading, updating, and deleting (CRUD) products and categories, adding products to a shopping cart, placing an order, user registration and authentication, and more.

## Technologies
- Java and Spring Boot
- Spring Data JPA
- Spring Security
- Spring Cloud
- Hibernate
- Maven
- Lombok
- MapStruct
- PostgreSQL
- RabbitMQ (for asynchronous messaging)



## Microservices
1. **API Gateway**: This service routes requests to the appropriate microservices.
2. **AuthService**: This service handles user registration and authentication (Under Development).
3. **UserService**: This service handles user-related operations.
4. **ProductService**: This service handles product-related operations.
5. **CategoryService**: This service handles category-related operations.
6. **CartService**: This service handles shopping cart operations.
7. **OrderService**: This service handles order-related operations.
8. **NotificationService**: This service sends notifications to users.
9. **ShippingService**: This service handles shipping-related operations (Under Development).
10. **RabbitMQ Service**: Handles the communication between services using RabbitMQ.

## Project Structure
- Each microservice is a separate Maven project and can be built and run independently.
- Common code like models and utilities is placed in a separate module and included as a dependency in the microservices that require it.

## TODO
- Implement unit and integration tests for the microservices.
- Implement Spring Security and JWT authentication in the AuthService.

## Contributors
- [Cihan Dilsiz](https://github.com/cdilsiz5)

