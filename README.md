# movie-theater-ticket-service

Java 8.0 and Maven 3.5.2 based project to simulate the working of a movie theater reservation system.

The service provides the following functionality
1. Find the number of seats available within the venue
    Note: available seats are seats that are neither held nor reserved.
2. Find and hold the best available seats on behalf of a customer
    Note: each ticket hold should expire within a set number of seconds.
3. Reserve and commit a specific group of held seats for a customer



### Execution Steps:
1. git pull the project on your local host.

2. IF you dont have JAVA and Maven installed, install them using Homebrew :  https://brew.sh/

    ``` brew cask install java8 ```

    ``` brew install maven  ```

3. Verify their installation

    ``` java -version```

    ```maven -version ```

4. navigate to the project root and execute the following

 ```  mvn package  ```

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
