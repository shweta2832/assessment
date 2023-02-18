BDD Framework
This is a Behavior-Driven Development (BDD) framework that is used to write automated tests for a web application. The framework uses the Page Object Model (POM) design pattern to organize the code and improve maintainability.

Project Structure
The project structure consists of the following components:

src/main/java/pages: This folder contains the page classes that represent the web pages of the application. Each page class contains methods that interact with the elements on the page.
src/test/resources/features: This folder contains the feature files that describe the behavior of the application and the scenarios that are under test. The feature files are written in the Given-When-Then format.
src/test/java/step_definition: This folder contains the step definition classes that implement the steps defined in the feature files. Each step definition class contains methods that perform the actions described in the steps.

Configuration
The following configuration files are used by the framework:

configs/configuration.properties: This file contains the hardcoded values used by the framework, such as the email address, password, and application URL.

src/test/resources/extent.properties: This file contains the properties for the Extent reporting plugin, such as the output directory and file name.
Running the Framework
To run the framework, follow these steps:

Clone the repository to your local machine.
Ensure that you have the correct version of Chrome installed and that the chrome_driver.exe file is present in the drivers folder.
Open the project in an IDE and run the src/test/java/runner/Runner.java file as a JUnit test.
or run using mvn test from the directory where pom.xml is located
After the tests have finished running, the Extent report will be generated in the test-output folder.
Report
The report is generated using the ExtentCucumberAdapter plugin and consists of an HTML file (Spark.html) and a folder containing screenshots (Screenshots).



