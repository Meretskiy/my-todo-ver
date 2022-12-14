## Server side application: myTODOver
Application for maintaining a to-do list.
The functionality allows you to create a user and log in. 
After authorization: you can add notes, delete, edit, you can mark the note as completed and get the entire list of notes.

## Getting Started

#### To run app, you can use a pre-built Docker image or build it locally.

https://hub.docker.com/r/meretskiy/my-todo-ver-0.0.1

#### Launch Docker container in the background:

 - Build and run jar:

```
./mvnw package && java -jar target/my-todo-ver-0.0.1-SNAPSHOT.jar
```

 - Build docker image:

```
docker build -t my-todo-ver-0.0.1 .
```

 - Run docker image:

```
docker run -d -p 8189:8189 -t my-todo-ver-0.0.1
```

- Fast test:

```
curl --location --request POST 'http://localhost:8189/todover/api/v1/user/authentication' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "BillyBones",
"password": "100"
}'
```

Web interface is not ready yet, you can test the api using postman.

### Stack:
 - Java 11
 - Spring-Boot
 - Spring-Web
 - Spring-Data-JPA
 - Spring-Security
 - H2
 - Lombok
 - Jjwt
 - Flyway
 - Junit
 - Sel4j 
 - Maven  
 - Docker

### Api documentation:

- #### Registration in the application:

 @POST http://localhost:8189/todover/api/v1/user/registration

request:

```json
{
 "username": "DarkWindDuck", 
 "password": "111",
 "email": "duck@dark.com"
}
```


 - #### Authorization and obtaining a token:

@POST http://localhost:8189/todover/api/v1/user/authentication

request:

```json
{
"username": "BillyBones",
"password": "100"
}
```

response:

```json
{
"token": "....."
}
```

This Bearer Token is used in all API requests.

 - #### Getting the entire list of notes for a given user:

@GET http://localhost:8189/todover/api/v1/notes

response:

```json
[ 
    {
    "id": 1,
    "username": "BillyBones",
    "article": "buy food",
    "creationDateTime": "2022-10-22T22:41:53.850384",
    "executed": false
    },
    {
    "id": 2,
    "username": "BillyBones",
    "article": "take out the trash",
    "creationDateTime": "2022-10-22T22:41:53.850384",
    "executed": false
    },
    {
    "id": 3,
    "username": "BillyBones",
    "article": "call a friend",
    "creationDateTime": "2022-10-22T22:41:53.850384",
    "executed": true
    }
]
```

 - #### Create a new note:

@POST http://localhost:8189/todover/api/v1/notes

response:

```json
{
"id": 8,
"username": "BillyBones",
"article": "",
"creationDateTime": "2022-10-23T00:05:38.732553",
"executed": false
}
```

 - #### Select note:

@GET http://localhost:8189/todover/api/v1/notes/{id}

request:

```
id notes
```

response:

```json
{
"id": 8,
"username": "BillyBones",
"article": "pay the bills",
"creationDateTime": "2022-10-23T00:05:38.732553",
"executed": true
}
```

 - #### Editing a note / changing the progress status

@POST http://localhost:8189/todover/api/v1/notes/update

request and response:

```json
{
"id": 8,
"username": "BillyBones",
"article": "pay the bills",
"creationDateTime": "2022-10-23T00:05:38.732553",
"executed": true
}
```

 - #### Deleting a note:

@DELETE http://localhost:8189/todover/api/v1/notes/{id}

request:

```
id note
```

### TODO:

 - add logging
 - add validation
 - finalize the separation of roles paid / free user
 - make a front on AngularJS
