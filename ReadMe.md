**Billing Service**

This project provides functionalities for calculating discounts, computing net payable amounts, and retrieving exchange rates. It is built using Java, Maven, and Spring Boot, and includes comprehensive unit tests with code coverage analysis using JaCoCo.​

**Table of Contents**

- Prerequisites

- Project Structure

- Building the Project

- Running Tests

- Generating Code Coverage Reports

- UML Diagram

- Integrated Endpoints

**Prerequisites**

Ensure you have the following installed on your system:
- Jdk 8 or higher
- Git
- Maven

**Building the Project**

To build the project, navigate to the project root directory and execute:

mvn clean install

This command will clean any previous builds and compile the source code. It will also run unit tests and package the application​

**Running Tests**

To execute all unit tests, use the following command:

mvn test

This will run all tests located in the src/test/java directory.

**Running Static Code Analysis**

The project integrates Maven checkstyle plugin to run static code analysis. To generate the code analysis report:

Run the following Maven command:

mvn checkstyle:checkstyle

This will run the static code analysis and will generate the report.

After the build completes, open the following file in your browser to view the report:

target/checkstyle/checkstyle-report.xml

The report provides detailed insights into static code analysis.


**Generating Code Coverage Reports**

The project integrates JaCoCo for code coverage analysis. To generate the coverage report:

Run the following Maven command:

mvn clean verify

This will execute tests and generate the coverage report.

After the build completes, open the following file in your browser to view the report:​

target/site/jacoco/index.html

The report provides detailed insights into code coverage metrics, including line, branch, and method coverage.​

**UML Diagram**

The UML Diagram of all the class relationships is provided in the root folder of the project

**Integrated Endpoints**

The application integrates with an external exchange rate service to fetch current currency conversion rates.​

Exchange Rate Service: Retrieves the exchange rate between two currencies.​

Endpoint: GET /exchange-rate?baseCurrency={baseCurrency}&targetCurrency={targetCurrency}​

Response:

{
"baseCurrency": "USD",
"targetCurrency": "PKR",
"exchangeRate": 285.5
}
Usage: Utilized by the CurrencyExchangeService to obtain current exchange rates for currency conversion during net payable calculations.​
