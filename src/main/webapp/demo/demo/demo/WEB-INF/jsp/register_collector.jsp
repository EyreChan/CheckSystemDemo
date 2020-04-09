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
                        <h5>科室医生列表</h5>
                    </div>
                    <div class="ibox-content">
						<div class="row" id="doctors">
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- 全局js -->
    <script src="/static/js/jquery.min.js?v=2.1.4"></script>
    <script src="/static/js/bootstrap.min.js?v=3.3.6"></script>

    <!-- 自定义js -->
    <script src="/static/js/content.js?v=1.0.0"></script>
    <script src="/static/js/plugins/layer/layer.min.js"></script>
    
    <script src="/static/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
	<script>
		var doctorDatas;
		
		function getDoctorByDeptId(ID){
			$.ajax({
	        		url: '/patient/getGoodDoctorByDepartment',
	        		type: 'POST',
	        		data:{'ID':ID},
	        		dataType: 'JSON',
	        		success: function(res){
	        			
	        			doctorDatas = res;
	        			for(let i=0;i<res.length;i++){
	        				var level =  res[i].level == 1?"专家":"普通医生";
	        				var id = "#time"+i;
	        				var n=checknums(res[i].id, i);

	        				$("#doctors").append(
	        					"<div class='col-sm-4'>"+
	                                "<div class='panel panel-success'>"+
	                                    "<div class='panel-heading'>"+res[i].id+" 相关信息</div>"+
	                                    "<div class='panel-body'>"+
	                                        "<p >医生职称: "+level+"</p>"+
	                                        
	                                        "<p >今日剩余挂号名额: "+n+"</p>"+
	                                        
	                                        "<button class='btn btn-info' onclick='register("+ i +" ," + n +")' >挂号</button>"+
	                                    "</div>"+
	                                "</div>"+
	                            "</div>"
	        				);
	        				laydate.render({
	        					  elem: '#time'+i //指定元素
	        				});
	        				
	        			}
	        			
	        		},
	        		error: function(res){
	        			layer.msg('加载失败');
	        		}
	        	});
		}
		function getDoctorById(id){
			$.ajax({
	        		url: '../getDocById.do',
	        		type: 'POST',
	        		data:{'doctorId':id},
	        		dataType: 'JSON',
	        		success: function(res){
	        			doctorDatas = res;
	        				var status =  res.workStatus == 1?"正常":"请假";
	        				$("#doctors").append(
	        					"<div class='col-sm-4'>"+
	                                "<div class='panel panel-success'>"+
	                                    "<div class='panel-heading'>"+res.doctorName+" 相关信息</div>"+
	                                    "<div class='panel-body'>"+
	                                    	"<p>医生姓名: "+res.doctorName+"</p>"+
	                                        "<p class='des'>医生简介: "+res.doctorDes+"</p>"+
	                                        "<p>所属科室: "+res.deptName+"</p>"+
	                                        "<p>出诊时间: "+res.workTime+"</p>"+
	                                        "<p style='color:red'>当前工作状态: "+status+"</p>"+
	                                        
	                                        "<div class='form-group'>"+
	                                        	"<label class='col-sm-2 control-label' style='padding:0px'>挂号时间：</label>" +
		                                        "<div class='col-sm-10'>" +
		                                        	"<input id='time' class='laydate-icon form-control layer-date'>" +
		                                        "</div>"+
	                                        "</div>" +
	                                        "<button class='btn btn-info' onclick='register(-1)'>挂号</button>"+
	                                    "</div>"+
	                                "</div>"+
	                            "</div>"
	        				);
	        				laydate.render({
	        					  elem: '#time' //指定元素
	        				});
	        		},
	        		error: function(res){
	        			layer.msg('加载失败');
	        		}
	        	});
		}
	
		function checknums(ID,index){
			
			if(index >= 0){
				var mydoctor = doctorDatas[index];
			}else{
				var mydoctor = doctorDatas;
			}
			var res = 0;
			var now = new Date();
			var nowTime = now.getFullYear() + "-" +((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
			
					$.ajax({
	        			url: '/patient/getRemainedByIDAndDate',
	        			type: 'POST',
	        			async:false,
	        			data: {
		        			'ID':ID,
		        			'Date':nowTime	
	        				},
	        			dataType: 'text',
	        			success: function(result){
	        				res=result;
	        			},
	        			error: function(res){
	        				layer.msg("查询失败");
	        			},
	        		});
			
			return res;	
		}
	
		function register(I,n){
			
			
			if(n<=0){
				layer.msg('该医生今日已无挂号名额');
			}else{
				if(I >= 0){
					var mydoctor = doctorDatas[I];
				}else{
					var mydoctor = doctorDatas;
				}
				var collector_ID = $.cookie('loginID');

				layer.use('extend/layer.ext.js', function(){
							
				                layer.prompt({
									formType: 0,
									value: '',
									title: '请输入病人ID',
									btn: ['确认','取消'] ,
									btnAlign:'c'//按钮
								} ,  function(value,index){
									$.ajax({
										url: '/collector/insertRegister',
					        			type: 'POST',
					        			data: {
					        				'patient_ID':value,
					        				'doc_ID':mydoctor.id,
					        				'depart_ID':mydoctor.departId,
					        				'collector_ID':collector_ID
					        			},
					        			dataType: 'JSON',
					        			success: function(result){
					        				if(result>0){
					        					
					        					layer.alert('挂号成功!',function(index){
					        						layer.close(index);
					        						parent.layer.closeAll();
					        					});
					        				}
					        			},
					        			error: function(res){
					        				layer.msg('挂号失败');
					        			}
									})
								},function(value,index){
				    			});
				        });

				
				layer.prompt	
			}
		}
		
	</script>

    
    

</body>

</html>
