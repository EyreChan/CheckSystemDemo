<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta name="renderer" content="webkit">
	
	    <title>用户主页</title>
	
	    <!--[if lt IE 9]>
	    <meta http-equiv="refresh" content="0;ie.html" />
	    <![endif]-->
	
	    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	    <link href="/static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
	    <link href="/static/css/animate.css" rel="stylesheet">
	    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
	</head>
	<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
	    <div id="wrapper">
	        <!--左侧导航开始-->
	        <nav class="navbar-default navbar-static-side" role="navigation">
	            <div class="sidebar-collapse">
	                <ul class="nav" id="side-menu">
	                    <li class="nav-header">
	                        <div class="dropdown profile-element">
	                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
	                                <span class="clear">
	                                    <span class="block m-t-xs" style="font-size:20px;">
	                                        <strong class="font-bold">文档格式审查系统</strong>
	                                    </span>
	                                </span>
	                            </a>
	                        </div>
	                    </li>   
	                    <li>
	                        <a class="J_menuItem" href="/user/user_manageTemplate">
		                        <span class="nav-label">模板管理</span>
	                        </a>
	                    </li>
	                    <li>
	                        <a class="J_menuItem" href="/user/user_checkFormat">
		                        <span class="nav-label">格式审查</span>
	                        </a>
	                    </li>
	                    <li>
	                        <a class="J_menuItem" href="/user/user_getResult">
		                        <span class="nav-label">审查结果</span>
	                        </a>
	                    </li>
	                    <li>
	                        <a class="J_menuItem" onclick="loginOut()">
		                        <span class="nav-label">退出登录</span>
	                        </a>
	                    </li>
	                </ul>
	            </div>
	        </nav>
	        <!--左侧导航结束-->
	        <!--右侧部分开始-->
	        <div id="page-wrapper" class="gray-bg dashbard-1">
	            <div class="row J_mainContent" id="content-main">
	                <iframe id="J_iframe" width="100%" height="100%" src="/user/user_manageTemplate" frameborder="0" seamless></iframe>
	            </div>
	        </div>
	        <!--右侧部分结束-->
	    </div>
	
	    <!-- 全局js -->
	    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
	    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
	    <script src="/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	    <script src="/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	    <script src="/static/js/plugins/layer/layer.min.js"></script>
	
	    <!-- 自定义js -->
	    <script src="/static/js/hAdmin.js?v=4.1.0"></script>
	    <script type="text/javascript" src="/static/js/index.js"></script>
	
	    <!-- 第三方插件 -->
	    <script src="/static/js/plugins/pace/pace.min.js"></script>
	    <script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
	</body>
	<script type="text/javascript">
		function loginOut(){
			layer.confirm('确认退出吗？', {
		  		  btn: ['确定','取消'] //按钮
		  		}, function(){
		  			layer.msg('退出成功，前往登录界面！');
		  			setTimeout(function(){
		  	  			$.cookie('loginName', null, {path: '/'});
		  	  			$.cookie('loginPassword', null, {path: '/'});
						window.location.href="/log/logout";
					},1000);
		  		}, function(){
		  	});
		}
	</script>
</html>