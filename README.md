# IouRestService (Spring Boot Application)
Решение задачи 5 https://t.me/try2py/197

> Условие задачи:
> Четверо соседей по комнате имеют привычку занимать деньги друг у друга, но им трудно вспомнить, кто кому должен и сколько. Ваша задача состоит в том, чтобы реализовать простой > API (желательно следовать принципу REST, если знакомы с ним), который создаёт долговые расписки в виде POST-запросов и может предоставлять информацию о долгах с помощью GET-запросов.

## REST API
### Список пользователей
request:
```
GET http://localhost:8080/api/v1/user
request body: empty
``` 

output:
```
{
    "users": [
        {
            "id": 1,
            "name": "Andrey"
        },
        {
            "id": 2,
            "name": "Timofei"
        },
        {
            "id": 3,
            "name": "Jimmy"
        }
    ]
}
```


### Добавить пользователя
request:
```
POST http://localhost:8080/api/v1/user
request body: { "name": "Jimmy" }
``` 

output:
```
{
    "id": 3,
    "name": "Jimmy"
}
```


### Удалить пользователя
request:
```
DELETE http://localhost:8080/api/v1/user
request body: { "id": 3 }
``` 

output:
```
{
    "message": "success"
}
```


### Список долговых расписок
request:
```
GET http://localhost:8080/api/v1/iou
request body: empty
``` 

output:
```
{
    "notes": [
        {
            "id": 1,
            "lender": {
                "id": 1,
                "name": "Andrey"
            },
            "borrower": {
                "id": 2,
                "name": "Timofei"
            },
            "amount": 100.0
        },
        {
            "id": 2,
            "lender": {
                "id": 1,
                "name": "Andrey"
            },
            "borrower": {
                "id": 2,
                "name": "Timofei"
            },
            "amount": 120.5
        },
        {
            "id": 3,
            "lender": {
                "id": 1,
                "name": "Andrey"
            },
            "borrower": {
                "id": 2,
                "name": "Timofei"
            },
            "amount": 13.5
        }
    ]
}
```


### Список просуммированных долговых расписок
request
```
GET http://localhost:8080/api/v1/iou/summed
request body: empty
```

output:
```
{
    "notes": [
        {
            "lender": {
                "id": 1,
                "name": "Andrey"
            },
            "borrower": {
                "id": 2,
                "name": "Timofei"
            },
            "amount": 234.0
        }
    ]
}
```


### Добавить долговую расписку
request:
```
POST http://localhost:8080/api/v1/iou
request body: {
    "lender": 1,
    "borrower": 2,
    "amount": 13.50
}
``` 

output:
```
{
    "id": 3,
    "lender": {
        "id": 1,
        "name": "Andrey"
    },
    "borrower": {
        "id": 2,
        "name": "Timofei"
    },
    "amount": 13.5
}
```


### Удалить долговую расписку
request:
```
DELETE http://localhost:8080/api/v1/iou
request body: { "id": 3 }
```

output:
```
{
    "message": "success"
}
```
