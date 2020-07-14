<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
<#--    order = user.getCart.getActualOrder()-->
    isAdmin = user.isAdmin()
    isUser = user.isUser()
    >
<#else>
    <#assign
    name = "Guest"
    isAdmin = false
    isUser = false
    >
</#if>