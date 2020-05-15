<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 登录</title>
    
    <link href="/static/css/bootstrap.min.css?v=3.3.6" type="text/css" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" type="text/css" rel="stylesheet">
    
    <link href="/static/css/animate.css" type="text/css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" type="text/css" rel="stylesheet">
    
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>

<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">DFS</h1>
            </div>
            <h3>欢迎使用 文档格式审查分析系统</h3>

            <form class="m-t" role="form" action="index.html">
                <div class="form-group">
                    <input id="userName" type="text" class="form-control" placeholder="用户名">
                </div>
                <div class="form-group">
                    <input id="password" type="password" class="form-control" placeholder="密码">
                </div>
                <button type="button" onclick="login()" class="btn btn-primary block full-width m-b">登 录</button>
                <p class="text-muted text-center"><a href="/log/goRegister">注册一个新账号</a>
                </p>
            </form>
        </div>
    </div>

    <!-- 全局js -->
    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="/static/layer/layer.js"></script>
    <script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
	<script>
		function login(){
			var userName = $('#userName').val();
			var password =$('#password').val();

			if(userName == "" || password == ""){
				layer.msg("请输入用户名和密码!");
			}
			
			$.ajax({
        		url: '/log/validate',
        		type: 'POST',
        		data: {
	        			'name':userName,
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
