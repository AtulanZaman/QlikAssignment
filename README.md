# Interview Assignment for Qlik

### Feature Overview
This projects implements a Spring Boot application to perform CRUD operations for messages using a REST API.
Additionally the properties of a message describe whether the text is a palindrome or not.

### Technical Overview
The web application is created using the Spring Boot web framework. The ORM for messages is implemented using JPA, with annotation support provided by Spring Boot. "Message.java" represents the model for messages and "MessageRepository.java" represents the ORM mapping to a JPA repository. 

"MessageController.java" represents the controller layer for interfacing with the model for HTTP requests. Spring Boots's mapping packages are used to wire the HTTP method mappings to relevant method implentation in the model using annotations. Additionally Spring's "Hateoas" package has been used to provide necessary encapsulation to transform the object representations to "Hypertext Application Languange" format that is a signature template for RESTful APIs. This is implemented in the respective request mapping methods in "MessageController.java", with an assembler for the Message object "MessageModelAssembler" to format the messages into a HAL friendly template for REST API.

In order to make the service a fully RESTful service, distinguished from a simple RPC service, the response template of the APIs need to follow "Hypertext Application Language" standards. This includes by is not limited to adding "self" and "collection" links to every GET request response. For this implementation I have not implemented the paging related links for requests, which should also be present for REST APIs.

The "isPalindrome" property of the "/messages" API indicates whether a text message is a palindrome or not. This is a non-persisted property of the Message class, that is added in the API object based on some calculation.

### API Documentation
The Message API has the following schema and HTTP endpoints
```json
{
    "id" : <Integer, unique>,
    "text" : <String>,
    "isPalindrome" : <boolean, read-only>
}
```

- GET: /messages
- GET: /messages/{id}
- POST: /messages
- PUT: /messages/{id}
- DELETE: /messages/{id}


### API Example
Example response for a "GET: /messages" request
```json
{
    "_embedded": {
        "messageList": [
            {
                "id": 1,
                "text": "Hi",
                "isPalindrome": false,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/messages/1"
                    },
                    "messages": {
                        "href": "http://localhost:8080/messages"
                    }
                }
            },
            {
                "id": 2,
                "text": "Hello world!",
                "isPalindrome": false,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/messages/2"
                    },
                    "messages": {
                        "href": "http://localhost:8080/messages"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/messages"
        }
    }
}
```

### Testing Strategy
This project implements a unittest and 5 API tests for each of the HTTP methods.
JUnit test:
The JUnit test is implemented in MessagesApplicationTests.java.
    - "testMessageIsPalindrome" : Tests that Message.getIsPalindrome returns correct result for all test cases. It mocks the MessageRepository service to avoid persistence initialization.

API tests:
The API tests are implemented in a Postman collection located in directory "test/api/postman". The collection has a request for each mapping, and tests to assert the values and structure of the expected response from the server.
- GET : Assert status, collection of messages and structure of response to check RESTful properties.
- GETOne : Assert status, structure and values of a response to get 1 message using and id.
- POST : Assert status, structure and values of a response to create a new message.
- PUT : Assert status, structure and values of response to update a message.
- DELETE : Assert status of request to successfully delete a message.