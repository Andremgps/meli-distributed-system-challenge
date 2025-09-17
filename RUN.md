# Run Guide

## Easy Way

You just need docker and docker-compose installed on your machine.

Then run the following commands:

`git clone https://github.com/Andremgps/meli-distributed-system-challenge.git`

`cd meli-distributed-system-challenge`

`docker-compose up --build`

And you are ready to go! Refer to README.md for main endpoints and usage.

## Hard Painful Way

### Prerequisites

* Java 21
* Maven 3.9
* Docker

### Steps

1. Clone the repository:

   `git clone https://github.com/Andremgps/meli-distributed-system-challenge.git`
2. Start rabbitmq using docker:

   `docker-compose up rabbitmq`
3. Navigate to api-gateway directory and run:
 
   `cd api-gateway`

   `mvn spring-boot:run`
4. In a new terminal, navigate to inventory-service directory and run:

   `cd inventory-service`

   `mvn spring-boot:run`
5. In a new terminal, navigate to store-service directory and run:

   `cd store-service`

   `mvn spring-boot:run`

After all services are up, you can access the API Gateway at `http://localhost:8080`.
Refer to README.md for main endpoints and usage.
