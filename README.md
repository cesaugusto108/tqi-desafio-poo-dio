# tqi-desafio-poo-dio

Desafio de código do Bootcamp DIO TQI Kotlin Backend Developer (https://web.dio.me/track/bootcamp-tqi-kotlin)

- REST API
- Orientação a objetos (Abstração, encapsulamento, herança e polimorfismo)
- Java 17
- Kotlin 1.8.21
- JPA e Hibernate (Single table inheritance strategy)
- MySQL
- H2 embedded database
- Spring Security (basic authentication/authorization)
- JWT
- Flyway
- Swagger (localhost:8081/bootcamp-tracker-dev/v3/api-docs | http://localhost:8081/bootcamp-tracker-dev/swagger-ui/index.html)

Authenticate user:

```
$ curl -X POST -H "Content-Type: application/json" -d '{"username": "csim", "password": "1234"}' localhost:8081/bootcamp-tracker-dev/v1/auth/login/authenticate | json_pp
* Connected to localhost (127.0.0.1) port 8081 (#0)
> POST /bootcamp-tracker-dev/v1/auth/login/authenticate HTTP/1.1
> Host: localhost:8081
> User-Agent: curl/8.1.2
> Accept: */*
> Content-Type: application/json
> Content-Length: 40
>
} [40 bytes data]
< HTTP/1.1 200
< Vary: Origin
< Vary: Access-Control-Request-Method
< Vary: Access-Control-Request-Headers
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 0
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Mon, 14 Aug 2023 02:01:44 GMT
<
{ [279 bytes data]
100   312    0   272  100    40    902    132 --:--:-- --:--:-- --:--:--  1036
* Connection #0 to host localhost left intact
{
   "authenticated" : true,
   "expirationDate" : "2023-08-14T03:01:44.000+00:00",
   "issuedAt" : "2023-08-14T02:01:44.000+00:00",
   "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc2ltIiwiaWF0IjoxNjkxOTc4NTA0LCJleHAiOjE2OTE5ODIxMDR9.KTSGsXp9M04oq0wqONQxoniDkXC6JugLru3DZmLx_Po",
   "username" : "csim"
}
```

JSON:

```
$ curl -v -X GET --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc2ltIiwiaWF0IjoxNjkxOTc4NTA0LCJleHAiOjE2OTE5ODIxMDR9.KTSGsXp9M04oq0wqONQxoniDkXC6JugLru3DZmLx_Po" localhost:8081/bootcamp-tracker-dev/v1/developers | json_pp
* Connected to localhost (127.0.0.1) port 8081 (#0)
> GET /bootcamp-tracker-dev/v1/developers HTTP/1.1
> Host: localhost:8081
> User-Agent: curl/8.1.2
> Accept: */*
> Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc2ltIiwiaWF0IjoxNjkxOTc4NTA0LCJleHAiOjE2OTE5ODIxMDR9.KTSGsXp9M04oq0wqONQxoniDkXC6JugLru3DZmLx_Po
>
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0< HTTP/1.1 200
< Vary: Origin
< Vary: Access-Control-Request-Method
< Vary: Access-Control-Request-Headers
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 0
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Mon, 14 Aug 2023 02:06:37 GMT
<
{ [1853 bytes data]
100  1846    0  1846    0     0   3787      0 --:--:-- --:--:-- --:--:--  3782
* Connection #0 to host localhost left intact
[
   {
      "age" : 28,
      "bootcamps" : [
         {
            "activities" : [
               {
                  "date" : null,
                  "description" : "Sintaxe Kotlin",
                  "details" : "Aprendendo a sintaxe Kotlin",
                  "hours" : 300,
                  "id" : -4
               }
            ],
            "description" : "Linux Experience",
            "details" : "Aperfeiçoamento Linux",
            "finishDate" : null,
            "id" : -2,
            "startDate" : null
         },
         {
            "activities" : [
               {
                  "date" : null,
                  "description" : "Kotlin - POO",
                  "details" : "Orientação a objetos com Kotlin",
                  "hours" : null,
                  "id" : -1
               },
               {
                  "date" : null,
                  "description" : "Java - POO",
                  "details" : "Orientação a objetos com Java",
                  "hours" : null,
                  "id" : -2
               }
            ],
            "description" : "TQI Kotlin Backend",
            "details" : "Java e Kotlin backend",
            "finishDate" : null,
            "id" : -1,
            "startDate" : null
         }
      ],
      "email" : "carlos@email.com",
      "id" : -2,
      "level" : 4,
      "links" : [
         {
            "href" : "http://localhost:8081/bootcamp-tracker-dev/v1/developers",
            "rel" : "self"
         },
         {
            "href" : "http://localhost:8081/bootcamp-tracker-dev/v1/developers/-2",
            "rel" : "developer-2"
         }
      ],
      "name" : {
         "firstName" : "Carlos",
         "lastName" : "Moura",
         "middleName" : "Antônio"
      },
      "username" : "carlosan"
   },
   {
      "age" : 29,
      "bootcamps" : [
         {
            "activities" : [
               {
                  "date" : null,
                  "description" : "Sintaxe Kotlin",
                  "details" : "Aprendendo a sintaxe Kotlin",
                  "hours" : 300,
                  "id" : -4
               }
            ],
            "description" : "Linux Experience",
            "details" : "Aperfeiçoamento Linux",
            "finishDate" : null,
            "id" : -2,
            "startDate" : null
         },
         {
            "activities" : [
               {
                  "date" : null,
                  "description" : "Kotlin - POO",
                  "details" : "Orientação a objetos com Kotlin",
                  "hours" : null,
                  "id" : -1
               },
               {
                  "date" : null,
                  "description" : "Java - POO",
                  "details" : "Orientação a objetos com Java",
                  "hours" : null,
                  "id" : -2
               }
            ],
            "description" : "TQI Kotlin Backend",
            "details" : "Java e Kotlin backend",
            "finishDate" : null,
            "id" : -1,
            "startDate" : null
         }
      ],
      "email" : "josecc@email.com",
      "id" : -1,
      "level" : 2,
      "links" : [
         {
            "href" : "http://localhost:8081/bootcamp-tracker-dev/v1/developers",
            "rel" : "self"
         },
         {
            "href" : "http://localhost:8081/bootcamp-tracker-dev/v1/developers/-1",
            "rel" : "developer-1"
         }
      ],
      "name" : {
         "firstName" : "José",
         "lastName" : "Costa",
         "middleName" : "Carlos"
      },
      "username" : "josecc"
   }
]

```
YAML:

```
$ curl -v -X GET --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc2ltIiwiaWF0IjoxNjkxOTc4NTA0LCJleHAiOjE2OTE5ODIxMDR9.KTSGsXp9M04oq0wqONQxoniDkXC6JugLru3DZmLx_Po" --header "Accept:application/x-yaml" localhost:8081/bootcamp-tracker-dev/v1/developers
*   Trying 127.0.0.1:8081...
* Connected to localhost (127.0.0.1) port 8081 (#0)
> GET /bootcamp-tracker-dev/v1/developers HTTP/1.1
> Host: localhost:8081
> User-Agent: curl/8.1.2
> Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc2ltIiwiaWF0IjoxNjkxOTc4NTA0LCJleHAiOjE2OTE5ODIxMDR9.KTSGsXp9M04oq0wqONQxoniDkXC6JugLru3DZmLx_Po
> Accept:application/x-yaml
>
< HTTP/1.1 200
< Vary: Origin
< Vary: Access-Control-Request-Method
< Vary: Access-Control-Request-Headers
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 0
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/x-yaml
< Transfer-Encoding: chunked
< Date: Mon, 14 Aug 2023 02:08:45 GMT
<
---
- id: -2
  name:
    firstName: "Carlos"
    middleName: "Antônio"
    lastName: "Moura"
  age: 28
  username: "carlosan"
  email: "carlos@email.com"
  level: 4
  bootcamps:
  - id: -2
    description: "Linux Experience"
    details: "Aperfeiçoamento Linux"
    activities:
    - id: -4
      description: "Sintaxe Kotlin"
      details: "Aprendendo a sintaxe Kotlin"
      hours: 300
  - id: -1
    description: "TQI Kotlin Backend"
    details: "Java e Kotlin backend"
    activities:
    - id: -2
      description: "Java - POO"
      details: "Orientação a objetos com Java"
    - id: -1
      description: "Kotlin - POO"
      details: "Orientação a objetos com Kotlin"
  links:
  - rel: "self"
    href: "http://localhost:8081/bootcamp-tracker-dev/v1/developers"
  - rel: "developer-2"
    href: "http://localhost:8081/bootcamp-tracker-dev/v1/developers/-2"
- id: -1
  name:
    firstName: "José"
    middleName: "Carlos"
    lastName: "Costa"
  age: 29
  username: "josecc"
  email: "josecc@email.com"
  level: 2
  bootcamps:
  - id: -2
    description: "Linux Experience"
    details: "Aperfeiçoamento Linux"
    activities:
    - id: -4
      description: "Sintaxe Kotlin"
      details: "Aprendendo a sintaxe Kotlin"
      hours: 300
  - id: -1
    description: "TQI Kotlin Backend"
    details: "Java e Kotlin backend"
    activities:
    - id: -2
      description: "Java - POO"
      details: "Orientação a objetos com Java"
    - id: -1
      description: "Kotlin - POO"
      details: "Orientação a objetos com Kotlin"
  links:
  - rel: "self"
    href: "http://localhost:8081/bootcamp-tracker-dev/v1/developers"
  - rel: "developer-1"
    href: "http://localhost:8081/bootcamp-tracker-dev/v1/developers/-1"
* Connection #0 to host localhost left intact
```
![diagram](https://github.com/cesaugusto108/tqi-desafio-poo-dio/assets/93228693/db8552df-f45b-4f69-b1c4-c037b5d3acc8)
