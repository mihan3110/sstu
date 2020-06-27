<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
        >
<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name=user.getName()
        isAdmin = user.isAdmin()
        >
<#else>
    <#assign
          name="Неизвестный"
            isAdmin= false
            >
</#if>