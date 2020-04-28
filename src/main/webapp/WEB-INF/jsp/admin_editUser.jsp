<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	                    <div class="ibox-title">
	                        <a type='button' class='btn btn-info' href='#' onclick=saveUser()>保存 </a>
	                    </div>
	                    <div class="ibox-content">
							<center><font size="5" ><a>用户信息</a></font></center>
						</div>
	                    <div class="ibox-content">
							<table class="table table-striped table-bordered table-hover dataTables-example">
	                            <thead>
	                            </thead>
	                            <tbody id="userInfo">
	                            </tbody>
	                        </table>
	                    </div>
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
			function getUser(username){
				$.ajax({
		        		url: '/admin/admin_getUserByName',
		        		type: 'POST',
		        		data:{'name':username},
		        		dataType: 'JSON',
		        		success: function(res){
	            			line1 = "<tr><td>用户名</td>" +
		    					"<td><textarea id='userName' style='width:100%;border:solid 1px #4682B4;' >" +
		    					"</textarea></td></tr>";
	            			line2 = "<tr><td>性别</td>" +
	    						"<td><textarea id='userGender' style='width:100%;border:solid 1px #4682B4;'>" +
	    						"</textarea></td></tr>";
	   						line3 = "<tr><td>年龄</td>" +
	    						"<td><textarea id='userAge' style='width:100%;border:solid 1px #4682B4;'>" +
	    						"</textarea></td></tr>";
	   						line4 = "<tr><td>电话</td>" +
	    						"<td><textarea id='userPhone' style='width:100%;border:solid 1px #4682B4;'>" +
	    						"</textarea></td></tr>";
    						line5 = "<tr><td>密码</td>" +
	    						"<td><textarea id='userPassword' style='width:100%;border:solid 1px #4682B4;'>" +
	    						"</textarea></td></tr>";
		        			$("#userInfo").append(line1+line2+line3+line4+line5);
		        			if(res.name != null)
		        				document.getElementById('userName').value = res.name;
		        			if(res.gender != null)
		        				document.getElementById('userGender').value = res.gender;
		        			if(res.age != null)
		        				document.getElementById('userAge').value = res.age;
		        			if(res.phone != null)
		        				document.getElementById('userPhone').value = res.phone;
		        			if(res.password != null)
		        				document.getElementById('userPassword').value = res.password;
		        		},
		        		error: function(res){
		        			layer.msg('加载失败');
		        		}
		        	});
			}
			function saveUser() {
				var name = document.getElementById('userName').value;
				var gender = document.getElementById('userGender').value;
				var age = document.getElementById('userAge').value;
				var phone = document.getElementById('userPhone').value;
				var password = document.getElementById('userPassword').value;
				
				$.ajax({
	        		url: '/admin/admin_updateByName',
	        		type: 'POST',
	        		data:{
	        			'name':name,
	        			'gender':gender,
	        			'age':age,
	        			'phone':phone,
	        			'password':password
	        		},
		       		dataType: 'JSON',
		       		success: function(res){
		       			if(res == 0){
		        			layer.msg('保存失败');
	        			}
	        			else{
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
