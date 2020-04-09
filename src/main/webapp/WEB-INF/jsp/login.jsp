<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 登录</title>
    
    <link rel="stylesheet" type="text/css" href="/static/css/index.css" />
    
    
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>

<body>
	<img class="bgone" src="/static/images/1.jpg" />
	<img class="pic" src="/static/images/a.png" />

	<div class="table">
		<div class="wel">文档格式审查系统登录</div>
		<div class="wel1">DOCUMENT FORMAT CHECK SYSTEM LOGIN</div>
		<div class="user">
			<div id="yonghu" style=""><img src="/static/images/yhm.png" /></div>
			<input id="name" type="text" placeholder="用户名" />
		</div>			
		<div class="password">
			<div id="yonghu"><img src="/static/images/mm.png" /></div>
			<input id="password" type="password" placeholder="密码"/>
		</div>
		<button type="button" onclick="login()" class="btn">登 录</button>
        <p>还没有账号？<a href="/log/goRegister">点此注册</a>
        </p>
	</div>

    <!-- 全局js -->
    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="/static/layer/layer.js"></script>
    <script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
	<script>

		function login(){
			var name = $('#name').val();
			var password =$('#password').val();

			if(name == "" || password == ""){
				layer.msg("请输入用户名或密码!");
			}
			
			$.ajax({
        		url: '/log/validate',
        		type: 'POST',
        		data: {
	        			'name':name,
	        			'password':password
        			},
        		dataType: 'text',
        		success: function(result){
        			window.location.href = result;
        		},
        		error: function(res){
        			layer.msg("登录失败");
        		},
        	});
		}
	</script>
</body>

</html>
