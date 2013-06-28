<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Go4U</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- Apple devices fullscreen -->
<meta name="apple-mobile-web-app-capable" content="yes" />
<!-- Apple devices fullscreen -->
<meta names="apple-mobile-web-app-status-bar-style"	content="black-translucent" />
<title>NSO ADMIN</title>
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
    <div id="navigation">
        <div class="container-fluid">
            <a href="#" id="brand">GO4U</a>
        </div>
    </div>
    <div id="content" class="container-fluid">
    	<div id="main">
    		<div class="container-fluid">
    		    <div class="page-header">
    		        <div class="pull-right">
    		            <ul class="stats">
							<li class="satgreen"><i class="icon-money"> </i>
								<div class="details" style="line-height:37px;">
									<span class="big" id="currency">100 韩元（₩）= 0.536人民币元（￥）</span>
								</div>
						    </li>
						</ul>
    		        </div>
    		    </div>
				<div class="row-fluid">
					<form class="form-validate form-horizontal" id="link-form">
						<div class="control-group">
							<input class="span10 input" name="url" data-url-rule="true"
								data-required-rule="true" placeholder="代购商品链接">
							<button id="btn-price" type="button" class="btn btn-info">帮我找</button>
						</div>
					</form>
				</div>
				<div style="display: none;" id="product-info">
					<div class="row-fluid">
						<div class="span12"
							style="float: none; margin-left: auto; margin-right: auto;">
							<div class="offset1 span4">
								<img id="big_img">
							</div>
							<div class="span6 container-fluid" style="text-align: left;">
								<form class="form-horizontal">
									<div class="control-group">
										<label class="control-label" for="prd_name">商品名称</label>
										<div class="controls">
											<span id="prd_name"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="prd_price">价格</label>
										<div class="controls">
											<span id="prd_price"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="count">数量</label>
										<div class="controls">
											<input type="text" id="count" class="spinner input-small"
												value="1">
										</div>
									</div>
									<div class="control-group">
										<div class="controls">
											<button type="button" id="btn-money" class="btn btn-info">计算价格</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<ul class="stats" style="margin-top:20px;">
							<li class="lightred">
							    <div class="details" id="results"></div>
							</li>
						</ul>
					</div>
				</div>
			</div>
    	</div>
    </div>

	<script>
	var currency, price;
    $(function(){
        $.post('<%=request.getContextPath()%>/currency').done(function(resp){
        	currency = parseFloat(resp.currency);
        	$('#currency').empty().text('100 韩元（₩）= ' + resp.currency + '人民币元（￥）');
         });
        
        var calResults = function(){
        	var result = (price * $('#count').val() + 2500)/100 * currency;
        	$('#results').empty().text('(' + price + '₩ X '+ $('#count').val() + ' + 2500₩) ÷ 100 X ' + currency + ' = ￥' + result.toFixed(2));
        };
        
        $('#btn-price').click(function(){
            $.post('<%=request.getContextPath()%>/info', $('#link-form').serialize()).done(function(resp){
                $('#product-info').show();
                $('#big_img').attr('src',resp.main_img);
                try {
                    price = parseFloat(resp.price.replace(',', ''));
                } catch(e){
                }
                $('#prd_price').empty().text(resp.price);
                $('#prd_name').empty().text(resp.name);
                calResults();
            });
        });
        
        $('#btn-money').click(function(){
        	calResults();
        });
    });
</script>
</body>
</html>
