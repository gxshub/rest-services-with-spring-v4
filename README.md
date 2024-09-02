# REST Services with Spring V4

### Domain Event
This version includes the domain event patterns. 
The __Book Service__ implements two ways of publishing and handling domain events
which are enabled in Spring Boot, i.e., 
the `AbstractAggregateRoot` generic class
(used in [`Book.java`](./book-service/src/main/java/csci318/demo/model/Book.java) and 
the `ApplicationEventPublisher` interface
(used in [`BookService.java`](./book-service/src/main/java/csci318/demo/service/BookService.java)).

Reference: [https://www.baeldung.com/spring-data-ddd](https://www.baeldung.com/spring-data-ddd)

#### Demonstrated Use Cases
(1) Borrow the book `0-684-84328-5` from the Wollongong City Library (2500):
```shell
curl -X PUT http://localhost:8082/books/borrow/0-684-84328-5/2500
```
(2) Return it to the same library:
```shell
curl -X PUT http://localhost:8082/books/return/0-684-84328-5/2500
```
In between the above two steps, check the change of available libraries:
```shell
curl -X GET http://localhost:8082/books/{0-684-84328-5}/available
```

### Event Sourcing
This version also demonstrates the event sourcing pattern.
#### H2 Console
<!--
Add the following two lines to `src/main/resources/application.properties`:
```properties
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
```
-->
Go to the H2 console [http://localhost:8082/h2-console/](http://localhost:8082/h2-console/).
<!-- To log on, change the value in the `JDBC URL` entry to 
`jdbc:h2:mem:testdb`. -->
_Compare the `BOOK` table and the `BOOK_EVENT` table (which is 
considered as an event store)._

_TODO._
When borrowing a book which is not available in the library 
(i.e., performing step (1) two consecutive times), an event is not
created in the `BOOK_EVENT` event store.
This is correct as the unsuccessful request does not change the system state. 
However, information should be returned to the customer to indicate the (un)successful
book borrowing request. 
For this purpose,
implement a DTO object as the return of the PUT request used in step (1).