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
	                        <h5>style</h5>
	                    </div>
	                    <div class="ibox-content">
	                    	<div>
		                    	<table class="table table-striped table-bordered table-hover dataTables-example">
		                            <thead>
		                                <tr>
		                                	<th>名称</th>
		                                	<th>字体SZ</th>
		                                	<th>字体SZCS</th>
		                                	<th>字体ASC</th>
		                                	<th>字体EAST</th>
		                                	<th>对齐方式</th>
		                                </tr>
		                            </thead>
		                            <tbody id="formatList">
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
	    	function getStyle(tempname){
	        	$.ajax({
	        		url: '/file/file_getStyleFormat',
	        		type: 'POST',
	        		dataType: 'JSON',
	        		data:{'name':tempname},
	        		success: function(res){
	        			var data = res;
	        			//然后 DataTables 这样初始化：
	                    $('.dataTables-example').DataTable( {
	                        data: data,
	                        columns: [
	                            { data: 'styleName' },
	                            { data: 'fontSZ' },
	                            { data: 'fontSZCS' },                            
	                            { data: 'fontASC' },
	                            { data: 'fontEAST' },
	                            { data: 'alignment' }
	                        ]
	                    } );
	        		},
	        		error: function(res){
	        			layer.msg('显示失败');
	        		}
	        	});
	        }
	    </script>
	</body>
</html>
