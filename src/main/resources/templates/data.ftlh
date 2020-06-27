<#import "parts/common.ftlh" as c>


<@c.page>
<table class="table">

    <thead>
    <tr>
        <th scope="col">Датчик</th>
        <th scope="col">DevEui</th>

    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${devname}</td>
        <td>${deveui}</td>

    </tr>
    </tbody>
</table>

<table class="table">
    <thead>
    <tr>
        <th scope="col" >Данные</th>
        <th scope="col" >Отправка</th>
    </tr>
    </thead>
    <tbody>
    <#list dataList as data>
    <tr>
        <td> ${data}</td>
        <td>
            <form action="/senddata" method="get">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" value="${devname}" name="devname"/>
                <input type="hidden" value="${deveui}" name="deveui"/>
                <input type="hidden" value="${data}" name="data"/>
                <button class="btn btn-primary ml-2" type="submit">Отправить</button>
            </form>
        </td>

    </tr>
    <#else>
    <div class="card" style="width: 18rem;">
        <ul class="list-group list-group-flush">
            Данных нет
        </ul>
    </div>
    </#list>
    </tbody>
</table>
</@c.page>