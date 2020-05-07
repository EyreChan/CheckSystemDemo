<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
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
	</head>
	<body class="gray-bg">
		<div class="wrapper wrapper-content animated fadeIn">
	        <div class="row">
	            <div class="col-sm-12">
                    <div class="ibox-content" style="font-size:18px;" id="template">
                        <p>待审查文件位置：<input id="path" type="text" name="filePath"></p> 
					   	<p>待审查文件名：<input id="name" type="text" name="fileName"></p>
                    </div>
                    <a type='button' class='btn btn-info' href='#' onclick=check()>格式审查</a>
                    <a type='button' class='btn btn-info' href="/user/user_getResult">查看结果</a>
                </div>
            </div>
        </div>
	
	    <!-- 全局js -->
	    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
	    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
	    <script src="/static/layer/layer.js"></script>
	    <script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
		<script>
			$(document).ready(function () {
				$.ajax({
	        		url: '/user/user_getTemplates',
	        		type: 'POST',
		       		dataType: 'JSON',
		       		success: function(res){
		       			selectBox = '<p>选择模板：<select class="sub_button" id ="templateName" name="templateName">';
		       			for(let i=0;i<res.length;i++){
							selectBox += '<option value="' + res[i].name + '">' + res[i].name + '</option>';
		       			}
		       			selectBox += '</select></p>';
	        			$("#template").append(selectBox);
		       		},
		    		error: function(res){
		    			layer.msg('获取失败');
		    		}
				});
			});
			function check() {
				var filePath = document.getElementById('path').value;
				var fileName = document.getElementById('name').value;
				var templateName = $('#templateName').val();
				$.ajax({
	        		url: '/file/file_parserDocx',
	        		type: 'POST',
	        		data:{
	        			'path':filePath,
	        			'name':fileName,
	        			'type':"document",
	        			'tempName':templateName
	        		},
		       		dataType: 'JSON',
		       		success: function(res){
		       			if(res == 0){
		        			layer.msg('审查失败');
	        			}
	        			else{
	        				layer.msg('审查成功');
	        			}
		       		},
		    		error: function(res){
		    			layer.msg('审查失败');
		    		}
				});
			}
		</script>
	</body>

</html>
