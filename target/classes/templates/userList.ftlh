<#import "parts/common.ftlh" as c>

<@c.page>
Список пользователей
<table class="table">
<thead>
    <tr>
        <th scope="col">Логин</th>
        <th scope="col">Имя</th>
        <th scope="col">Фамилия</th>
    </tr>
</thead>
<tbody>
<#list users as user>
    <tr>
        <td>${user.username}</td>
        <td>${user.name}</td>
        <td>${user.surname}</td>
        <td><a href="/user/${user.id}">Изменить</a></td>
    </tr>
</#list>
</tbody>
</table>
</@c.page>
