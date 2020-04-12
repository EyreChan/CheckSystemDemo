<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	    <title>用户注册</title>
	
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
	                <h1 class="logo-name">DFMS</h1>
	            </div>
	            <h3>欢迎注册文档格式管理系统</h3>
	            <p>创建一个新账户</p>
	            <form class="m-t" role="form" action="login.html">
	                <div class="form-group">
	                    <input id="name" type="text" class="form-control" placeholder="请输入用户名">
	                </div>
	                <div class="form-group">
	                    <input id="phone" type="text" class="form-control" placeholder="请输入电话">
	                </div>
	                <div class="form-group">
	                    <input id="gender" type="text" class="form-control" placeholder="请输入性别">
	                </div>
	                <div class="form-group">
	                    <input id="age" type="text" class="form-control" placeholder="请输入年龄">
	                </div>
	                <div class="form-group">
	                    <input id="password" type="password" class="form-control" placeholder="请输入密码">
	                </div>
	                <div class="form-group">
	                    <input id="repassword" type="password" class="form-control" placeholder="请再次输入密码">
	                </div>
	                <button type="button" class="btn btn-primary block full-width m-b" onclick="register()">注 册</button>
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
	        	var name = $('#name').val();
	        	var phone = $('#phone').val();
	        	var gender = $('#gender').val();
	        	var age = $('#age').val();
	        	var password = $('#password').val();
	        	var repassword = $('#repassword').val();
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
		        			'name':name,
		        			'phone':phone,
		        			'gender':gender,	
		        			'age':age,
		        			'password':password,
	        			},
	        		dataType: 'JSON',
	        		success: function(res){
	        			if(res > 0){
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
