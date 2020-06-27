<#import "parts/common.ftlh" as c>


<@c.page>
<div class="form-group mb-2 mt-2">
    Список датчиков
</div>
<table class="table">
    <thead>
    <tr>
        <th scope="col">Название</th>
        <th scope="col" >DevEui</th>
    </tr>
    </thead>
    <tbody>
    <#list devices as devname, deveui>
    <tr>
        <td>${devname}</td>
        <td>${deveui}</td>
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