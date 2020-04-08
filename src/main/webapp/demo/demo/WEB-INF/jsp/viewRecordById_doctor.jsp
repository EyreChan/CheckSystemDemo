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
		function Date2String(date) {
			return  moment(date).format("YYYY-MM-DD");
		};
		function viewRecordById(ID) {
			$("#recordID").append(
					"<p><a>No：</a>" + ID + "</p>");
			$.ajax({
	        		url: '/doctor/getRecordByID',
	        		type: 'POST',
	        		data:{'ID':ID},
	        		dataType: 'JSON',
	        		success: function(res){
	        			$("#deptType").append("<p><a>&nbsp;&nbsp;&nbsp;&nbsp;科室：</a>" + res.department.type + "</p>");
	        			$("#visitTime").append("<p><a>就诊时间：</a>" + Date2String(res.recordWithBLOBs.time) + "</p>");
	        			
	        			line1 = "<tr>" +
                			"<td width='33%''>姓名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
               				res.patient.name + "</td>" +
                			"<td width='33%''>&nbsp;&nbsp;性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
               				res.patient.sex + "</td>" +
                			"<td width='33%''>&nbsp;&nbsp;年龄：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
               				res.patient.age + "</td></tr>";
            			line2 = "<tr><td>电话：&nbsp;&nbsp;&nbsp;" + 
       						res.patient.phone + "</td>" +
        					"<td colspan='2'>&nbsp;&nbsp;&nbsp;身份证号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
       						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
       						res.patient.id + "</td></tr>";
            			line3 = "<tr><td align='center'>主诉</td>" +
	    					"<td colspan='2'><textarea id='Illness' rows='6' cols='68' style='width:100%;height:100%;border:solid 1px #4682B4;'>" + 
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
        				document.getElementById('Illness').value=res.recordWithBLOBs.illness;
        				document.getElementById('Illness').readOnly = true; 
        				document.getElementById('History').value=res.recordWithBLOBs.history;
        				document.getElementById('History').readOnly = true; 
        				document.getElementById('Result').value=res.recordWithBLOBs.result;
      					document.getElementById('Result').readOnly = true; 
        				document.getElementById('Treatment').value=res.recordWithBLOBs.treatment;
       					document.getElementById('Treatment').readOnly = true; 
	        		},
	        		error: function(res){
	        			layer.msg('病历加载失败');
	        		}
	        	});
		};
		
	</script>

    
    

</body>

</html>
