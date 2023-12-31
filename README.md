# REST Services with Spring V4

### Domain Event
This version includes the domain event patterns. 
The __Book Service__ implements two ways of publishing and handling domain events
which are enabled in Spring Boot, i.e., 
the `AbstractAggregateRoot` generic class and 
the `ApplicationEventPublisher` interface.

Demonstrating Use Cases
Borrow the book `0-684-84328-5` from the Wollongong City Library (2500):
```shell
curl -X PUT http://localhost:8082/books/borrow/0-684-84328-5/2500
```
Then, return it to the same library:
```shell
curl -X PUT http://localhost:8082/books/return/0-684-84328-5/2500
```
During the process, check the change of available libraries:
```shell
curl -X GET http://localhost:8082/books/{0-684-84328-5}/available
```

### Event Sourcing
This version also demonstrates the event sourcing pattern.
#### H2 Console
Add the following two lines to `src/main/resources/application.properties`:
```properties
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
```
The console [http://localhost:8082/h2-console/](http://localhost:8082/h2-console/).
To log on, change the value in the `JDBC URL` entry to 
`jdbc:h2:mem:testdb`. 

_Compare the `BOOK` table and the `BOOK_EVENT` table._
