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
                        <a type='button' class='btn btn-info' href='#' onclick=savePrescription()>保存 </a>
                    </div>
                    <div class="ibox-content">
						<center><font size="5" ><a>处方单</a></font></center>
						<div class="row" id="prescriptionID" style="float:right;font-size:14px;">
						</div>
					</div>
                    <div class="ibox-content" style="font-size:18px;">
						<table class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                            	<tr>
                            		<th>药品ID</th>
                            		<th>数量</th>
                            		<th>价格</th>
                            	</tr>
                            </thead>
                            <tbody id="medicineInfo">
                            </tbody>
                        </table>
                    </div>
                    
        			<div style = "text-align:right;">
               			<a type='button' class='btn btn-info' href='#' onclick=getPriceById()>确定 </a>
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
		var record_ID = '<%= session.getAttribute("record_ID") %>';
		function savePrescription() {
			var medicine_ID = document.getElementById("medicineId").value;
			var nums = document.getElementById("medicineNum").value;
			$.ajax({
        		url: '/doctor/updatePrescription',
        		type: 'POST',
        		data:{
        			'ID':ID,
        			'medicine_ID':medicine_ID,
        			'nums':nums
        		},
	       		dataType: 'JSON',
	       		success: function(res){
	       			layer.msg(res);
	       			if(res == 1) {
	       				layer.alert('保存成功', function() {
	       					parent.layer.closeAll();
	       					window.parent.location.reload();
	       				});
	       			} else {
	       				layer.msg("库存不足！");
	       			}
       			},
	    		error: function(res){
	    			layer.msg('保存失败');
	    		}
			});
			setTimeout(function (){
					window.location.reload();
				}, 2000);
		};
		function getPriceById() {
			var medicine_ID = document.getElementById("medicineId").value;
			var num = document.getElementById("medicineNum").value;
			$.ajax({
        		url: '/doctor/getMedicineByID',
        		type: 'POST',
        		data:{
        			'medicine_ID':medicine_ID
        		},
	       		dataType: 'JSON',
	       		success: function(res){
	       			document.getElementById("medicinePrice").value = res.price * num;
	       		},
	    		error: function(res){
	    			layer.msg('获取价格失败');
	    		}
			});
		};

		var data;
		function getPrescriptionById(prescription_ID){
			$.ajax({
	        		url: '/doctor/getPrescriptionById',
	        		type: 'POST',
	        		data:{'prescription_ID':prescription_ID},
	        		async:false,
	        		dataType: 'JSON',
	        		success: function(res){
	        			$("#prescriptionID").append(
	        					"<p><a>No：</a>" + prescription_ID + "</p>");
	        			ID = prescription_ID;
	        			selectBox = '<tr><td><select class="sub_button" id ="medicineId" name="medicineId">';
	        			selectBox += getMedicineInfo(selectBox);
		       			selectBox += '</select></td><td><input type="text" id="medicineNum" /></td>' +
	        				'<td><input type="text" id="medicinePrice" value = "0" /></td></tr>';
	        			$("#medicineInfo").append(selectBox);
	        			document.getElementById("medicineId").value = res.medicineId;
		       			document.getElementById("medicineNum").value = res.num;
		       			document.getElementById("medicinePrice").value = res.price;
	        		},
	        		error: function(res){
	        			layer.msg('获取处方失败');
	        		}
        	});
		};
		function getMedicineInfo(selectBox) {
			$.ajax({
        		url: '/doctor/getAllMedicines',
        		type: 'POST',
        		data:{
        			'record_ID':record_ID
        		},
        		async:false,
	       		dataType: 'JSON',
	       		success: function(res, selectBox){
	       			for(let i=0;i<res.length;i++){
						selectBox += '<option value="' + res[i].id + '">' + res[i].id + '</option>';
	       			}
	       			data = selectBox;
	       		},
	    		error: function(res){
	    			layer.msg('获取失败');
	    		}
			});
			return data;
		};
		
	</script>

    
    

</body>

</html>
