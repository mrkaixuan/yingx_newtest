<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

    <script>
        /*//点击切换验证码
        $("#captchaImage".click(function () {
            $("#captchaImage").prop("src")
        }))*/
    </script>

</head>
<body>

    <!--顶部导航-->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="#">应学App后台管理系统</a>
            </div>

            <!-- 导航栏内容 -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" >欢迎:${admin.username}</a>
                    </li>
                    <li>
                        <a href="${path}/admin/logout ">退出登录 <span class="glyphicon glyphicon-log-out"></span> </a>
                    </li>
                </ul>

            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>



    <!--栅格系统-->
    <div class="container-fluid">
        <div class="row">
        <div class="col-md-2">
            <!--手风琴插件-->
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <!--胶囊式标签页-->
                            <ul class="nav nav-pills nav-stacked">
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/user/user.jsp')">用户展示</a></li>
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/echarts/TestEchartsGoEasy.jsp')">用户统计</a></li>
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/echarts/TestMapContr.jsp')">用户分布</a></li>
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/echarts/toWeChatWith2.jsp')">聊天室</a></li>

                            </ul>
                        </div>
                    </div>
                </div>
                <br>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                分类管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <!--胶囊式标签页-->
                            <ul class="nav nav-pills nav-stacked">
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/category/category.jsp')">分类展示</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <br>
                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                视频管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body">
                            <!--胶囊式标签页-->
                            <ul class="nav nav-pills nav-stacked">
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/video/video.jsp')">视频列表</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <br>
                <div class="panel panel-info">
                    <div class="panel-heading" role="tab">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body">
                            <!--胶囊式标签页-->
                            <ul class="nav nav-pills nav-stacked">
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/feedback/feedback.jsp')">反馈列表</a></li>
<%--                                <li role="presentation"><a href="#">订单添加</a></li>--%>
                            </ul>
                        </div>
                    </div>
                </div>

                <br>
                <div class="panel panel-info">
                    <div class="panel-heading" role="tab">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body">
                            <!--胶囊式标签页-->
                            <ul class="nav nav-pills nav-stacked">
                                <li role="presentation"><a href="javascript:$('#centerLayout').load('${path}/log/log.jsp')">日志列表</a></li>
                                <%--                                <li role="presentation"><a href="#">订单添加</a></li>--%>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-10"  id="centerLayout" >
            <!--巨幕-->
            <div class="jumbotron">
                <h1>欢迎来到应学App后台管理系统</h1>
                <%--<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured
                    content or information.</p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>--%>
            </div>
            <!--轮播图-->
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="4"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="../img/1.jpg" height="650" width="1366"/>
                        <div class="carousel-caption">
                            1
                        </div>
                    </div>
                    <div class="item">
                        <img src="../img/2.jpg" height="650" width="1366"/>
                        <div class="carousel-caption">
                            2
                        </div>
                    </div>
                    <div class="item">
                        <img src="../img/3.jpg" height="650" width="1366"/>
                        <div class="carousel-caption">
                            3
                        </div>
                    </div>
                    <div class="item">
                        <img src="../img/4.jpg" height="650" width="1366"/>
                        <div class="carousel-caption">
                            4
                        </div>
                    </div>
                    <div class="item">
                        <img src="../img/5.jpg" height="650" width="1366"/>
                        <div class="carousel-caption">
                            5
                        </div>
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="false"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="false"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            <br/>
            <br/>
            <br/>


        </div>
        </div>
        <!--页脚-->
      <%--  <div class="row footer-bottom">
            <ul class="list-inline text-center">
                <li>Copyright &copy;2019. n Success Software All Rights Reserved.</li>
            </ul>
        </div>--%>


    </div>
    <div class="panel panel-footer panel-default" align="center">
        <div>Copyright &copy;2019. n Success Software All Rights Reserved.</div>
    </div>
        <!--左边手风琴部分-->
        <!--巨幕开始-->
        <!--右边轮播图部分-->
        <!--页脚-->
    <!--栅格系统-->

</body>
</html>
