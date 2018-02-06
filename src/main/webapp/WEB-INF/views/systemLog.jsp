<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>日志管理</title>
    <%@ include file="common.jsp"%>
    <script>
        $(function () {
            var systemLogDatagrid = $('#systemLog_datagrid');

            var cmdObject = {
                search:function () {
                    systemLogDatagrid.datagrid('load', {
                        'keyword' : $('input[name="keyword"]').val()
                    });
                }
            };

            systemLogDatagrid.datagrid({
                url:'/systemLog/list',
                fit:true,
                fitColumns:true,
                singleSelect:true,
                toolbar:'#systemLog_toolbar',
                pagination:true,
                columns:[[
                    {field:'employeeId',title:'员工',width:1,align:'center'},
                    {field:'operateTime',title:'时间',width:1,align:'center'},
                    {field:'ip',title:'ip',width:1,align:'center'},
                    {field:'function',title:'方法',width:1,align:'center'},
                    {field:'params',title:'参数',width:1,align:'center'}
                ]]
            });

            $('a').click(function () {
                var cmd = $(this).data('cmd');
                if(cmd){
                    cmdObject[cmd]();
                }
            });
        });
    </script>
</head>
<body>
    <div id="systemLog_toolbar">
        <div>
            <input type="text" name="keyword"><a data-cmd="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
        </div>
    </div>
    <table id="systemLog_datagrid"></table>
</body>
</html>
