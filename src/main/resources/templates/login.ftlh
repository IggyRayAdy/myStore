<head>
</head>
<body>
<h3> Authentication page </h3>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
</#if>

<form action="/login" method="post">
    <div><input type="text" name="username" placeholder="User name"/></div>
    <div><input type="password" name="password" placeholder="Password"/></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form>

<div>
    ${message?ifExists}
</div>

<a href="/registration"> Registration page</a>
</body>
</html>


