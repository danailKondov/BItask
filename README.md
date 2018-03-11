## Практическое задание в рамках стажировки в компании Bell Integrator

#### Автор: Д.Кондов
#### E-mail: dkondov@yandex.ru

#### Даны: 

* организации с офисами и сотрудниками
* пользователи базы данных и логика регистрации и активации аккаунтов
* справочники с типами документов и кодами стран

#### REST архитектура:

Все описанные возвращаемые типы данных находятся в параметре data. В случае ошибки возвращается параметр error.

Например, в случае, если запрос корректно отработает, бэк возвращает в body ответа:

`{
    “data”:{
        //то, что в параметре out
    }
 }`

А в случае ошибки возвращает 

`{
    “error”:”текст ошибки”
}`

Везде, где не написан метод, использовать POST
 
1. ***api/register***

    In:

    `{
  “login”:””,
  “password”:””,
  “name”:””
}`

    Out:

    `{
    “result”:”success”
}`

2. ***api/activation?code=<код активации, который был отправлен пользователю>***
method:GET

3. api/login

    In:

    `{
  “login”:””,
  “password”:””
}`

    Out:

    `{
    “result”:”success”
}`


4. ***api/organization/list***

    In (фильтр):

    `{
  “name”:””, //обязательный параметр
  “inn”:””,
  “isActive”:””
}`

    Out:

    `[
  {
    “id”:””,
    “name”:””,
    “isActive”:”true”
  },
  ...
]`

5. ***api/organization/{id:.+}***

    method:GET

    Out:

    `{
  “id”:””,
  “name”:””,
  “fullName”:””,
  “inn”:””,
  “kpp”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}`

6. ***api/organization/update***

    In:
    
    `{
  “id”:””,
  “name”:””,
  “fullName”:””,
  “inn”:””,
  “kpp”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}`

    Out:
    
    `{
    “result”:”success”
}`

6. ***api/organization/save***

    In:

    `{
  “name”:””,
  “fullName”:””,
  “inn”:””,
  “kpp”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}`

    Out:
    
    `{
    “result”:”success”
}`

7. ***api/office/list***

    In (фильтр):

    `{
  “orgId”:””, //обязательный параметр
  “name”:””,
  “phone”:””,
  “isActive” 
}`

    Out:

    `[
  {
    “id”:””,
    “name”:””,
    “isActive”:”true”
  },
  ...
]`

8. ***api/office/{id:.+}***

    method:GET

    Out:
    
    `{
  “id”:””,
  “name”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}`

9. ***api/office/update***

    In:

    `{
  “id”:””,
  “name”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}`

    Out:
    
    `{
    “result”:”success”
}`

10. ***api/office/save***

    In:

    `{
  “name”:””,
  “address”:””,
  “phone”,””,
  “isActive”:”true”
}`

    Out:

   `{
    “result”:”success”
}`

11. ***api/user/list***

    In (фильтр):

    `{
  “officeId”:””, //обязательный параметр
  “firstName”:””,
  “lastName”:””,
  “middleName”:””,
  “position”,””,
  “docCode”:””,
  “citizenshipCode”:””
}`

    Out:

    `{
  “id”:””,
  “firstName”:””,
  “secondName”:””,
  “middleName”:””,
  “position”:””
}`

12. ***api/user/{id:.+}***

    method:GET

    Out:
    
    `{
  “id”:””,
  “firstName”:””,
  “secondName”:””,
  “middleName”:””,
  “position”:””
  “phone”,””,
  “docName”:””,
  “docNumber”:””,
  “docDate”:””,
  “citizenshipName”:””,
  “citizenshipCode”:””,
  “isIdentified”:”true”
}`

13. ***api/user/update***

    In:

    `{
  “id”:””,
  “firstName”:””,
  “secondName”:””,
  “middleName”:””,
  “position”:””
  “phone”,””,
  “docName”:””,
  “docNumber”:””,
  “docDate”:””,
  “citizenshipName”:””,
  “citizenshipCode”:””,
  “isIdentified”:”true”
}`

    Out:

    `{
    “result”:”success”
}`


14. ***api/user/save***

    In:

    `{
  “firstName”:””,
  “secondName”:””,
  “middleName”:””,
  “position”:””
  “phone”,””,
  “docCode”:””,
  “docName”:””,
  “docNumber”:””,
  “docDate”:””,
  “citizenshipName”:””,
  “citizenshipCode”:””,
  “isIdentified”:”true”
}`



15. ***api/docs***

    Справочники:

    `[
  {
    “name”:“Паспорт гражданина РФ”,
    “code”:”21”
  },
  ...
]`



16. ***api/countries***

    Виды документов, удостоверяющих личность физического лица

    `[
  {
    “name”:“Российская Федерация”,
    “code”:”643”
  },
  ...
]`


Репозиторий с исходным примером:
-
git - https://github.com/azEsm/empty_project

Swagger UI
-
Пример обращения http://localhost:8888//swagger-ui.html