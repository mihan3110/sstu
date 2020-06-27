<#import "parts/common.ftlh" as c>


<@c.page>
<table class="table">

    <thead>
    <tr>
        <th scope="col">Датчик</th>
        <th scope="col">DevEui</th>
        <th scope="col">Данные</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>${devname}</td>
            <td>${deveui}</td>
            <td>${data}</td>
        </tr>
    </tbody>
</table>
<form class="form-inline mt-5" action="/senddata" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="form-group mb-2">
        <input class="form-control ml-2" type="text" name="address1" placeholder="Введите начало адреса"/><a class="ml-2">?data=${data}</a>
        <input class="staticEmail" type="hidden" name="address2" value="?data=${data}"/>
        <button class="btn btn-primary ml-2" type="submit">Отправить</button>
    </div>

</form>

</@c.page>