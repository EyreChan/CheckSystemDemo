<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 基本表单</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
	<style>
		.form-control, .single-line{padding:4px 12px;}
	</style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12">
                                <form role="form" class="form-horizontal">
                                	<div class="form-group">
                                        <label>处方ID：</label>
                                        <input id="preid" type="text" placeholder="处方ID" class="form-control">
                                    </div>
                                    
                                   <div class="form-group">
                                        <label>可更新状态为：</label>
		                                    <select class="form-control m-b" name="pstate" id="pstate">
		                                        <option value="2">等待配药</option>
		                                        <option value="3">正在配药</option>
		                                        <option value="4">等待发药</option>
		                                    </select>
                                    </div>
                                    <div>
                                        <button type="button" class="btn btn-sm btn-primary pull-right m-t-n-xs" onclick="updatestate()">
                                        	<strong>更 新</strong>
                                        </button>
                                    </div>
                                </form>
                            </div>
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

    <!-- iCheck -->
    <script src="/static/js/plugins/iCheck/icheck.min.js"></script>
    <script src="/static/layer/layer.js"></script>
    <script>
	    $(document).ready(function () {
	        $('.i-checks').iCheck({
	            checkboxClass: 'icheckbox_square-green',
	            radioClass: 'iradio_square-green',
	        });
	    });
    
	    function getPreById(id){
	    	
	    	$.ajax({
        		url: '/Dispenser/getPrecriptionByID',
        		type: 'POST',
        		data: {'ID':id},
        		dataType: 'JSON',
        		success: function(res){        			
        			$('#preid').val(res.id);
        		},
        		error: function(res){
        			layer.msg('失败');
        		}
        	});
	    }
	    
        function updatestate(){
        	var pstate = $("select option:checked").val();
        	var stateName = $("select option:checked").text();
			var preid=$('#preid').val();
        	$.ajax({
        		url: '/Dispenser/updatePrescriptionByID',
        		type: 'POST',
        		data: {'ID':preid,
        			'state':pstate
        			},
        		dataType: 'JSON',
        		success: function(result){
        			var frameIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
        			
        			if(result>0){
        				layer.alert('更新成功',function(index){
        					//layer.close(index);
        					window.parent.location.reload();
        					parent.layer.closeAll();
        					//alert(111);
        					
        				});
        				
        			}else{
        				layer.msg('更新失败');
        			}
        		},
        		error: function(res){
        			layer.msg('更新失败');
        		}
        	});
        }
    </script>

    
    

</body>

</html>
