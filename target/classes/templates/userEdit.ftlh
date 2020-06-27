<#import "parts/common.ftlh" as c>

<@c.page>
<div class="form-group mb-2 mt-2">
Управление пользователем
</div>
<form action="/user" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>

    <input type="text" value="${user.username}" name="username"/>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Роль</th>
        </tr>
        </thead>
        <tbody>
        <#list roles as role>
        <tr>
            <td><label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label></td>
        </tr>
        </#list>
    </tbody>
    </table>
        <input type="hidden" value="${user.id}" name="userId"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary ml-2" type="submit">Сохранить</button>
</form>
</@c.page>