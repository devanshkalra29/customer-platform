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
- Docker installation

### Installation
1. Clone the repo:
`git clone git@github.com:devanshkalra29/customer-platform.git`
2. Build via terminal using `./gradlew build`or in intelliJ using the [gradle tools window](https://www.jetbrains.com/help/idea/jetgradle-tool-window.html)

### API Endpoints:
All the API endpoints are defined in the **CustomerController** class:

| Method | URL                   | Description | Parameters                 | Request Body Example                                                                                                                                                |
|--------|-----------------------|---          |----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
 | GET    | `/customers/v1/`      | Retrieves all customers | -                          | -                                                                                                                                                                   |
 | GET    | `/customers/v1/{id}`  | Retrieves a customer by ID | `id`: UUID of the customer | -                                                                                                                                                                   |
 | GET    | `/customers/v1/search` | Retrieves a customer by email | `email`: email of the customer  | -                                                                                                                                                                   |
 | PUT    | `/customers/v1/{id}`  | Updates details of an existing customer by Id | `id`: UUID of the customer | `{"prefix" : "Mr.","firstName": "John","middleName": "Allen","lastName": "Johnson","suffix": "Sr","email": "john.johnson@example.com","phoneNumber": "1234567890" }` |
 | POST   | `/customers/v1`       | Adds a new customer record to the database    | - | `{"prefix" : "Mr.","firstName": "John","middleName": "Allen","lastName": "Johnson","suffix": "Sr","email": "john.johnson@example.com","phoneNumber": "1234567890" }` |
 | DELETE | `customers/v1/{id}`   | Removes a customer record from the database | `id`: UUID of the customer | - |


### Configuration
#### Application.properties
in the application.properties file, you will find a basic H2 database configurations, along with the necessary properties for Datadog.
- **H2** - The H2 database properties come configured in properties file. To simulate the database environment and provide a realistic schema/ data setup, the application utilizes a **schema.sql** and **data.sql** files. These scripts are automatically executed at runtime to create the necessary database schema and populate the database with the initial data.



- **Datadog** 
1. In order to generate metrics monitor via dashboards, you will need to create a [Datadog account](https://www.datadoghq.com/). 
2. Once created, you will need to install a Datadog agent on the host where the application is running. Instructions can be found [here](https://docs.datadoghq.com/getting_started/agent/).
3. Create an API Key and Application (APP) Key via your Datadog account.
4. add both you API key and APP key to your environment variables in IntelliJ or System. The applications.properties file already has placeholders for these names below (example for intelliJ setup shown).
<img width="497" alt="Screenshot 2024-04-21 at 6 13 04 PM" src="https://github.com/devanshkalra29/customer-platform/assets/112348374/8b05c5ec-4095-4bd6-aa6c-69ab261f4bb2">



### Docker & Helm Chart Local Setup
In order to utilize helm, please ensure that you have Minikube, kubectl and Docker installed (listed in the prerequisites). Let's walk through a **NodePort** setup. All the commands can be run via terminal

**Docker**: 
- run the command `./gradlew clean build` from your project directory, which will create a jar file in your `build/libs` folder.

**Helm setup**:
- the helm configuration files have already been created in this repo. If wanting to create again, you can delete the **helmchart** and re-generate the files using `helm create {name of folder}` in the project directory. 
- In the **values.yaml** file
  - In the image section: change repository value to "customer-platform" and tag as "latest".
  - In the service section: change type to "NodePort", and port to 8080.

**Minikube**:
- When starting up minikube run `minikube start --driver=docker`
- Run the command `eval$(minikube docker-env)`to set up the docker environment.
- Run the command `docker build -t customer-platform .` to create your image. to verify it was created, you can run `docker images`.
- To deploy the kubernetes config, run the command `helm install {any name} {name of folder created above for the helm chart}`.
- Check your deployment by running `kubectl get pods`.




## Testing
Under the **src/main** directory, there are Unit tests written using Spock. in the **test/resources** folder, a **mockCustomers.json** file is created for mock data purposes. The tests can be ran individually in intelliJ or via terminal.


## Contact
please email the author at devanshkalra29@gmail.com with any questions!












 
