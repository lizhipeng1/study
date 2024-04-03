<#--form表单提交-->
<#global base>${request.contextPath}</#global>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0">
    <title>登录</title>
    <link rel="stylesheet" href="${base}/static/style/login.css">
</head>
<body>

<div class="login-container">
    <div class="account-header">

    </div>
    <div class="account-container">
        <div class="account-top">
            <div class="account-top-logo">
                (logo)管理系统
            </div>
            <div class="account-top-desc">某解决方案</div>
        </div>
        <div class="account-login">
            <form action="${base}/ssoLoginProcesses" method="POST">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-item">
                    <input type="text" class="input" id="username" name="username" value="admin" placeholder="请输入用户名">
                    <span class="icon icon-username">
                        <img src="${base}/static/images/icon/username.svg" alt="">
                    </span>
                </div>

                <div class="form-item">
                    <input type="password" class="input" id="password" name="password" value="abcABC@123" placeholder="请输入密码">
                    <span class="icon icon-password">
                        <img src="${base}/static/images/icon/password.svg" alt="">
                    </span>
                </div>
                <div class="form-item">
                    <input type="text" class="input" id="imageCode" name="imageCode" value="1" placeholder="请输入图片验证码">
                    <span class="icon icon-image-code">
                        <img src="${base}/static/images/icon/imageCode.svg" alt="">
                    </span>
                    <div class="imageCode">
                        <img src="${base}/login/imageCode" id="loginImgCode" style="width: 90px;height: 100%;">
                    </div>
                </div>
                <div class="login-submit">
                    <button type="button" id="login-btn" class="btn btn-primary btn-long btn-large">
                        <span>登录</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
    <div class="footer-container">
        <div class="footer-links">
            <a href="">官网</a>
            <a href="">吉林省教育厅</a>
            <a href="">长春市教育局</a>
        </div>
        <div class="footer-copyright">
            Copyright © 2020 嘉玉计算机软件研发工作室
        </div>
    </div>
</div>
<script src="${base}/static/js/jquery.min.js"></script>
<script>
    $(function () {
        var error = "";
        <#if Session.SPRING_SECURITY_LAST_EXCEPTION??>
        error = "${Session.SPRING_SECURITY_LAST_EXCEPTION.message}";
        </#if>
        if (error !== "") {
            alert(error);
        }
        $("#login-btn").on("click", function () {
            if ($("input#username").val().trim() === "") {
                $('input#username').parent(".form-item").find(".error-tip").remove();
                $('<div class="error-tip">请输入用户名</div>').appendTo($('input#username').parent(".form-item"));
            } else if ($("input#password").val().trim() === "") {
                $('input#password').parent(".form-item").find(".error-tip").remove();
                $('<div class="error-tip">请输入密码</div>').appendTo($('input#password').parent(".form-item"));
            } else if ($("input#imageCode").val().trim() === "") {
                $('input#imageCode').parent(".form-item").find(".error-tip").remove();
                $('<div class="error-tip">请输入图片验证码</div>').appendTo($('input#imageCode').parent(".form-item"));
            } else {
                $("form").submit();
            }
        })
        $("input").on("keypress", function (v, a) {
            var value = $(this).val();
            if (value !== undefined && value.trim() !== '') {
                $(this).parent(".form-item").find(".error-tip").remove();
            }
        })
        $("#loginImgCode").on("click", function () {
            $(this).attr("src", "${base}/login/imageCode?random=" + Math.random());
        })
    })
</script>
</body>
</html>
