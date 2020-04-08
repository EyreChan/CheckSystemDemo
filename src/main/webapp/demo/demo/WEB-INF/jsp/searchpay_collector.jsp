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
                        <h5>缴费处理 </h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    <div class="ibox-content" style='text-align:center'>
                    <br/><br/><br/><br/><br/><br/><br/><br/>
 						
                       <input type="text" id="patient_id"  placeholder="请输入病人ID" size="40"/>
                       <input type="button" value="查询" name="search" onclick="getPayList()" />
                       
                       
                       
                       <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

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
    function getPayList(){
		var patient_ID = $('#patient_id').val();
		
		if(patient_ID == ""){
			layer.msg("病人ID不能为空!");
		}else{
			sessionStorage.setItem("patient_ID",patient_ID);
			window.location.href='/collector/SolvePay_collector';
		}

	}
    </script>
	

</body>

</html>
