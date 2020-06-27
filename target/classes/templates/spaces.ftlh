<#import "parts/common.ftlh" as c>


<@c.page>


<div class="mt-5">
    <form class="form-inline" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group mb-2">

            <label class="staticEmail">Добавить группу :</label>

            <input class="form-control ml-2" type="text" name="name" placeholder="Введите название"/>

            <button class="btn btn-primary ml-2" type="submit">Добавить</button>
        </div>
    </form>
</div>
<div class="form-group mb-2 mt-2">
    Группы датчиков
</div>
<table class="table">

    <thead>
    <tr>
        <th scope="col">Группа датчиков</th>
        <th scope="col">Датчики</th>
        <th scope="col">Удаление</th>
    </tr>
    </thead>
    <tbody>
    <#list spaces as idspace, name>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <tr>
        <td>${name}</td>
        <td>
            <form action="/spaces/${idspace}" method="get">
                <input type="hidden" value="${idspace}" name="idspace"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button class="btn btn-primary ml-2" type="submit">Список</button>
            </form>
        </td>
        <td>
            <form action="/spaces/delete" method="get">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <input type="hidden" value="${idspace}" name="idspace"/>
                <button class="btn btn-primary ml-2" type="submit">Удалить</button>
            </form>
        </td>
    </tr>




    </ul>
    </div>
    <#else>
    <div class="card" style="width: 18rem;">
        <ul class="list-group list-group-flush">
            Группы датчиков отсутствуют
        </ul>
    </div>
    </#list>
</@c.page>