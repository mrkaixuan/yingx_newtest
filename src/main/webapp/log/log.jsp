<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>
    $(function(){
        //初始化表单
        //初始化表单属性
        $("#lTable").jqGrid({
            url : "${path}/log/queryByPage",  //分页查询   page  rows  total recoreds  page  rows
            datatype : "json",
            rowNum : 5,  //每页展示是条数
            rowList : [ 2,5, 10, 30 ],
            pager : '#lPager',
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            viewrecords:true,  //是否展示数据总条数
            colNames : [ 'ID','管理员','修改时间','操作','结果' ],
            colModel : [
                {name : 'id',width : 30, align:"center"},
                {name : 'adminName',editable:true,width : 40, align:"center"},
                {name : 'time',editable:true,width : 130,align : "center"},
                {name : 'action',editable:true,width : 50,align : "center"},
                {name : 'status',editable:true,width : 90,align : "center"}
            ]
        });

        //处理曾删改查操作   工具栏
        $("#lTable").jqGrid('navGrid', '#lPager',
            {edit : true,add : true,del : true,edittext:"修改",addtext:"添加",deltext:"删除"},

        );

    });
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
        <center><h3>日志管理</h3></center>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">日志信息</a></li>
    </div>

    <%--初始化表单--%>
    <table id="lTable" />

    <%--工具栏--%>
    <div id="lPager" />

</div>
</body>
