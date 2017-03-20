# BlueSpurs Interview Test

- [Task 1](#task-1)
- [Task 2 (BONUS)](#task-2-bonus)
- [Task 1 Solution Description](#task-1-solution-description)

## Task 1

Your task is to create a RESTful web service endpoint that allows a client to input a product name as a GET query parameter and returns the cheapest product.

Using the provided API keys for the BestBuy and Walmart APIs, your result should return the best (minimum) price for the product and which store to buy it from. If there are multiple products, always return the lowest priced product. For example:

**Request**
```
GET /product/search?name=ipad
```

**Example Response**
```
200 OK

{
    "productName": "iPad Mini",
    "bestPrice": "150.00",
    "currency": "CAD",
    "location": "Walmart"
}
```

### Required API Keys

**BestBuy**: `pfe9fpy68yg28hvvma49sc89`

**Walmart**: `rm25tyum3p9jm9x9x7zxshfa`

## Task 2 (BONUS)

A second optional task is another RESTful web service endpoint that allows a client to input an email address and product name as a JSON object in the HTTP body.

Using the `EmailService`, every time the minimum price decreases for the specified item, send an email to the specified email address indicating that the price has dropped.

**Example Request**
```
POST /product/alert
{
    "productName": "ipad",
    "email": "someone@somewhere.com"
}
```

**Example Response**
```
200 OK

(no response body)
```

**Example Email**
```
Sending email to 'someone@somewhere.com' with subject 'The price of the ipad has dropped!' and message 'The price of the ipad has dropped from 150.00CAD to 148.00CAD! Get it quick!'
```

*Note that when using the `EmailService`, no emails are actually sent. A string similar to the example email will be logged in the console.*

## When You're Finished...

Commit this project *to your **own** Git repository* on Github or Bitbucket and email us the link to the repository.

# Starter Kit Documentation

**NOTE 1: Commands and file paths in this documentation are relative to the project root unless otherwise specified.**

This starter kit is an example of the systems we use at BlueSpurs. We want to avoid time spent setting up environments and worrying about boilerplate. The goals are:

- Introduce a common base for all projects
- Reduce developer environment dependencies
- Standardize deployments
- Follow best practices

## Key Features

- Gradle build tools
- Unit tests
- In-memory caching by default, configurable to use other providers such as Redis, Memcache, or EhCache
- Cross-origin resource sharing (CORS) enabled by default
- Spring projects including Boot, Security, Devtools, and HATEOAS
- Tomcat hooks for WAR container deployment
- Logging using SLF4J
- Test code coverage reporting

# Getting Started

## What You Need

To get started, you'll need 2 things:

1. The Java JDK (development kit) version 1.8
2. The text editor or IDE of your choice (recommend Eclipse or IntelliJ)
    - Recommended IDE for use are Eclipse and IntelliJ
    - Additional setup instructions are provided for each below

## Configuring and Running the Project

Configuration values can be found in the `src/main/resources/application.properties` file. For a development environment, these defaults should work out of the box.

## Running the Development Server

To run the project, issue the command `./gradlew bootRun` (on Windows, omit `./`). After some time, you should be able to open `http://localhost:8080` in your web browser to see the text, *"The Bluespurs Spring Starter Kit is running properly."*

## Running Tests

You can run tests using gradle by executing `./gradlew cleanTest test`.

## Running the Project in IntelliJ

After opening IntelliJ, if you have any projects open, choose `File -> Close Project`.

From the start screen, follow these steps:

1. Open project...
2. Navigate to the `settings.gradle` file and select it.
3. In the gradle settings dialog, enable `Use auto-import` and set the JVM to version 1.8.
4. Click OK. Once IntelliJ is finished building the project, navigate to `Run -> Edit Configurations...`.
5. Create a new configuration (click the `+` icon) and choose `Gradle`.
    - Name it anything, like `Development Server`.
    - Enable `Single instance only`.
    - Choose the gradle project.
    - Set the task to `bootRun`.
6. Create a new configuration and choose `JUnit`.
    - Name it `Unit Tests`.
    - Set the test kind to `Category`.
    - Set the category to `com.bluespurs.starterkit.UnitTest`.
    - Set the search to `Whole project`.

Now that the run configurations are set up, you can run or debug the application and it's tests. Once the development server is running, it is not necessary to reboot it after every change. Simply recompile the project (`Build -> Make Project` or `Ctrl-F9`) and the changes will be reloaded.

## Running the Project in Eclipse

These instructions are for Eclipse Mars using the regular flavour of Eclipse (Eclipse Java SE or Eclipse for Java Developers - not Eclipse Java EE).

1. In Eclipse, follow `File -> Import...` and expand `Gradle` to select `Gradle Project`.
2. Choose `Next`.
3. Select the root directory. Leave all of the settings as-is and navigate through the dialogs. Click `Finish`.
4. Eclipse will build the project.
5. Navigate to `Run -> Run Configurations`.
6. Add a new `Gradle Project` run configuration:
    - Name it anything, like `Development Server`.
    - Set the task to `bootRun`.
7. Add a new `JUnit` run configuration:
    - Name it anything, like `Tests`.
    - Select `Run all tests in the selected project`.
    - Set the test runner to `JUnit 4`.

You can now run the application and it's tests.

# Understanding the Starter Kit

The Spring Framework uses a variety of design patterns that users should familiarize themselves with in order to take advantage of the framework in full. Spring makes an effort to make itself more manageable by providing Spring Boot. Spring Boot helps developers get started writing production-grade Java applications with very little configuration. Most of the configuration is handled automatically with the ability to be overridden as needed by the developer.

A comprehensive overview of Spring Boot can be found here: [Spring Boot v1.3.5.RELEASE single page documentation](http://docs.spring.io/spring-boot/docs/1.3.5.RELEASE/reference/htmlsingle/)

As much as possible, following the [S.O.L.I.D software design principles](https://en.wikipedia.org/wiki/SOLID_(object-oriented_design)) when developing software leads to systems that, over time, tend to be more extensible and maintainable while removing code smells. Spring embraces such principles.

Prior to Spring version 4, most configuration was done using XML files. Today, *[annotation](https://en.wikipedia.org/wiki/Java_annotation) driven* configuration is encouraged. This starter kit uses annotation driven configuration exclusively.

## Getting Familiar with the Stack

A brief description of the technologies used in the starter kit:

- **REST** (Representation State Transfer) is an architectural style used to build applications that embrace HTTP as the data transport protocol. HATEOAS (Hypermedia as the Engine of Application State) is used for *service discovery* in REST applications.
- **Gradle** is a *build tool* which manages the compiling/packaging process and other tasks, such as downloading other Java packages (typically JAR files) that are depended on. Spring Boot is one such dependency that is managed by Gradle.
- **Spring** is a Java framework. It is comprised of many projects (such as Spring Boot or Spring Security) that can be used interchangeably. For example, it is possible to use the Spring Web project without using Spring Boot. Some projects complement other projects, but can be interchanged with other implementations due to the Java Specification Requests (JSR) which set standards for frameworks such as Spring.

If you're new to these technologies, it is important to understand their respective roles in the development process.

## Common Design Patterns in Spring

- **Inversion of Control with Dependency Injection** (IoC and DI): Rather than having objects that define and instantiate their own associations (dependencies), IoC is the idea that objects only need to define their dependencies, and have an external object (often called the *container*) instantiate the correct instances. DI is a design pattern that enables IoC.
    - [Inversion of Control Containers and the Dependency Injection pattern - Martin Fowler](http://www.martinfowler.com/articles/injection.html)
    - [Inversion of Control and Frameworks - Martin Fowler](http://martinfowler.com/bliki/InversionOfControl.html)
    - [What in the world are Spring Beans? - Stack Overflow](http://stackoverflow.com/questions/17193365/what-in-the-world-are-spring-beans)
    - [The IoC Container and Beans - Spring Documentation](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html)
- **Data Transfer Objects** (DTO): A simple method of representing data to be transferred from one system to another (common prior to serialization). This starter kit uses the DTO pattern in two places: input and output. Input classes are stored in `com.bluespurs.starterkit.controller.request` and classes are stored in `com.bluespurs.starterkit.controller.resource`. These classes are only used to transfer data into and out of the system.
    - [Data Transfer Object - Patterns of Enterprise Application Architecture - Martin Fowler](http://martinfowler.com/eaaCatalog/dataTransferObject.html)
- **Repositories and Specifications**: Repositories are a domain-specific (business-specific) abstraction to *Data Access Objects* (DAO), a common design pattern for CRUD (create, read, update, delete) operations on a database.
    - [What is the difference between DAO and Repository patterns? - Stack Overflow](http://stackoverflow.com/questions/8550124/what-is-the-difference-between-dao-and-repository-patterns)
    - [Repository - Patterns of Enterprise Application Architecture - Martin Fowler](http://martinfowler.com/eaaCatalog/repository.html)

## Starter Kit Architecture Pattern

In many Java applications (not exclusive to Spring), it is common to see a 3-tier architecture that resembles an MVC structure.

> There is no problem in computer science that cannot be solved by adding another layer of indirection, except having too many layers of indirection. - David Wheeler *or* Butler Lampson

- **Controllers** handle I/O and talk to *services*.
- **Services** perform business logic (including transactional work and caching) and talk to *repositories*.
- **Repositories** manage data access in a domain context and talk to the *DAO*.

You may have noticed that this is a layered architecture. For example, controllers should not talk to repositories directly. Layers should only communicate with those directly above or below it. This, of course, is a *pattern* not a rule.

# Development Topics

## Validating User Input

Input validation is handled by the [Hibernate Validator](http://hibernate.org/validator/) library. Validation can be done on user input (namely POST and PUT HTTP verbs) by creating a class inside of `com.bluespurs.starterkit.controller.request` and annotating the fields appropriately. For example, a registration input class may look like this:

```java
public class UserRegistrationRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Length(min = 8, max = 72)
    private String password;

    // Getters and setters omitted.
}
```

To use this class, simply accept it as an argument to any controller method (make sure to annotate it with `@Valid @RequestBody` to tell Spring that it needs to be validated and that the data comes from the HTTP request body):

```java
@RestController
class MyController {
    @RequestMapping(method = RequestMethod.POST)
    public void registerNewUser(@Valid @RequestBody UserRegistrationRequest request) {
        // Use getter and setter methods to access user input.
    }
}
```

If validation fails, Spring will terminate the request and generate an HTTP 400 Bad Request response.

## Generating a WAR

The starter kit is only configured to run inside of a Tomcat servlet container. To generate the WAR file, run: `./gradlew bootRepackage`. For this project, `bootRepackage` has been configured to depend on `test`, meaning that a WAR file cannot be deployed if any tests are failing.


## Task 1 Solution Description

Solution contains only task 1 implementation. 
Controller level is represented by `ProductController`, that deligates real work to `ProductService` implementation (`ProductServiceImpl`). This service uses `RestTemplate` for invocation of actual API for each of registered seller. Seller is described by domain class `Seller`, that encloses all actual seller information like name, API key, URL template, and also information about returned JSON structure - like products node name, and names of the elements, where name and best price are stored. Rest template, making a request, does not directly map a resulting JSON to any particular object because it would require a separate mapper implementation for each new seller (and keeping the process of adding new seller simple is critical). Instead, helper method is used to create new `Product` POJO from returned `JsonNode` object and current seller - this object is the one, that is returned to the client as JSON and is similar for all sellers. 

New seller could be added in `ApplicationConfiguration` class, directly into sellers collections. Builder is provided (and should be used) for creating new instances. Builder also ensures, that provided about seller information is complete. 

Error handling is implemented by `ErrorController`, that handles different types of exceptions and returns corresponding HTTP response code and user-friendly message about the error. Custom exceptions include `ProductNotFoundException` (when product is not found) and `BadHttpResponseException` (when seller returns NOT-OK HTTP response code). Situations with wrong API keys, not supplied product name, and bad HTTP requests are handled using Spring framework exceptions. No error page is supplied (so trying to use not mapped request will show standard error page).

API keys can be found in `application.properties` file, that is incorrect - in production system they should be injected from environment variables.

Tests are implemented only for `ProductController` class, that also is not good (I remember about TDD), but due to time restrictions I've decided, that working app could be more interesting, than working tests. So, if I could create TODO list for applicaiton - first item for this list is normal test coverage.

Another item for TODO - checking, that provided sellers information is correct during application startup, and signaling that something is wrong when information is not correct (like URL is not responding, or API key is not correct, or provided info about returned JSON is not correct), so critical information could be corrected ASAP.

It was all I was able to implement within required timeframe, so maybe I'm just too slow for you :)
