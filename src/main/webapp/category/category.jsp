<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height:auto;
        vertical-align:text-top;
        padding-top:2px;
        display: table-cell;
        vertical-align: middle;
    }
    #tableDataSearch tr td{
        text-align:center;
    }
    .ui-jqgrid tr.jqgrow td {
         font-weight: 400;
         overflow: hidden;
         white-space: normal;
         height: 23px;
         padding: 1px 2px;
         border-bottom-width: 1px;
         border-bottom-color: inherit;
         border-bottom-style: solid
    }

    .ui-jqgrid tr.jqgfirstrow td {
         padding: 0 2px 0 2px;border-right-width: 1px; border-right-style: solid;
    }
</style>
<script>

    $(function(){
        pageInit();
    });

    //创建父表格
    function pageInit(){
        $("#cateTable").jqGrid({
            url : "${path}/cate/queryOneCategoryByPage", //分页查询一级类别
            editurl:"${path}/cate/edit",
            datatype : "json",
            rowNum : 5,
            rowList : [ 5, 10, 20, 30 ],
            pager : '#catePager',
            viewrecords : true,
            styleUI:"Bootstrap",
            height : "auto",
            autowidth:true,
            colNames : [ 'Id', '名称', '级别','编辑'],
            colModel : [
                {name : 'id',index : 'id',  width : 80,align : "center"},
                {name : 'name',index : 'invdate',width : 80,align : "center"},
                {name : 'levels',index : 'name',width : 80,align : "center"},
                {name : 'id',width : 100,align:'center',
                    formatter:function(cellvalue, options, rowObject){
                        return "<button onclick='deleteCate(\""+cellvalue+"\")' class='btn btn-danger' style='height:30px;width:100px' >删除</button>";
                    }
                }
            ],
            subGrid : true, //是否开启子表格
            //1.subgrid_id 点击一行时会在在父表格中创建一个div用来容纳子表格的  subgrid_id就是这个div的id
            //2.row_id 点击行的id
            subGridRowExpanded : function(subgridId, rowId) {

                addSubGrid(subgridId, rowId);
            }
        });

        //父表格的工具栏
        $("#cateTable").jqGrid('navGrid', '#catePager', {add : false,edit : false,del : true},
            {},
            {},
            {
                closeAfterDel:true,
                afterSubmit:function(data) {
                    //向警告框中追加错误信息
                    $("#showMsg").html(data.responseJSON.message);
                    //展示警告框
                    $("#deleteMsg").show();

                    //自动关闭
                    setTimeout(function () {
                        $("#deleteMsg").hide();
                    }, 3000);
                    return "hello";
                }
            }
        );
    }

    //创建子表格的属性
    function addSubGrid(subgridId, rowId){
        var subgridTableId= subgridId + "Table";
        var pagerId= subgridId+"Page";

        //在父表格创建出的div中创建子表格的table,工具栏
        $("#"+subgridId).html("<table id='"+subgridTableId+"' /><div id='"+pagerId+"' />");

        //配置子表格的属性
        $("#" + subgridTableId).jqGrid({
            url : "${path}/cate/querySecondCategoryByPage?parentId=" + rowId, //rowId 以及类别id 根据以及类别id查询所对应的二级类别
            editurl: "${path}/cate/secondEdit",
            datatype : "json",
            rowNum : 5,
            rowList : [ 5, 10, 20, 30 ],
            pager : "#"+pagerId,
            styleUI:"Bootstrap",
            height : "auto",
            viewrecords:true,
            autowidth:true,
            colNames : [ 'Id', '名称', '级别',"父类别id"],
            colModel : [
                {name : 'id',index : 'id',  width : 50,align : "center"},
                {name : 'name',index : 'invdate',width : 60,align : "center"},
                {name : 'levels',index : 'name',width : 60,align : "center"},
                {name : 'parentId',index : 'name',width : 60,align : "center"},
            ],
        });

        //子表格的工具栏
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId, {edit : false,add : false,del : true});
    }
    //点击删除行按钮
    function deleteCate(){
        //确定删除 展示模态框
        $("#myModal").modal("show");

    }


    //确定删除
    function OKDelete(){

        $("#myModal").modal("hide");

        //selrow   只读属性，最后选择行的id
        var rowId=$("#cateTable").jqGrid("getGridParam","selrow");

        $.post("${path}/category/edit",{"id":rowId,"oper":"del"},function(data){
            //向警告框中追加错误信息
            $("#showMsg").html(data.message);
            //展示警告框
            $("#deleteMsg").show();

            //自动关闭
            setTimeout(function(){
                $("#deleteMsg").hide();
            },3000);
            return "hello";
        },"JSON");
    }

</script>

<%--初始化一个面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <center><h2>类别管理</h2></center>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">类别信息</a></li>
    </div>


    <%--面板加按钮 添加删除功能--%>
    <div class="panel panel-body">
        <button class="btn btn-info" id="delBtn1">删除数据</button>
        <button class="btn btn-success" id="delBtn2">测试按钮</button>
    </div>

    <%--警告提示框--%>
    <div id="deleteMsg" class="alert alert-danger" style="height: 50px;width: 250px;display: none" align="center">
        <span id="showMsg"/>
    </div>


    <%--初始化表单--%>
    <table id="cateTable" />

    <%--工具栏--%>
    <div id="catePager" />

</div>



<%--模态框,用于验证,删除确定提示--%>
<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">删除提示</h4>
            </div>
            <div class="modal-body">
                <span>您确定要删除吗</span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="OKDelete()">删除</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
