# Movie-Theater-Ticket-Service

Java 8.0 and Maven 3.5.2 based project to simulate the working of a movie theater reservation system.


The service simulates the following functionality for a 10 X 10 movie theater
1. Find the number of seats available within the venue.
    Note: available seats are seats that are neither held nor reserved.
2. Find and hold the best available seats on behalf of a customer.
    Note: each ticket hold should expire within a set number of seconds.
3. Reserve and commit a specific group of held seats for a customer.

The project demonstrates key Java concepts like OOP, Collection, MultiThreading, Thread safety,
Race Conditions like 'check-then-act', 'read-modify-write' etc.

Maven (for those who dont know) is a dependency management and build automation tool for Java applications.
Good Read: http://www.baeldung.com/ant-maven-gradle



### Execution Steps:
1. git pull the project on your local host.

2. IF you dont have JAVA and Maven installed, install them using Homebrew :  https://brew.sh/
    ``` brew cask install java8 ```
    ``` brew install maven  ```

3. Verify their installation :
    ` java -version`
    `maven -version `

4. navigate to the project root and execute the following
    `  mvn clean install  `

    This will  install all the project dependencies.
    You will see a `target` folder in your project root

5.   `  mvn package  `

    you should see something like :

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.255 s
[INFO] Finished at: 2017-11-06T07:23:15-05:00
[INFO] Final Memory: 16M/213M
[INFO] ------------------------------------------------------------------------
```


    Then type the following :

    ```  java -cp target/movie-theater-ticket-service-1.0-SNAPSHOT.jar App  ```

you should see something like :

```
 Please provide a numeric input to perform one of the actions
 [ 1 ] for remaining seats
 [ 2 ] for reserve seats
 [ 3 ] for book seats
 To Exit, press any other key
```
You have the app running !!!




### TESTING :-

The following testing frameworks have been used :
jUnit : testing Framework
Mockito : For mocking dependencies
AssertJ : Assertion Library

Run the test suite using  :  ` mvn test  `

 Check Code Coverage at :   ` ./target/site/index.html`