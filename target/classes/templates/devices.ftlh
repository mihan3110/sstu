<#import "parts/common.ftlh" as c>


<@c.page>


<div>
    <form class="form-inline mt-5" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group mb-2">

            <label class="staticEmail">Добавить датчик :</label>

            <input class="form-control ml-2" type="text" name="name" placeholder="Введите номер"/>
            <br>
            <input class="form-control ml-2" type="text" name="address" placeholder="Введите адрес автоотправки  отправки информации"/>
            <button class="btn btn-primary ml-2" type="submit">Добавить</button>
        </div>

    </form>
</div>
<div class="form-group mb-2 mt-2">
    Список датчиков
</div>
<table class="table">
<thead>
<tr>
    <th scope="col">Название</th>
    <th scope="col" >DevEui</th>
    <th scope="col" >Адрес автоотправки</th>
    <th scope="col" >Данные</th>
    <th scope="col" >Удаление</th>
</tr>
</thead>
<tbody>
<#list devices as devinfo>
<tr>
    <td> ${devinfo.getName()}</td>
    <td>
        ${devinfo.getDeveui()}

    </td>
    <td>
        <#if devinfo.getAddress()??>
        ${devinfo.getAddress()}
    </#if>
    </td>
    <td>
        <form action="/devices/${devinfo.getDeveui()}" method="get">
            <input type="hidden" value="${devinfo.getDeveui()}" name="deveui"/>
            <button class="btn btn-primary ml-2" type="submit">Показать</button>
        </form>
    </td>
    <td>
        <form action="/devices/delete" method="get">
            <input type="hidden" value="${devinfo.getDeveui()}" name="deveui"/>
            <button class="btn btn-primary ml-2" type="submit">Удалить</button>
        </form>
    </td>

</tr>
<#else>
<div class="card" style="width: 18rem;">
    <ul class="list-group list-group-flush">
        Датчики отсутствуют
    </ul>
</div>
</#list>
</tbody>
</table>
</@c.page>