<#import "parts/common.ftlh" as c>

<#include "parts/authentication.ftlh">

<@c.page>
    <#if isAdmin || isUser>
        <div>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" value="Logout"/>
            </form>
        </div>
    </#if>

    <div>Hello, ${name},it's myStore.</div>

    <form action="/">
        <input type="submit" value="Main page"/>
    </form>
</@c.page>
