<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Go4U</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!-- Apple devices fullscreen -->
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <!-- Apple devices fullscreen -->
    <meta names="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <title>NSO ADMIN</title>
    <link href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/assets/css/jqueryui/themes/smoothness/jquery-ui.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/assets/css/jqueryui/themes/smoothness/jquery.ui.theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/assets/css/themes.css" rel="stylesheet">

    <script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/plugins/jquery.nicescroll.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/jquery-ui-1.10.1.custom.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/jquery.ui.touch-punch.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/plugins/jquery.slimscroll.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/plugins/validation/jquery.validate.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/plugins/validation/additional-methods.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/plugins/eakroko.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/js/plugins/flat-theme.min.js"></script>
</head>
<body>
<div class="container-fluid" style="height:80px;top:100px;margin-top:100px;">
    <div class="span12" style="width:100%;text-align: center;">
        <form class="form-validate form-horizontal" id="link-form">
            <div class="control-group">
                <input class="span10 input" name="url" data-url-rule="true" data-required-rule="true" placeholder="代购商品链接">
                <button id="btn-price" type="button" class="btn btn-info">计算价格</button>
            </div>
        </form>
    </div>
</div>
<div class="container-fluid" id="content" style="margin-top:30px;display:none;text-align: center">
    <div class="span12" style="float:none;margin-left: auto;margin-right: auto;" >
        <div class="span4">
            <img id="big_img">
        </div>
        <div class="span6 container-fluid">
            <div class="row-fluid">
                <div class="span4">品名</div>
                <div class="span8" id="name"></div>
            </div>
            <div class="row-fluid">
                <div class="span4">价格</div>
                <div class="span8" id="price"></div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        $('#btn-price').click(function(){
            $.post("<%=request.getContextPath()%>/info", $('#link-form').serialize()).done(function(resp){
                $('#content').show();
                $('#big_img').attr('src',resp.main_img);
                $('#price').empty().text(resp.price);
                $('#name').empty().text(resp.name);
            });
        });
    });
</script>
</body>
</html>
