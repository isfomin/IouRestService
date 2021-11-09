# IouRestService
Solution for task 5 https://t.me/try2py/197

## REST API
### list of users
input:
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


### list of iou
input:
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


### list of summed iou
input
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


### add user
input:
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

### add iou
input:
```
POST http://localhost:8080/api/v1/user
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

### delete user
input:
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

### delete iou
input:
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
