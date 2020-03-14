# Spring Boot Authentication Service

Spring Boot application providing authorisation for left-side applications

## Built With

* 	[Gradle](https://gradle.org/) - Dependency Management
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[PostgreSQL](https://www.postgresql.org/) - Open-Source Relational Database Management System
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system 
* 	[Thymeleaf](https://www.thymeleaf.org/) - Modern server-side Java template engine for both web and standalone environments.

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.dukekan.springboot.authmicroservice.authmicroservice.AuthmicroserviceApplication` class from your IDE.

- Open IDEA 
   - File -> New -> Project from version control -> Print `https://github.com/DukeKan/authmicroservice.git` into the input field
- Import the project as Gradle project
- Open terminal and run ```gradle wrapper``` to initialize Gradle wrapper
- Create `authmicroservice` database with the help of any PostgreSQL client app
- Run ```gradlew bootRun``` in order to start application
- Go to the http://localhost:8080/login page

### Security

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Spring Boot Starter Security default username is `test` and default password is `test`

### URLs

|  URL |  Method |
|----------|--------------|
|`http://localhost:8080/login`                           | GET | 
|`http://localhost:8080/register`                       | GET | 