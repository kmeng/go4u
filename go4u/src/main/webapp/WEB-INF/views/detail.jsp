<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    		<div class="container-fluid">
    		    <div class="page-header" style="margin-top:50px;margin-bottom:20px;text-align:center">
    	            <a href="http://dresschannel.taobao.com" target="about:blank">
                        <img src="<%=request.getContextPath()%>/assets/img/logo.png">
    	            </a>
    		    </div>
				<div class="row-fluid" style="text-align:center">
					<form class="form-validate form-horizontal" method="post" action="<%=request.getContextPath() %>/info" id="link-form">
						<div class="control-group">
							<input class="span10 input" name="url" data-url-rule="true"
								data-required-rule="true" placeholder="代购商品链接" value="${url}">
							<input id="btn-price" type="submit" class="btn btn-danger" value="&nbsp;&nbsp;GO&nbsp;&nbsp;">
						</div>
					</form>
				</div>
				<div id="product-info">
					<div class="row-fluid">
						<div class="span12"
							style="float: none; margin-left: auto; margin-right: auto;">
							<div class="offset1 span4">
								<img id="big_img" src="${info.main_img}">
							</div>
							<div class="span6 container-fluid" style="text-align: left;">
								<form class="form-horizontal">
									<div class="control-group">
										<label class="control-label" for="prd_name">商品名称</label>
										<div class="controls">
											<span id="prd_name">${info.name}</span>
										</div>
									</div>
                                    <c:if test="${empty info.discountPrice}">
									<div class="control-group">
										<label class="control-label" for="prd_price">价格</label>
										<div class="controls">
											<span id="prd_price">${info.price}</span>
										</div>
									</div>
                                    </c:if>
                                    <c:if test="${not empty info.discountPrice}">
                                        <div class="control-group">
                                            <label class="control-label" for="prd_origin_price">原价</label>
                                            <div class="controls">
                                                <span id="prd_origin_price" style="text-decoration: line-through;">${info.price}</span>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="prd_price">现价</label>
                                            <div class="controls">
                                                <span id="prd_price" style="color:#267326;font-weight: bold;" >${info.discountPrice}</span>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty info.colors}">
                                        <div class="control-group">
                                            <label class="control-label" for="prd_color">颜色</label>
                                            <div class="controls">
                                                <select name="color" id="prd_color">
                                                    <c:forEach var="color" items="${info.colors}">
                                                        <option value="${color}">${color}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty info.sizes}">
                                        <div class="control-group">
                                            <label class="control-label" for="prd_size">尺码</label>
                                            <div class="controls">
                                                <select name="size" id="prd_size">
                                                    <c:forEach var="size" items="${info.sizes}">
                                                        <option value="${size}">${size}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </c:if>
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
					<div class="row-fluid" style="text-align:center">
						<ul class="stats" style="margin-top:20px;width:100%">
							<li class="lightred" style="width:100%">
							    <div class="details" id="results"></div>
							</li>
						</ul>
					</div>
				</div>
			</div>
    	</div>
    </div>
    <div id="footer">
        <p>
            Copyright &copy; 2013 DressChannel时尚频道  京ICP备13037906号-1
        </p>
    </div>

	<script>
    $(function(){        
        var calResults = function(){
        	var currency = ${currency};
        	var price = parseFloat($('#prd_price').html());
        	var result = (price * $('#count').val() + 2500)/100 * currency;
        	$('#results').empty().text('(' + price + '₩ X '+ $('#count').val() + ' + 2500₩) ÷ 100 X ' + currency + ' = ￥' + result.toFixed(2));
        };
        
        $('#btn-money').click(function(){
        	calResults();
        });
    	calResults();
    });

var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fd2ed6adf25ff01dface1c4cda9496ba2' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>
