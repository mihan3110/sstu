<#macro login path>
<br/>
<a href="/registration">Зарегистрироваться</a>
<form action="${path}" method="post">
<div class="form-group">
    <label class="col-sm-2 col-form-label">Логин :</label>
    <div class="col-sm-6">
        <input type="text" name="username" class="form-control" placeholder="Введите пароль"/>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-form-label">Пароль :</label>
    <div class="col-sm-6">
        <input type="password" name="password" class="form-control" placeholder="Введите пароль"/>
    </label>
</div>
</div>
<input type="hidden" name="_csrf" value="${_csrf.token}"/>
<button class="btn btn-primary" type="submit">Войти</button>

        </form>
        </#macro>

<#macro logout>
<form action="/logout" method="post">
<input type="hidden" name="_csrf" value="${_csrf.token}"/>
<button class="btn btn-primary"  type="submit">Выйти</button>
</form>
        </#macro>

<#macro registration>
<form action="/registration" method="post">
<input type="hidden" name="_csrf" value="${_csrf.token}"/>
<div class="form-group">
    <label class="col-sm-2 col-form-label">Логин :</label>
    <div class="col-sm-6">
        <input type="text" name="username"/>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-form-label">Имя :</label>
    <div class="col-sm-6">
        <input type="text" name="name"/>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-form-label">Фамилия :</label>
    <div class="col-sm-6">
        <input type="text" name="surname"/>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-form-label">Пароль:</label>
    <div class="col-sm-6">
        <input type="password" name="password"/>
    </div>
</div>
<button class="btn btn-primary" type="submit">Зарегистрироваться</button>
</form>
        </#macro>