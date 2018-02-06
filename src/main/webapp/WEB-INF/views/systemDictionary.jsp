<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html>
<head>
    <%@include file="common.jsp" %>
    <title>数据字典首页</title>
    <script type="text/javascript">
    $(function () {
        var systemDictionary_datagrid = $("#systemDictionary_datagrid");
        var systemDictionary_toolbar = $("#systemDictionary_toolbar");
        var systemDictionary_dialog = $("#systemDictionary_dialog");
        var systemDictionary_form = $("#systemDictionary_form");

        var systemDictionaryItem_dialog = $("#systemDictionaryItem_dialog");
        var systemDictionaryItem_toolbar = $("#systemDictionaryItem_toolbar");
        var systemDictionaryItem_datagrid = $("#systemDictionaryItem_datagrid");
        var systemDictionaryItem_form = $("#systemDictionaryItem_form");

        systemDictionaryItem_datagrid.datagrid({
            rownumbers: true,
            fitColumns: true,
            fit: true,
            toolbar: '#systemDictionaryItem_toolbar',
            url:'/systemDictionaryItem/list',
            striped: true,
            singleSelect: true,
            columns: [[
				{field:'sn',title:'字典编号',width:100,align:'center'}, 
				{field:'name',title:'字典名称',width:100,align:'center'}, 
				{field:'intro',title:'字典简介',width:100,align:'center'},
				{field:'parent',title:'字典目录',width:100,align:'center',formatter:function (value,row,index) {
                    return value ? value.name : "没有目录";
                }}
            ]]
        });


        systemDictionary_datagrid.datagrid({
            url: "/systemDictionary/query",
            rownumbers: true,
            fitColumns: true,
            fit: true,
            toolbar: '#systemDictionary_toolbar',
            striped: true,
            singleSelect: true,
            columns: [[
				{field:'sn',title:'字典目录编号',width:100,align:'center'}, 
				{field:'name',title:'字典目录名称',width:100,align:'center'}, 
				{field:'intro',title:'字典目录简介',width:100,align:'center'}
            ]],
            onClickRow: function (rowIndex, rowData) {
                var options = systemDictionaryItem_datagrid.datagrid('options');
                options.url = "/systemDictionaryItem/queryBySystemDictionaryId";
                systemDictionaryItem_datagrid.datagrid('load', {id: rowData.id});
            }

        });


        systemDictionary_dialog.dialog({
            closable: true,
            width: 300,
            height: 200,
            buttons: '#systemDictionary_dialog_buttons',
            closed: true
        });
        systemDictionaryItem_dialog.dialog({
            closable: true,
            width: 300,
            height: 200,
            buttons: '#systemDictionaryItem_dialog_buttons',
            closed: true
        });
        var cmdObj = {
            add: function () {
                systemDictionary_dialog.dialog('open');
                systemDictionary_dialog.dialog("setTitle", '新增');
                systemDictionary_form.form("clear");
            },
            cancel: function () {
                systemDictionary_dialog.dialog('close');
            },
            save: function () {
                // 判断是否有id,如有就更新数据
                var id = $("#dictionaryId").val();
                var url;
                if (id) {
                    url = "/systemDictionary/update";
                } else {
                    url = '/systemDictionary/save';
                }
                systemDictionary_form.form("submit", {
                    url: url,
                    success: function (result) {
                        result = $.parseJSON(result);
                        if (result.success) {
                            $.messager.alert("温馨提示", result.msg, 'icon-smile',
                                function () {
                                    systemDictionary_dialog.dialog('close');
                                    systemDictionary_datagrid.datagrid('reload');
                                });
                        } else {
                            $.messager.alert("温馨提示", result.msg, 'icon-warning');
                        }
                    }
                })
            },
            edit: function () {
                var record = systemDictionary_datagrid.datagrid("getSelected");
                if (record) {
                    systemDictionary_dialog.dialog("open");
                    systemDictionary_dialog.dialog("setTitle", "字典目录编辑");
                    systemDictionary_form.form("clear");
                    systemDictionary_form.form("load", record);
                } else {
                    $.messager.alert("温馨提示", "请选择一条数据", 'icon-warning');
                }
            },
            reload: function () {
                systemDictionary_datagrid.datagrid("reload");
            },
            del: function () {
                var record = systemDictionary_datagrid.datagrid("getSelected");
                if (record) {
                    $.messager.confirm("温馨提示", "亲,确定要删除该字典目录吗?", function (yes) {
                        if (yes) {
                            $.get("/systemDictionary/delete?id=" + record.id,
                                function (result) {
                                    if (result.success) {
                                        $.messager.alert("温馨提示", result.msg,
                                            'info', function () {
                                                systemDictionary_datagrid.datagrid('reload');
                                            });
                                    } else {
                                        $.messager.alert("温馨提示", result.msg,
                                            'warning');
                                    }
                                });
                        }
                    });

                } else {
                    $.messager.alert("温馨提示", "请选择一条数据", 'icon-warning');
                }
            },
            addItem: function () {
                systemDictionaryItem_dialog.dialog('open');
                systemDictionaryItem_dialog.dialog("setTitle", '新增');
                systemDictionaryItem_form.form("clear");
            },
            cancelItem: function () {
                systemDictionaryItem_dialog.dialog('close');
            },
            saveItem: function () {
                // 判断是否有id,如有就更新数据
                var id = $("#itemId").val();
                var url;
                if (id) {
                    url = "/systemDictionaryItem/update";
                } else {
                    url = '/systemDictionaryItem/save';
                }
                systemDictionaryItem_form.form("submit", {
                    url: url,
                    success: function (result) {
                        result = $.parseJSON(result);
                        if (result.success) {
                            $.messager.alert("温馨提示", result.msg, 'icon-smile',
                                function () {
                                    systemDictionaryItem_dialog.dialog('close');
                                    systemDictionaryItem_datagrid.datagrid('reload');
                                });
                        } else {
                            $.messager.alert("温馨提示", result.msg, 'icon-warning',
                                function () {
									return;
                                });
                        }
                    }
                });
            },
            editItem: function () {
                var row = systemDictionaryItem_datagrid.datagrid("getSelected");
                if (!row) {
                    $.messager.alert("温馨提示", "请选择一条数据", 'icon-warning');
               	 } 
                    systemDictionaryItem_form.form("clear");
                    systemDictionaryItem_dialog.dialog("setTitle", "字典目录编辑");
                    //处理字典目录数据
                    row['parent.id'] = row.parent.id;
                    console.log(row);
                    console.log(row.parent.name);
                    console.log(row.parent.id);
                    systemDictionaryItem_form.form("load",row);
                   /*  
                    $.get("/systemDictionaryItem/queryBySystemDictionaryId?id=" + row.id, function (data) {
                        var systemDictionaryItemIds = $.map(data, function (row, index) {
                            return row.id;
                        })
                        $('#systemDictionary_combobox').combobox('setValues', systemDictionaryItemIds);
                    }, "json"); */
                    
                    systemDictionaryItem_dialog.dialog("open");
            },
            reloadItem: function () {
                systemDictionaryItem_datagrid.datagrid("reload");
            },
            delItem: function () {
                var record = systemDictionaryItem_datagrid.datagrid("getSelected");
                if (record) {
                    $.messager.confirm("温馨提示", "亲,确定要删除该字典目录吗?", function (yes) {
                        if (yes) {
                            $.get("/systemDictionaryItem/delete?id=" + record.id,
                                function (result) {
                                    if (result.success) {
                                        $.messager.alert("温馨提示", result.msg,
                                            'info', function () {
                                                systemDictionaryItem_datagrid.datagrid('reload');
                                            });
                                    } else {
                                        $.messager.alert("温馨提示", result.msg,
                                            'warning');
                                    }
                                });
                        }
                    });

                } else {
                    $.messager.alert("温馨提示", "请选择一条数据", 'icon-warning');
                }
            }
        };
        $("[data-cmd]").on("click", function () {
            var cmd = $(this).data("cmd");
            cmdObj[cmd]();
        });

    });
    </script>
</head>
<body>

<div id="cc" class="easyui-layout" style="width:600px;height:400px;" fit="true">
    <div data-options="region:'west',title:'字典目录',split:true" style="width:600px;">
        <table id="systemDictionary_datagrid"></table>
        <div id="systemDictionary_toolbar">
            <a class="easyui-linkbutton" iconCls="icon-add" plain="true"data-cmd="add">新增</a> 
            <a class="easyui-linkbutton"iconCls="icon-edit" plain="true" data-cmd="edit" id="edit_btn">编辑</a>
            <a class="easyui-linkbutton" iconCls="icon-remove" plain="true"data-cmd="del">删除</a>
            <a class="easyui-linkbutton" iconCls="icon-reload" plain="true"data-cmd="reload">刷新</a>
        </div>
        <div id="systemDictionary_dialog">
            <form method="post" id="systemDictionary_form">
                <input type="hidden" name="id" id="dictionaryId"/>
                <table align="center">
                    <tr>
                        <td>字典目录编号:</td>
                        <td><input class="easyui-textbox" name="sn"/></td>
                    </tr>
                    <tr>
                        <td>字典目录名称:</td>
                        <td><input class="easyui-textbox" name="name"/></td>
                    </tr>
                    <tr>
                        <td>字典目录简介:</td>
                        <td><input class="easyui-textbox" name="intro"/></td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="systemDictionary_dialog_buttons">
            <a class="easyui-linkbutton" iconCls="icon-save" plain="true"  data-cmd="save">保存</a> 
            <a class="easyui-linkbutton"iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
        </div>
    </div>

    <div data-options="region:'center',title:'字典目录明细'" style="padding:3px;background:#eee;">
        <table id="systemDictionaryItem_datagrid"></table>
        <div id="systemDictionaryItem_toolbar">
            <a class="easyui-linkbutton" iconCls="icon-add" plain="true"data-cmd="addItem">新增</a>
            <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" data-cmd="editItem">编辑</a>
            <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="delItem">删除</a>
            <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="reloadItem">刷新</a>
        </div>
        <div id="systemDictionaryItem_dialog">
            <form method="post" id="systemDictionaryItem_form">
                <input type="hidden" name="id" id="itemId"/>
                <table align="center">
                	<tr>
                        <td>字典明细编号:</td>
                        <td><input class="easyui-textbox" name="sn"/></td>
                    </tr>
                    <tr>
                        <td>字典明细名称:</td>
                        <td><input class="easyui-textbox" name="name"/></td>
                    </tr>
                    <tr>
                        <td>字典明细简介:</td>
                        <td><input class="easyui-textbox" name="intro"/></td>
                    </tr>
                    <tr>
                        <td>字典父目录:</td>
                        <td><input id="systemDictionary_combobox" class="easyui-combobox" name="parent.id"
                            data-options="valueField:'id',textField:'name',url:'/systemDictionary/query',panelHeight:'auto'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id="systemDictionaryItem_dialog_buttons">
        <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="saveItem">保存</a>
        <a class="easyui-linkbutton"iconCls="icon-cancel" plain="true" data-cmd="cancelItem">取消</a>
    </div>
</div>


</body>
</html>