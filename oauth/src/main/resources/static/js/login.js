$(function () {
    layui.use('layer', function () {
        var layer = layui.layer;
        var loginFcn = function () {
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
                var layerIndex = -1;
                $.ajax({
                    url: base + '/ssoLoginProcesses',
                    type: 'POST',
                    // contentType: 'application/json;charset=UTF-8',
                    beforeSend: function (request) {
                        request.setRequestHeader("X-CSRF-TOKEN", token);
                        request.setRequestHeader("imageCode", $("input#imageCode").val().trim());
                        layerIndex = layer.load();
                    },
                    data: {
                        username: $("input#username").val().trim(),
                        password: encrypt($("input#password").val().trim()),
                        'remember-me': $("input[name='remember-me']:checked").val()
                    },
                    success: function (data) {
                        layer.close(layerIndex);
                        if (data.respCode == "0000") {
                            layer.load();
                            window.location.href = data.respMsg
                        } else {
                            $("#loginImgCode").click();
                            layer.alert(data.respMsg, {icon: 2});
                            if (data.respMsg === '用户名或密码错误') {
                                $("input#password").val('')
                            }
                        }
                    }
                })
            }
        }
        $("#login-btn").on("click", loginFcn)
        $("input").on("keypress", function (event) {
            var value = $(this).val();
            if (value !== undefined && value.trim() !== '') {
                $(this).parent(".form-item").find(".error-tip").remove();
            }
            /* if (event.keyCode == "13") {
                loginFcn()
            }*/
        })
        $("#loginImgCode").on("click", function () {
            $(this).attr("src", base + "/login/imageCode?random=" + Math.random());
        })
    });
})
