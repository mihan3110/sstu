<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
<a class="navbar-brand" href="/">Lora</a>
<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
</button>
<div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="/">Домой</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/spaces">Группы датчиков</a>
        </li>
        <#if isAdmin>
        <li class="nav-item">
            <a class="nav-link" href="/user">Пользователи</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/index" href="">Новый датчик</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/alldevices">Все датчики</a>
        </li>
    </#if>
</ul>
<div class="navbar-tex mr-3">${name}</div>
<@l.logout />
</div>
        </nav>
