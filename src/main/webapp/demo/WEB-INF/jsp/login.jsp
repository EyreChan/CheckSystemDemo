<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 登录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" type="image/icon" href="/static/ico/favicon.ico"> 
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

                <h1 class="logo-name">HIS</h1>

            </div>
            <h3>欢迎使用 医院门诊系统</h3>

            <form class="m-t" role="form" action="index.html">
                <div class="form-group">
                    <input id="ID" type="text" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input id="password" type="password" class="form-control" placeholder="密码" required="">
                </div>
                <button type="button" onclick="login()" class="btn btn-primary block full-width m-b">登 录</button>
                
                <p class="text-muted text-center"><a href="/log/goRegister">注册一个新账号</a>
                <br/><br/>
                 <select class="sub_button" id ="userType" name="userType">
					<option value="patient">病人</option>
					<option value="doctor">医生</option>
					<option value="dispenser">配药师</option>
					<option value="chemist">药剂师</option>
					<option value="collector">收费员</option>
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
			var ID = $('#ID').val();
			var password =$('#password').val();
			var userType=$('#userType').val();

			if(ID == "" || password == ""){
				layer.msg("请输入用户名和密码!");
			}
			
			$.ajax({
        		url: '/log/validate',
        		type: 'POST',
        		data: {
	        			'ID':ID,
	        			'password':password,
	        			'userType':userType
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
