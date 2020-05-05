<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title> - 选项卡 &amp; 面板</title>
	    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	    <link href="/static/css/animate.css" rel="stylesheet">
	    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
		<style>
			.des{display:block;line-height:25px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;}
		</style>
	</head>
	<body class="gray-bg">
	    <div class="wrapper wrapper-content animated fadeIn">
	        <div class="row">
	            <div class="col-sm-12">
	                <div class="ibox">
					   	<p>文件位置：<input id="path" type="text" name="filePath"></p> 
					   	<p>文件名：<input id="name" type="text" name="fileName"></p>
				   		<a type='button' class='btn btn-info' href='#' onclick=submit()>提交</a>
	                </div>
	            </div>
	        </div>
	    </div>
	
		<!-- 脚本 -->
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.js"></script>
	    <!-- 语言包 -->
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/locale/zh-cn.js"></script>
	
	    <!-- 全局js -->
	    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
	    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
	
	    <!-- 自定义js -->
	    <script src="/static/js/content.js?v=1.0.0"></script>
	    <script src="/static/js/plugins/layer/layer.min.js"></script>
	    
	    <script src="/static/js/laydate/laydate.js"></script>
	    <script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
	    
	    <script>
			function submit() {
				var filePath = document.getElementById('path').value;
				var fileName = document.getElementById('name').value;
				$.ajax({
	        		url: '/file/file_parserDocx',
	        		type: 'POST',
	        		data:{
	        			'path':filePath,
	        			'name':fileName,
	        			'type':"template"
	        		},
		       		dataType: 'JSON',
		       		success: function(res){
		       			if(res == 0){
		        			layer.msg('保存失败');
	        			}
	        			else{
	        				layer.msg('保存成功');
	        				var index = parent.layer.getFrameIndex(window.name);  
        				 	parent.layer.close(index);
        				 	window.parent.location.reload(); 
	        			}
		       		},
		    		error: function(res){
		    			layer.msg('保存失败');
		    		}
				});
			}
			//重新加载
	        function reload(){
	        	window.location.reload();
	        }
		</script>
	</body>
</html>
