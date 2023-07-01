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

```
$ curl -v --user "devadmin:1234" localhost:8081/bootcamp-tracker-dev/developers | json_pp
* Connected to localhost (127.0.0.1) port 8081 (#0)
* Server auth using Basic with user 'devadmin'
> GET /bootcamp-tracker-dev/developers HTTP/1.1
> Host: localhost:8081
> Authorization: Basic ZGV2YWRtaW46MTIzNA==
> User-Agent: curl/8.1.1
> Accept: */*
> 
< HTTP/1.1 200 
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 0
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 01 Jul 2023 14:55:45 GMT
< 
{ [498 bytes data]
100   491    0   491    0     0   6206      0 --:--:-- --:--:-- --:--:--  6215
* Connection #0 to host localhost left intact
[
   {
      "age" : 29,
      "bootcamps" : [
         {
            "activities" : [
               {
                  "date" : null,
                  "description" : "Orientação a objetos",
                  "details" : "Orientação a objetos com Kotlin",
                  "hours" : null,
                  "id" : -1
               },
               {
                  "date" : null,
                  "description" : "Sintaxe Java",
                  "details" : "Aprendendo a sintaxe Java",
                  "hours" : 300,
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
      "name" : {
         "firstName" : "José",
         "lastName" : "Costa",
         "middleName" : "Carlos"
      }
   }
]
```

![diagram](https://github.com/cesaugusto108/tqi-desafio-poo-dio/assets/93228693/12495049-1fd9-4d81-a7f3-836c7a671066)
