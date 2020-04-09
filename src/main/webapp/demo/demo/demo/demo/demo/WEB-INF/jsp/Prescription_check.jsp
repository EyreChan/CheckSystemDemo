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
                        <h5>我的处方单 <small>列表</small></h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    <div class="ibox-content">

                        <table class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                                <tr>
                                    <th>处方单ID</th>
                                    <th>就诊医生ID</th>
                                    <th>病历ID</th>
                                    <th>药品ID</th>
                                    <th>处方进度</th>
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
        		url: '/patient/getPrescriptionsByPatient',
        		type: 'POST',
        		
        		data:{'ID':patient_ID},
        		
        		dataType: 'JSON',
        		success: function(res){
        			var data = res;
        			
        			//然后 DataTables 这样初始化：
                    $('.dataTables-example').DataTable( {
                        data: data,
                        columns: [
                            { data: 'id' },
                            { data: 'docId' },
                            { data: 'recordId' },
                            { data: 'medicineId'},
                            { data: null}
                        ],
                        columnDefs:[{
                            targets: 4,
                            render: function (data, type, row, meta) {
                            	var a;
                            	if(row.state == 2){
                            		a= "<font color='green' size='3''>等待配药<font/>";
                            	}else if(row.state == 3){
                            		a= "<font color='green' size='3''>正在配药<font/>";
                            	}else if(row.state == 4){
                            		a= "<font color='green' size='3''>等待发药<font/>";
                            	}else if(row.state == 5){
                            		a= "<font color='green' size='3''>正在发药<font/>";
                            	};
                            	return a;
                                
                            }
                        },
                            { "orderable": false, "targets": 4 },
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