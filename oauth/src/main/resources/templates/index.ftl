<#global base>${request.contextPath}</#global>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0">
    <title>首页</title>
</head>
<body>
<script>
    window.parent.postMessage({
        type: 'refresh',
        t: ''
    }, '*');
</script>
</body>
</html>
