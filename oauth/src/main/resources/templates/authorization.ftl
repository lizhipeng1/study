<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
<h1>clientId：${clientId}</h1>
<h1>scopeName：${scopeName}</h1>
<#list scopes as item>
    <h2>scope：${item}</h2>
</#list>
<form action="/oauth/authorize" method="post">
    <input type="hidden" name="user_oauth_approval" value="true">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <#list scopes as item>
        <input type="hidden" name="scope.${item}" value="true">
    </#list>
    <input type="submit" value="确认授权" />
</form>
</body>
</html>
