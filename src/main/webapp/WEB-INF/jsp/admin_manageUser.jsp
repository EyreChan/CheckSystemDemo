<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	
	    <!-- Data Tables -->
	    <link href="/static/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	
	    <link href="/static/css/animate.css" rel="stylesheet">
	    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
		<style>
			.float-e-margins .btn {margin-bottom: 0px;}		
			.tableBtn{float:left;width:30%;margin-top:0px;margin-left:5px;padding:2px 8px;}
		</style>
	</head>

	<body class="gray-bg">
	    <div class="wrapper wrapper-content animated fadeInRight">
	        <div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>普通用户 <small>列表</small></h5>
	                        <div style="text-align:right">
	                        	<a type="button" style="text-align:right;" class="btn btn-info" href="#" onclick=addUser()>添加用户</a>
	                        </div>
	                    </div>
	                    <div class="ibox-content">
	                    	<div>
		                    	<table class="table table-striped table-bordered table-hover dataTables-example">
		                            <thead>
		                                <tr>
		                                    <th>用户名</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                            <tbody id="userList">
		                            </tbody>
		                        </table>
	                    	</div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	
	    <!-- 全局js -->
	    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
	    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
	    <script src="/static/js/plugins/jeditable/jquery.jeditable.js"></script>
	    <!-- Data Tables -->
	    <script src="/static/js/plugins/dataTables/jquery.dataTables.js"></script>
	    <script src="/static/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	    <!-- 自定义js -->
	    <script src="/static/js/content.js?v=1.0.0"></script>
		<script src="/static/layer/layer.js"></script>
	
	    <!-- Page-Level Scripts -->
	    <script>
	        $(document).ready(function () {
	        	$.ajax({
	        		url: '/admin/admin_getAllUsers',
	        		type: 'POST',
	        		dataType: 'JSON',
	        		success: function(res){
	        			var data = res;
	        			//然后 DataTables 这样初始化：
	                    $('.dataTables-example').DataTable( {
	                        data: data,
	                        columns: [
	                            { data: 'name' },
	                            { data: null}
	                        ],
	                        columnDefs:[{
	                            targets: 1,
	                            render: function (data, type, row, meta) {
	                                return '<a type="button" class="btn btn-info" href="#" onclick=editByName("' + row.name + '") >编辑 </a>' 
	                                		+ '<a type="button" class="btn btn-info" href="#" onclick=deleteByName("' + row.name + '") >删除</a>';
	                            }
	                        },
	                            { "orderable": false, "targets": 1 },
	                        ],
	                    } );
	        		},
	        		error: function(res){
	        			layer.msg('显示失败');
	        		}
	        	});
	        });
	        function deleteByName(username){
	        	//根据用户名删除用户信息
	        	layer.confirm('确认删除吗？', {
			  		  btn: ['确定','取消'] //按钮
			  		}, function(name){
			  			$.ajax({
			        		url: '/admin/admin_deleteUser',
			        		type: 'POST',
			        		dataType: 'JSON',
			        		data:{'name':username},
			        		success: function(res){
			        			if(res == 0){
				        			layer.msg('删除失败');
			        			}
			        			else{
			        				layer.alert('删除成功',function(index){
	                					layer.close(index);
	                					reload();
	                				});
			        			}
			        		},
			        		error: function(res){
			        			layer.msg('删除失败');
			        		}
			        	});
			  		}, function(){
			  	});
	        }
	        function editByName(username) {
	        	layer.open({
		       		  type: 2,
		       		  title: '用户信息',
		       		  shadeClose: true,
		       		  shade: 0.8,
		       		  area: ['70%', '90%'],
		       		  content: '/admin/admin_editUser',
		       		  success: function (layero, index) {
	        			var iframe = window['layui-layer-iframe' + index];
	                  	iframe.getUser(username)
	                }
	       		});
	        }
	        function addUser() {
	        	layer.open({
		       		  type: 2,
		       		  title: '用户信息',
		       		  shadeClose: true,
		       		  shade: 0.8,
		       		  area: ['70%', '90%'],
		       		  content: '/admin/admin_addUser',
		       		  success: function (layero, index) {
	        			var iframe = window['layui-layer-iframe' + index];
	                  	iframe.addUser()
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
