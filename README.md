# banking-test-task

## requirements

* JDK 11+

## dev

To run application:

```
mvn spring-boot:run
```

Server will be available at url: `http://localhost:8080`

## methods

### client
* GET https://localhost:8080/api/v1/client
* GET https://localhost:8080/api/v1/client/{id}
* POST https://localhost:8080/api/v1/client
* PUT https://localhost:8080/api/v1/client/{id}
* DELETE https://localhost:8080/api/v1/client/{id}

### bank
* GET https://localhost:8080/api/v1/bank
* GET https://localhost:8080/api/v1/bank/{id}
* POST https://localhost:8080/api/v1/bank
* PUT https://localhost:8080/api/v1/bank/{id}
* DELETE https://localhost:8080/api/v1/bank/{id}

### deposit
* GET https://localhost:8080/api/v1/deposit
* GET https://localhost:8080/api/v1/deposit/{id}
* POST https://localhost:8080/api/v1/deposit
* PUT https://localhost:8080/api/v1/deposit/{id}
* DELETE https://localhost:8080/api/v1/deposit/{id}
* POST https://localhost:8080/api/v1/deposit/search

## todos
* authorization
* run tests with mysql (cause h2 doesn't support some features)
    * unique constraints
    * update with concat + timestamp
    * dates
    * or probably just should maintain such constraints of app side for compatibility...
* mock time in tests