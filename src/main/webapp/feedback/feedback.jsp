<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>
    $(function(){
        //初始化表单
        //初始化表单属性
        $("#fTable").jqGrid({
            url : "${path}/feedback/queryFeedBackByPage",  //分页查询   page  rows  total recoreds  page  rows
            datatype : "json",
            rowNum : 5,  //每页展示是条数
            rowList : [ 2,5, 10, 30 ],
            pager : '#fPager',
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            viewrecords:true,  //是否展示数据总条数
            colNames : [ 'FID','标题','反馈内容','上传时间','用户ID' ],
            colModel : [
                {name : 'id',width : 30, align:"center"},
                {name : 'title',editable:true,width : 40, align:"center"},
                {name : 'content',editable:true,width : 130,align : "center"},
                {name : 'feedbackTime',editable:true,width : 50,align : "center"},
                {name : 'userId',editable:true,width : 90,align : "center"}
            ]
        });

        //处理曾删改查操作   工具栏
        $("#fTable").jqGrid('navGrid', '#fPager',
            {edit : true,add : true,del : true,edittext:"修改",addtext:"添加",deltext:"删除"},

        );

    });

    //根据用户id修改状态,通过ajax异步修改状态
    function updateStatus(id,status){

        if (status==1){
            $.ajax({
                url:"${path}/user/edit",
                type:"get",
                dataType:"text",
                data:{"id":id,"status":"0","oper":"edit"},
                success:function(){
                    //刷新页面
                    $("#fTable").trigger("reloadGrid");
                }
            });
        }else {
            $.ajax({
                url:"${path}/user/edit",
                type:"post",
                dataType:"text",
                data:{"id":id,"status":"1","oper":"edit"},
                success:function(){
                    //刷新页面
                    $("#uTable").trigger("reloadGrid");
                }

            });

        }
    }
</script>
<style type="text/css">

    #tableDataSearch tr td{
        text-align:center;
    }
</style>
<body>
<%--初始化一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <center><h3>反馈管理</h3></center>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">反馈信息</a></li>
    </div>

    <%--初始化表单--%>
    <table id="fTable" />

    <%--工具栏--%>
    <div id="fPager" />

</div>
</body>
