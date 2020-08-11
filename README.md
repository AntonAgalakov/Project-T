<p align="right">Обновлено:11 августа 2020 17:12<br>Исправлен bug даты</p>


# Project-T

*Backend сервиса Многопользовательский тайм трекер*

### Типы запросов
- Создать пользователя
- Изменить данные пользователя
- Начать отсчет времени по задачи X
- Показать все трудозатраты пользователя Y за период N..M в виде связного списка Задача - Сумма затраченного времени в виде (чч:мм), сортировка по времени поступения в трекер
- Показать все временные интерваля занятые работой за период N..M в виде связного списка Задача - Временной интервал (число чч:мм)
- Показать сумму трудозатрат по всем задачам пользователя Y за период N..M
- Удалить всю информацию о пользователе Z
- Очистить данные трекинга пользователя Z


### Документация

#### Содержание
0. [Запуск](#id-section0) 
1. [Сущности](#id-section1) 
2. [Управление](#id-section2)
- 2.1 [Пример добавление пользователя](#id-section3) 
- 2.2 [Пример добавление задачи](#id-section4) 
- 2.3 [Пример просмотр всех временных интервалов занятых работой в период...](#id-section5) 

<div id='id-section0'/>

##### Запуск
Склонируйте репозиторий и добавьте (username и password от mysql) в application.properties

<div id='id-section1'/>

Данный проект состоит из двух сущностей: User(пользователь) и Task(задача).

Сущность пользователь состоит из 

```
User {

	Поле id (задается автоматически)
	Поле name (текстового формата)

}
```


Сущьность задача состоит из

```
Task {
	
	Поле id (задается автоматически)
	Поле status (текстового формата)
	Поле description (текстового формата)
	Поле data_start (формат даты dd-MM-yyyy hh:mm)
	Поле data_end (формат даты dd-MM-yyyy hh:mm)

}
```

<div id='id-section2'/>

#### Управление

##### REST
Управление информацией основывается целиком и полностью на протоколе передаци данных HTTP.
Действия над данными производятся с помощью методов:
1. GET (получить)
2. POST (добавить)
3. PUT (обновить)
4. DELETE (удалить)

Примеры:

###### пользователи
Просмотр всех пользователей
`GET /user`


Добавление пользователя
`POST /user`


Просмотр пользователя под id 1
`GET /user/1`


Обновление пользователя под id 1
`PUT /user/1`


Удаление пользователя под id 1 (При удалении пользователя стираются и все его задачи)
`DELETE /user/1`

<div id='id-section3'/>

##### Добавление пользователя
Для того чтобы дабавить задачу, сначала ознакомьтесь с сущностью User (чтобы узнать какие поля в нее входят).
Далее с помощью метода POST добавляем пользователя по указанному ниже примеру (для удобства используйте Postman). 

Пример: 

```
{
    "name": "Anton"
}
```

###### задачи
Просмотр всех задач 
`GET user/{user_id}/task`


Обратите внимание, что есть два способа добавления задачи.
При первом добавлении дату и время можно не указывать.
Во втором случае поставится дата и время создания задачи автоматически.

Добавление задачи
`POST user/{user_id}/task`

Добавление задачи (с автоматическим выставлением времени и статуса = "Start")
`POST user/{user_id}/task/start`

Просмотр задачи под id 1
`GET user/{user_id}/task/1`

Завершить задачу (Автоматически выставляется время, и статус = "Final")
`PUT user/{user_id}/task/{task_id}/final`

Обновление задачи под id 1
`PUT user/{user_id}/task/1`

Удаление задачи под id 1
`DELETE user/{user_id}/task/1`

Удаление всех задач пользователя
`DELETE user/{user_id}/task`

Просмотр всех* трудозатрат пользователя за период N..M (формат даты: dd-MM-yyyy)
`GET user/{user_id}/task/works/{fromDay}/{toDay}` 

Просмотр всех* временных интервалов занятых работой за период N..M (формат даты: dd-MM-yyyy)
`GET user/{user_id}/task/intervals_works/{fromDay}/{toDay}` 

Узнать сумму трудозатрат по всем* задачам за период N..M (формат даты: dd-MM-yyyy)
`GET user/{user_id}/task/work_time/{fromDay}/{toDay}` 

	 
<div id='id-section4'/>

##### Добавление задачи
Для того чтобы дабавить задачу, сначала ознакомьтесь с сущностью Task (чтобы узнать какие поля в нее входят).
Далее с помощью метода POST добавляем задачу по указанному ниже примеру (для удобства используйте Postman). 

Пример:

```
{
    "description": "make a laboratory",
    "status": "statr",
    "dateStart": "03-09-2020 12:04"
}
```

<div id='id-section5'/>

##### Показать все временные интервалы занятые работой за период N..M
Пример:
`GET user/15/task/intervals_works/01-01-2020/01-03-2020`


##### Требования
- Ubuntu 18.04
- Java 8
- MySQL 5.7+
 
 Не обязательно:
- Postman
- GataGrid
- Intellij Idea

________________
\* под ВСЕХ понимаются задачи, которые завершены, т.е. имеют начальную и конечную даты.
