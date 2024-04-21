# Customer Platform
## Overview
Customer Platform is a RESTFUL web service that is designed to manage customer data. It provides well-defined CRUD endpoints while ensuring data integrity and ease of access. 

## Features 
### Interacting with Customer Data

- *Create Customer*: Adds a new customer to the database.
- *Retrieve Customer*: Fetches details of existing customers by email or ID
- *Update Customer*: Modifies details of an existing customer.
- *Delete Customer*: Removes a customer from the database
- *Get All Customers*: Retrieves all customers from the database

### Monitoring with Datadog
Customer Platform uses performance and usage metrics that are monitored using Datadog, which provides real-time observability and operational insights.

### Kubernetes-Ready with Helm
Deployment configurations are managed via Helm Charts, which helps simplify deployments across Kubernetes environments.


## Main Technologies Used
- [**Java (JDK17)**](https://openjdk.org/projects/jdk/17/) - Core programming language
- [**Spring Boot**](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - Spring Boot is an open-source Java framework used to create a Micro Service.
- **JPA/Hibernate** - for ORM and database interaction.
- [**Gradle**](https://docs.gradle.org/current/userguide/userguide.html) - Build automation tool.
- [**H2 Database**](https://www.h2database.com/html/main.html) - for a lightweight, in-memory database that doesn't need a server setup (mainly used since this is a POC).
- [**Docker**](https://docs.docker.com/) - Used to build, deploy, run and manage containers.
- [**Datadog**](https://docs.datadoghq.com/) - Used to monitor applications, track their performance, and creating dashboards for operational insights.
- [**Helm**](https://helm.sh/docs/) - Used to manage Kubernetes applications through Helm charts.
- [**Spock**](https://helm.sh/docs/) - Testing framework for Java and Groovy applications.

## Getting Started
### Prerequisites:
- JDK 17 or later
- Gradle 7+
- IDE such as IntelliJ for ease of use
- kubectl/minikube setup

### Installation
1. Clone the repo:
`git clone git@github.com:devanshkalra29/customer-platform.git`
2. Build via terminal using `./gradlew build`or in intelliJ using the [gradle tools window](https://www.jetbrains.com/help/idea/jetgradle-tool-window.html)

### API Endpoints:
All the API endpoints are defined in the **CustomerController** class:

| Method | URL                    | Description | Parameters                 | Request Body Example                                                                                                                                                |
|--------|------------------------|---          |----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
 | GET    | `/customers/v1/`       | Retrieves all customers | -                          | -                                                                                                                                                                   |
 | GET    | `/customers/v1/{id}`   | Retrieves a customer by ID | `id`: UUID of the customer | -                                                                                                                                                                   |
 | GET    | `/customers/v1/search` | Retrieves a customer by email | `email`: email of the customer  | -                                                                                                                                                                   |
 | PUT    | `/customers/v1/{id}`   | Updates details of an existing customer by Id | `id`: UUID of the customer | `{"prefix" : "Mr.","firstName": "John","middleName": "Allen","lastName": "Johnson","suffix": "Sr","email": "john.johnson@example.com","phoneNumber": "1234567890" }` |
 | POST   | `/customers/v1/`       | Adds a new customer record to the database    | - | `{"prefix" : "Mr.","firstName": "John","middleName": "Allen","lastName": "Johnson","suffix": "Sr","email": "john.johnson@example.com","phoneNumber": "1234567890" }` |
 | DELETE | `customers/v1/{id}`    | Removes a customer record from the database | `id`: UUID of the customer | - |


### Configuration
#### Application.properties
in the application.properties file, you will find a basic H2 database configurations, along with the necessary properties for Datadog.
- **H2** - The H2 database properties come configured in properties file. To simulate the database environment and provide a realistic schema/ data setup, the application utilizes a **schema.sql** and **data.sql** files. These scripts are automatically executed at runtime to create the necessary database schema and populate the database with the initial data.



- **Datadog** 
1. In order to generate metrics monitor via dashboards, you will need to create a [Datadog account](https://www.datadoghq.com/). 
2. Once created, you will need to install a Datadog agent on the host where the application is running. Instructions can be found [here](https://docs.datadoghq.com/getting_started/agent/).
3. Create an API Key and Application (APP) Key via your Datadog account.
4. add both you API key and APP key to your environment variables in IntelliJ or System. The applications.properties file already has placeholders for these names below (example for intelliJ setup shown).
![](../../../../var/folders/z9/xw486v412pn7bjjjfvxybtx80000gn/T/TemporaryItems/NSIRD_screencaptureui_EMU6i6/Screenshot 2024-04-21 at 5.45.24 PM.png)


### Docker & Helm Chart


## Testing
Under the **src/main** directory, there are Unit tests written using Spock. in the **test/resources** folder, a **mockCustomers.json** file is created for mock data purposes.


## Contact
please email the author at devanshkalra29@gmail.com with and questions!












 
