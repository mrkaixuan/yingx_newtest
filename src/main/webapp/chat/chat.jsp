<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>184 聊天室</title>
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
        $(function () {
            $("#sendMsg".click(function () {

                var content = $("#content").val();

                goEasy.publish(
                    {
                        channel:"myChatChannel",
                        message: content,
                        onSuccess:function () {

                            var msgDiv = ("<div style='width: 20px;height: 20px'>+content+</div>")
                            $("#showMsg").append(msgDiv);
                        },
                        onFaild:function (error) {
                            alert("error"+error.code+"message:"+error.content());

                        }


                    }
                )
            }))
        });

    </script>

</head>
<body>
    <div align="center">
        <h2>184 聊天室</h2>
        <div style="width:600px;height: 600px;border: 2px solid">

            <div id="showMsg" style="width:594px;height: 500px;border: 2px solid"></div>
            <div style="width: 594px;height: 88px;border: 2px solid">

                <input id="content" style="width: 500px;height: 80px " type="area">
                <button id="sendMsg" style="width: 70px;height: 70px;background-color: green" ></button>
            </div>

        </div>

    </div>



</body>
</html>
