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
                        <a type='button' class='btn btn-info' href='#' onclick=saveRecord()>保存 </a>
                    </div>
                    <div class="ibox-content">
						<center><font size="5" ><a>门诊病历</a></font></center>
						<div class="row" id="recordID" style="float:right;font-size:14px;">
						</div>
					</div>
					<div class="ibox-content" style="font-size:14px;">
						<div class="row" id="deptType" style="float:left;">
                        </div>
                        <div class="row" id="visitTime" style="float:right;">
                        </div>
                    </div>
                    <div class="ibox-content" style="font-size:18px;">
						<table class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                            </thead>
                            <tbody id="patientInfo">
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
		var ID = '<%= session.getAttribute("record_ID") %>';
		function saveRecord() {
			var illness = document.getElementById('Illness').value;
			var history = document.getElementById('History').value;
			var result = document.getElementById('Result').value;
			var treatment = document.getElementById('Treatment').value;
			
			$.ajax({
        		url: '/doctor/updatetRecordByID',
        		type: 'POST',
        		data:{
        			'ID':ID,
        			'illness':illness,
        			'history':history,
        			'result':result,
        			'treatment':treatment
        		},
	       		dataType: 'JSON',
	       		success: function(res){
	       			layer.msg("保存成功");
	       		},
	    		error: function(res){
	    			layer.msg('保存失败');
	    		}
			});
		};
		function Date2String(date) {
			return  moment(date).format("YYYY-MM-DD");
		};
		function getCurrRecord(){
			$("#recordID").append(
					"<p><a>No：</a>" + ID + "</p>");
			$.ajax({
	        		url: '/doctor/getRecordByID',
	        		type: 'POST',
	        		data:{'ID':ID},
	        		dataType: 'JSON',
	        		success: function(res){
	        			var ss;
	        			if(res.patient.sex==1)
	        				ss='女';
	        			else
	        				ss='男';
	        			$("#deptType").append("<p><a>&nbsp;&nbsp;&nbsp;&nbsp;科室：</a>" + res.department.type + "</p>");
	        			$("#visitTime").append("<p><a>就诊时间：</a>" + Date2String(res.recordWithBLOBs.time) + "</p>");
	        			
	        			line1 = "<tr>" +
                			"<td>&nbsp;姓名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
               				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
               				res.patient.name + "</td>" +
                			"<td>&nbsp;&nbsp;&nbsp;性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
               				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
               				ss + "</td>" +
                			"<td>&nbsp;&nbsp;年龄：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
               				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
               				res.patient.age + "</td></tr>";
            			line2 = "<tr><td>&nbsp;电话：&nbsp;&nbsp;&nbsp;&nbsp;" + 
       						"&nbsp;&nbsp;" + 
       						res.patient.phone + "</td>" +
        					"<td colspan='2'>&nbsp;&nbsp;&nbsp;身份证号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
       						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
       						res.patient.id + "</td></tr>";
            			line3 = "<tr><td align='center'>主诉</td>" +
	    					"<td colspan='2'><textarea id='Illness' rows='6' cols='68' style='width:100%;height:100%;border:solid 1px #4682B4;' >" +
	    					"</textarea></td></tr>";
            			line4 = "<tr><td align='center'>病史</td>" +
    						"<td colspan='2'><textarea id='History' rows='6' cols='68'  style='width:100%;height:100%;border:solid 1px #4682B4;'>" +
    						"</textarea></td></tr>";
   						line5 = "<tr><td align='center'>诊断结果</td>" +
    						"<td colspan='2'><textarea id='Result' rows='6' cols='68'  style='width:100%;height:100%;border:solid 1px #4682B4;'>" +
    						"</textarea></td></tr>";
   						line6 = "<tr><td align='center'>治疗方式</td>" +
    						"<td colspan='2'><textarea id='Treatment' rows='6' cols='68'  style='width:100%;height:100%;border:solid 1px #4682B4;'>" +
    						"</textarea></td></tr>";
	        			$("#patientInfo").append(line1+line2+line3+line4+line5+line6);
	        			if(res.recordWithBLOBs.illness != null)
	        				document.getElementById('Illness').value=res.recordWithBLOBs.illness;
	        			if(res.recordWithBLOBs.history != null)
	        				document.getElementById('History').value=res.recordWithBLOBs.history;
	        			if(res.recordWithBLOBs.result != null)
	        				document.getElementById('Result').value=res.recordWithBLOBs.result;
	        			if(res.recordWithBLOBs.treatment != null)
	        				document.getElementById('Treatment').value=res.recordWithBLOBs.treatment;
	        		},
	        		error: function(res){
	        			layer.msg('加载失败');
	        		}
	        	});
		};
		
	</script>

    
    

</body>

</html>
