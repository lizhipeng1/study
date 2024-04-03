<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>跳转</title>
</head>
<body>
<script>
    <#if accessToken??>
    window.parent.postMessage({
        type: 'tokenSet',
        t: '${accessToken?default('')}'
    }, '${state}');
    </#if>
</script>
</body>
</html>
