<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- Apple devices fullscreen -->
<meta name="apple-mobile-web-app-capable" content="yes" />
<!-- Apple devices fullscreen -->
<meta names="apple-mobile-web-app-status-bar-style"	content="black-translucent" />
<title>DressChannel</title>
<link href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/assets/css/jqueryui/themes/smoothness/jquery-ui.min.css"	rel="stylesheet">
<link href="<%=request.getContextPath() %>/assets/css/jqueryui/themes/smoothness/jquery.ui.theme.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/assets/css/themes.css" rel="stylesheet">
<style>
#results {
    font-size: 3em;
    color: white;
    line-height: 1.5em;
    text-align: center;
    margin-left: 20px;
    margin-right: 20px;
    font-weight:bold;
}

#main {
    margin-left: auto;
    margin-right: auto;
    max-width:1200px;
}
</style>

<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/plugins/jquery.nicescroll.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/jquery-ui-1.10.1.custom.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/jquery.ui.touch-punch.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/plugins/jquery.slimscroll.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/plugins/validation/jquery.validate.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/plugins/validation/additional-methods.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/plugins/jquery-ui/jquery.ui.spinner.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/plugins/eakroko.min.js"></script>
<script	src="<%=request.getContextPath() %>/assets/js/plugins/flat-theme.min.js"></script>
</head>
<body>
    <div id="content" class="container-fluid">
    	<div id="main">
    	    <div class="page-header" style="margin-top:100px; margin-bottom:20px; text-align:center">
    	        <a href="http://dresschannel.taobao.com" target="about:blank">
                    <img src="<%=request.getContextPath()%>/assets/img/logo.png">
    	        </a>
    	    </div>
    		<div class="container-fluid">
				<div class="row-fluid" style="text-align:center;">
					<form class="form-validate form-horizontal" method="post" action="<%=request.getContextPath() %>/info" id="link-form">
						<div class="control-group">
							<input class="span10 input" name="url" data-url-rule="true"
								data-required-rule="true" placeholder="代购商品链接">
							<input id="btn-price" type="submit" class="btn btn-danger" value="&nbsp;&nbsp;GO&nbsp;&nbsp;">
						</div>
					</form>
				</div>
				<!-- 
				<div class="row-fluid" style="text-align:center">
                    <EMBED height="550" type="application/x-shockwave-flash" width="950" src="http://stylenanda.cafe24.com/web/design4/120313.swf" enablecontextmenu="0" AUTOSTART="1">
				</div>
				 -->
			</div>
		</div>
    </div>
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fd2ed6adf25ff01dface1c4cda9496ba2' type='text/javascript'%3E%3C/script%3E"));
</script>    
</body>
</html>
