<#global base>${request.contextPath}</#global>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0">
    <title>全优学堂-辅导机构全链路解决方案</title>
    <link rel="stylesheet" type="text/css" href="${base}/static/style/login.css">
    <link type="text/css" href="${base}/static/js/layui/css/modules/layer/default/layer.css">
</head>
<body>

<div class="login-container">
    <div class="top-logo">
        <img src="${base}/static/images/logo-jiayu.png" alt="嘉玉工作室">
    </div>
    <div class="account-header">

    </div>
    <div class="account-container">
        <div class="account-top">
            <div class="account-top-logo">
                <img src="${base}/static/images/logo-qyxt.png" alt="全优学堂">
            </div>
            <div class="account-top-desc">辅导机构全链路解决方案</div>
        </div>
        <div class="account-login">
            <form action="${base}/ssoLoginProcesses" method="POST" onsubmit="return false;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-item">
                    <input type="text" class="input" id="username" name="username" value="" placeholder="请输入用户名">
                    <span class="icon icon-username">
                        <img src="${base}/static/images/icon/username.svg" alt="">
                    </span>
                </div>

                <div class="form-item">
                    <input type="password" class="input" id="password" name="password" value=""
                           placeholder="请输入密码">
                    <span class="icon icon-password">
                        <img src="${base}/static/images/icon/password.svg" alt="">
                    </span>
                </div>
                <div class="form-item" style="margin-bottom: 0px;">
                    <input type="text" class="input" id="imageCode" name="imageCode" value="" placeholder="请输入图片验证码">
                    <span class="icon icon-image-code">
                        <img src="${base}/static/images/icon/imageCode.svg" alt="">
                    </span>
                    <div class="imageCode">
                        <img src="${base}/login/imageCode" id="loginImgCode" style="width: 90px;height: 100%;">
                    </div>
                </div>
                <div class="form-item">
                    <div class="remember-me-container">
                        <input type="checkbox" id="rememberMe" name="remember-me"/>
                        <label for="rememberMe">7日免登陆</label>
                    </div>
                </div>
                <div class="login-submit">
                    <button type="submit" id="login-btn" class="btn btn-primary btn-long btn-large">
                        <span>登录</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
    <div class="footer-container">
        <#--<div class="footer-links">
            <a href="">官网</a>
            <a href="">吉林省教育厅</a>
            <a href="">长春市教育局</a>
        </div>-->
        <div class="footer-copyright">
            Copyright © 2020 嘉玉计软件研发工作室 jljiayu.cn All Rights Reserved <a href="https://beian.miit.gov.cn/">吉ICP备2020004998号-1</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${base}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${base}/static/js/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/crypto-js/min.js"></script>
<script type="text/javascript" src="${base}/static/js/crypto-js/util.js"></script>
<script>
    var base = "${base}"
    var token = "${_csrf.token}"
</script>
<script type="text/javascript" src="${base}/static/js/login.js"></script>
</body>
</html>
