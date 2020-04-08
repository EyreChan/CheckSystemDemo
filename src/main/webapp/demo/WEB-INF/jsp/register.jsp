<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 注册</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
            <div>

                <h1 class="logo-name">HIS</h1>

            </div>
            <h3>欢迎注册 医院门诊系统</h3>
            <p>创建一个新账户</p>
            <form class="m-t" role="form" action="login.html">
                <div class="form-group">
                    <input id="ID" type="text" class="form-control" placeholder="请输入病人ID" required="">
                </div>
                <div class="form-group">
                    <input id="name" type="text" class="form-control" placeholder="请输入病人姓名" required="">
                </div>
                <div class="form-group">
                    <input id="phone" type="text" class="form-control" placeholder="请输入病人电话" required="">
                </div>
                
                 <div class="form-group">
                    <input id="sex" type="text" class="form-control" placeholder="请输入性别" required="">
                </div>
                <div class="form-group">
                    <input id="password" type="password" class="form-control" placeholder="请输入密码" required="">
                </div>
                <div class="form-group">
                    <input id="repassword" type="password" class="form-control" placeholder="请再次输入密码" required="">
                </div>
                <button type="button" class="btn btn-primary block full-width m-b" onclick="register()">注 册</button>

                <p class="text-muted text-center"><small>已经有账户了？</small><a href="/log/login">点此登录</a>
                </p>

            </form>
        </div>
    </div>

    <!-- 全局js -->
    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
    <!-- iCheck -->
    <script src="/static/js/plugins/iCheck/icheck.min.js"></script>
    <script src="/static/layer/layer.js"></script>
    <script>
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
        
        function register(){
        	var ID = $('#ID').val();
        	var name = $('#name').val();
        	var phone = $('#phone').val();
        	var sex = $('#sex').val();
        	var password = $('#password').val();
        	var repassword = $('#repassword').val();
        	var sex = $('#sex').val();
        	if($('.form-control').val() == ""){
        		layer.msg("请确保信息填写完整");
        	}
        	console.log(password+" / "+repassword);
        	if(password !== repassword){
        		layer.msg("请确保两次密码输入一致");
        	}
        	$.ajax({
        		url: '/log/doRegister',
        		type: 'POST',
        		data: {
	        			'ID':ID,
	        			'name':name,
	        			'phone':phone,
	        			'sex':sex,	
	        			'password':password,
        			},
        		dataType: 'JSON',
        		success: function(res){
        			if(res >0){
        				layer.msg("注册成功！前往登录界面！");
        				setTimeout(function(){
    						window.location.href="/log/login";
    					},1000);
        			}else{
        				layer.msg("注册失败");
        			}
        		},
        		error: function(res){
        			layer.msg("访问后台失败");
        		}
        	});
        }
    </script>

    
    

</body>

</html>
