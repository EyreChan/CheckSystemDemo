<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 数据表格</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> 
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
                        <h5>我的排号单 <small>列表</small></h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    <div class="ibox-content">

                        <table class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                                <tr>
                                    <th>挂号单ID</th>
                                    <th>挂号科室ID</th>
                                    <th>挂号医生ID</th>
                                    <th>所属队列</th>
                                    <th>号码</th>
                                    <th>前方剩余人数</th>
                                </tr>
                            </thead>
                            <tbody id="deptList">
                            
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
    <script src="/static/js/plugins/jeditable/jquery.jeditable.js"></script>
    <!-- Data Tables -->
    <script src="/static/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="/static/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <!-- 自定义js -->
    <script src="/static/js/content.js?v=1.0.0"></script>
	<script src="/static/layer/layer.js"></script>
	<script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
    <!-- Page-Level Scripts -->
    <script>
        $(document).ready(function () {
        	
        	var patient_ID = $.cookie('loginID');
        	$.ajax({
        		url: '/patient/getIndexByID',
        		type: 'POST',
        		
        		data:{'patient_ID':patient_ID},
        		
        		dataType: 'JSON',
        		success: function(res){

        			var data = res;
        			
        			//然后 DataTables 这样初始化：
                    $('.dataTables-example').DataTable( {
                        data: data,
                        columns: [
                            { data: 'register_ID' },
                            { data: 'depart_ID' },
                            { data: 'doc_ID' },
                            { data: 'queue_ID' },
                            { data: 'index' },
                            { data: null}
                        ],
                        columnDefs:[{
                            targets: 5,
                            render: function (data, type, row, meta) {
                            	if(row.remained == 0){
                            		return "<font color='red' size='3'>"+row.remained+" 人<font/>";
                            	}else{
                            		return "<font color='green' size='3'>"+row.remained+" 人<font/>";
                            	}
                            	
                                
                            }
                        },
                            { "orderable": false, "targets": 3 },
                            { "orderable": false, "targets": 2 },
                            { "orderable": false, "targets": 1 },
                        ],
                    } );
        		},
        		error: function(res){
        			layer.msg('加载失败');
        		}
        	});
        });
    </script>
	
    
    

</body>

</html>

