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
                        <h5>我的预约 <small>列表</small></h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    <div class="ibox-content">

                        <table class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                                <tr>
                                    <th>预约单ID</th>
                                    <th>预约医生ID</th>
                                    <th>预约科室ID</th>
                                    <th>预约单状态</th>
                                    <th>预约时间</th>
                                    <th>操作</th>
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
        		url: '/patient/getReservationByPatient',
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
                            { data: 'departId' },
                            { data: 'state',render:function(data,type,row){
                                if(data == 0){
                                 
                                    return "<font color='green' size='3''>正常<font/>";
                                }else if(data == 1){
                                    return "<font color='green' size='3''>正常<font/>";
                                }else{
                                	return "<font color='red' size='3''>已取消<font/>";
                                }
                                
                        	} },
                            { data: 'time' ,"render" : function(data, type, full, meta) {
        						return  moment(data).format("YYYY-MM-DD");
        					}},
                            { data: null}
                        ],
                        columnDefs:[{
                            targets: 5,
                            render: function (data, type, row, meta) {
                            	if(row.state == 0){
                            		return '<a type="button" class="btn btn-info" href="#" onclick=changeghStatus("' + row.id + '") >取消预约 </a>';
                            	}else if(row.state == 1){
                            		return '<a type="button" class="btn btn-warning" href="#" disabled>已就诊 </a>';
                            	}else{
                            		return '<a type="button" class="btn btn-danger" href="#" disabled>已取消 </a>';
                            	}
                            	
                                
                            }
                        },
                            { "orderable": false, "targets": 3 },
                            { "orderable": false, "targets": 5 },
                            { "orderable": false, "targets": 1 },
                            { "orderable": false, "targets": 2 },
                            
                        ],
                    } );
        		},
        		error: function(res){
        			layer.msg('加载失败');
        		}
        	});
        });
        function changeghStatus(id){
        	
            	layer.confirm('确认取消预约？', {
          		  btn: ['确定','取消'] //按钮
          		}, function(){
          			$.ajax({
                  		url: '/patient/cancelReservationByID',
                  		type: 'POST',
                  		data: {
                  			'ID':id
                  		},
                  		dataType: 'JSON',
                  		success: function(result){
                  			if(result>0){
                  				layer.alert('取消成功',function(index){
                  					//layer.close(index);
                  					window.location.href="/patient/RervLists_patient";
                  				});
                  			}
                  		},
                  		error: function(res){
                  			layer.msg('修改失败');
                  		}
                  	});
          		}, function(){
          	});
        }
    </script>
	
    
    

</body>

</html>
