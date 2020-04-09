<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> - 选项卡 &amp; 面板</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/static/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
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
                        <a type='button' class='btn btn-info' href='#' onclick=addPrescription()>新增处方 </a>
                    </div>
                    <div class="ibox-content" style="font-size:18px;">
						<table class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                            	<tr>
                                    <th>处方ID</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="prescriptionInfo">
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
    
    <script src="/static/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="/static/js/plugins/dataTables/dataTables.bootstrap.js"></script>

	<script>
		var record_ID = '<%= session.getAttribute("record_ID") %>';
		function getPrescriptionList() {
        	$.ajax({
        		url: '/doctor/getAllPrescription',
        		data:{
        			'record_ID':record_ID
        		},
        		type: 'POST',
        		dataType: 'JSON',
        		success: function(res){
       				var data = res;
       				//然后 DataTables 这样初始化：
                    $('.dataTables-example').DataTable( {
                        data: data,
                        columns: [
                       		{ data: 'id' },
                           	{ data: null}
                        ],
                        columnDefs:[{
                            targets: 1,
                            render: function (data, type, row) {
                                return '<a type="button" class="btn btn-info" href="#" onclick=editOnePrescription("' + row.id + '")>编辑</a>';
                            }
                        },
                            { "orderable": false, "targets": 1 },
                        ],
                    } );
        		},
        		error: function(res){
        			layer.msg('获取列表失败');
        		}
        	});
        };
		function editOnePrescription(ID){
        	layer.open({
      		  type: 2,
      		  title: '处方',
      		  shadeClose: true,
      		  shade: 0.8,
      		  area: ['70%', '90%'],
      		  content: '/doctor/editPrevPrescription_doctor',
      		  success: function (layero, index) {
      			var iframe = window['layui-layer-iframe' + index];
                iframe.getPrescriptionById(ID)
              }
      		});
        };
		function addPrescription() {
			layer.open({
      			type: 2,
      		  	title: '处方',
      		  	shadeClose: true,
      		  	shade: 0.8,
      		  	area: ['70%', '90%'],
      		  	content: '/doctor/addPrescription_doctor',
      		  	success: function (layero, index) {
      		  		
      				var iframe = window['layui-layer-iframe' + index];
                	iframe.getCurrPrescription()
              	}
     		});
		}
	</script>

</body>

</html>
