<head>
</head>
<body>
<h3> Registration page </h3>

<form action="/registration" method="post">
    <div> Username: <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                           placeholder="User Name"/>
        <#if usernameError??>
            <i>${usernameError}</i>
        </#if>
    </div>
    <div> Password: <input type="password" name="password" placeholder="Password"/>
        <#if passwordError??>
            <i>${passwordError}</i>
        </#if>
    </div>
    <div> Password: <input type="password" name="password2" placeholder="Retype password"/>
        <#if password2Error??>
            <i>${password2Error}</i>
        </#if>
    </div>
    <div> Email: <input type="Email" name="email" value="<#if user??>${user.email}</#if>"
                        placeholder="johndou@gmail.com"/>
        <#if emailError??>
            <i>${emailError}</i>
        </#if>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form>

<div>
    ${message?ifExists}
</div>

</body>
</html>