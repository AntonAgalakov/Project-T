# Time Tracker

*Backend сервиса Многопользовательский тайм трекер*

### Типы запросов
- [ ] Создать пользователя
- [ ] Изменить данные пользователя
- [ ] Начать отсчет времени по задачи X
- [ ] Показать все трудозатраты пользователя Y за период N..M в виде связного списка Задача - Сумма затраченного времени в виде (чч:мм), сортировка по времени поступения в трекер
- [ ] Показать все временные интерваля занятые работой за период N..M в виде связного списка Задача - Временной интервал (число чч:мм)
- [ ] Показать сумму трудозатрат по всем задачам пользователя Y за период N..M
- [ ] Удалить всю информацию о пользователе Z
- [ ] Очистить данные трекинга пользователя Z


### Документация

Сущность Task состоит {
	
	Поле Id (Задается автоматически)
	Поле status (текстового формата предел 45)
	Поле description (текстового формата предел 255)
	Поле data_start (Не обновляемое поле, формат даты dd-MM-YYYY mm:ss)
	Поле data_end (формат даты dd-MM-YYYY mm:ss)

}

#### Управление
##### REST
Управление информацией основывается целиком и полностью на протоколе передаци данных HTTP.
Действия над данными производятся с помощью методов:
1. GET (получить)
2. POST (добавить)
3. PUT (обновить)
4. DELETE (удалить)

Примеры:

Просмотр всех задач
`GET /task`


Добавление задачи
`POST /task`


Просмотр задачи под id 1
`GET task/1`


Обновление задачи под id 1
`PUT /task/1`


Удаление задачи под id 1
`DEL /task/1`


##### Добавление задачи
Для того чтобы дабавить задачу, сначала ознакомьтесь с сущностью Task (чтобы узнать какие поля в нее входят).
Далее с помощью метода POST добавляем задачу по указанному ниже примеру (для удобства используйте Postman). 

Пример:

```
{
    "description": "test task",
    "status": "start",
    "dateStart": "19-03-2020",
    "dateEnd": null
}
```
