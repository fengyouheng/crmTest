<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>员工管理</title>
    <%@ include file="common.jsp"%>
    <script>
        $(function () {
            var employeeDatagrid = $('#employee_datagrid');
            var employeeForm = $('#employee_form');
            var employeeDialog = $('#employee_dialog');

            var cmdObject = {
                toSave:function () {
                    employeeForm.form('clear');
                    employeeDialog.dialog('setTitle', '新增');
                    $("input[data-checked='checked']").prop("checked","checked");
                	$("input[data-checked1='checked']").prop("checked","checked");
                    employeeDialog.dialog('open');
                },
                toUpdate:function () {
                    var row = employeeDatagrid.datagrid('getSelected');
                    if(!row){
                        $.messager.alert('温馨提示', '未选中一行！', 'error');
                        return;
                    }

                    employeeForm.form('clear');
                    employeeDialog.dialog('setTitle', '编辑');
                    if(row.dept){
                    	row['dept.id'] = row.dept.id;
                    }
                    console.log(row.dept.id);

                    // 回显角色
                    $.get("/employee/queryRoleByEmployee?employeeId=" + row.id, function (data) {
                        var roleIds = $.map(data, function (row, index) {
                            return row.id;
                        })
                        $('#employee_role_combobox').combobox('setValues', roleIds);
                    }, "json");
                    employeeForm.form('load', row);
					console.log(row)
                    employeeDialog.dialog('open');
                },
                save:function () {
                    var url = $('[name="id"]').val() ? '/employee/update' : '/employee/save';
                    employeeForm.form('submit', {
                        url:url,
                        onSubmit: function(param){
                            var roleIds = $('#employee_role_combobox').combobox('getValues');  //获取下拉框值
                            $.each(roleIds, function (index, roleId) {
                                param["roles[" + index + "].id"] = roleId;
                            });
                        },
                        success:function (data) {
                            data = JSON.parse(data);
                            if(!data.success){
                                $.messager.alert('温馨提示', data.msg, 'error');
                                return;
                            }
                            $.messager.alert('温馨提示', data.msg, 'info', function () {
                                employeeDialog.dialog('close');
                                employeeDatagrid.datagrid('reload');
                            });
                        }
                    })
                },
                leave:function () {
                    var row = employeeDatagrid.datagrid('getSelected');
                    if(!row){
                        $.messager.alert('温馨提示', '未选中一行！', 'error');
                        return;
                    }
                    $.messager.confirm('温馨提示', '确定离职该员工吗？', function (flag) {
                        if(flag){
                            $.post('/employee/leave?id=' + row.id, function (data) {
                                if(!data.success){
                                    $.messager.alert('温馨提示', data.msg, 'error');
                                    return;
                                }
                                $.messager.alert('温馨提示', data.msg, 'info', function () {
                                    employeeDatagrid.datagrid('reload');
                                });
                            }, 'json');
                        }
                    })
                },
                search:function () {
                    employeeDatagrid.datagrid('load', {
                        'keyword' : $('input[name="keyword"]').val()
                    });
                },
                cancel:function () {
                    employeeDialog.dialog('close');
                },
                reload:function(){
                	employeeDatagrid.datagrid("reload");
                },
                delete:function(){
    				var rowData = employeeDatagrid.datagrid('getSelected');
    				if(!rowData){
    					$.messager.alert("温馨提示","请选择一行数据！",'error');
    					return ;
    				}
    				$.messager.confirm('温馨提示','您确认想要删除该部门吗？',function(r){    
    				    if (!r){    
    				       return; 
    				    }
    				    //发送ajax,一般不是查询,使用post
    				    $.post('/employee/delete?id='+rowData.id,function(data){
    				    	if(!data.success){
    				    		$.messager.alert("溫馨提示",data.msg,"error");
    							return ;
    				    	}
    				    	employeeDatagrid.datagrid('reload');
    				    },'json');
    				}); 
    			}
            };
            employeeDatagrid.datagrid({
                url:'/employee/list',
                fit:true,
                fitColumns:true,
                singleSelect:true,
                toolbar:'#employee_toolbar',
                pagination:true,
                columns:[[
                    {field:'username',title:'账户名',width:1,align:'center'},
                    {field:'realname',title:'真实姓名',width:1,align:'center'},
                    {field:'tel',title:'联系电话',width:1,align:'center'},
                    {field:'email',title:'邮箱',width:1,align:'center'},
                    {field:'dept',title:'部门',width:1,align:'center',formatter:function(value,row,index){
                    	 return value ? value.name : '';
    		        }},
                    {field:'inputTime',title:'入职时间',width:1,align:'center'},
                    {field:'state',title:'状态',width:1,align:'center',formatter:function (value,row,index) {
                        return value == 0 ? '<span style="color: #00ee00">在职</span>' : '<span style="color: #CC2222">离职</span>';
                    }},
                    {field:'roles',title:'角色',width:1,align:'center',formatter:function(value,row,index){
                    	var op = '';
                    	$.each(value,function(index,v){
							if(v){
                    		op += v +' ';                    		
							}	
                    	})
                    	 return op ? op : '';
    		        }},
                    {field:'admin',title:'超级管理员',width:1,align:'center',formatter:function (value,row,index) {
                        return value == 0 ? "是":"否";
                    }}
                ]],
                onClickRow: function (rowIndex, rowData) {
                    $('#toUpdate, #leave').linkbutton(rowData.state == 0 ? 'enable' : 'disable');
                }
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
    <div id="employee_toolbar">
        <div>
            <shiro:hasPermission name="employee:save">
                <a data-cmd="toSave" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="employee:update">
                <a data-cmd="toUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="employee:leave">
                <a data-cmd="leave" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">离职</a>
            </shiro:hasPermission>
            	<a data-cmd="reload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
            	关键字：<input type="text" name="keyword">
            	<a data-cmd="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"></a>
           		<a data-cmd="delete" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
        </div>
    </div>
    <table id="employee_datagrid"></table>

    <div id="employee_dialog" class="easyui-dialog" title="新增" style="width:500px;height:300px;"
         data-options="modal:true,closed:true,buttons:'#employee_diaglog_buttons'">
        <form id="employee_form" method="post">
            <input type="hidden" name="id">
            <table align="center" style="margin-top: 15px;">
                <tr>
                    <td>用户名</td>
                    <td><input class="easyui-textbox" prompt="请输入用户名" name="username"></td>
                </tr>
                <tr>
                    <td>真实姓名</td>
                    <td><input class="easyui-textbox" prompt="请输入真实姓名" name="realname"></td>
                </tr>
                <tr>
                	<td>密码:</td>
                	<td><input class="easyui-textbox" prompt="请输入密码" type="password" name="password"/></td>
           		 </tr>
                <tr>
                    <td>联系电话</td>
                    <td><input class="easyui-textbox" prompt="请输入电话号码" name="tel"></td>
                </tr>
                <tr>
                    <td>邮箱</td>
                    <td><input class="easyui-textbox" prompt="请输入邮箱" name="email"></td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td><input id="employee_department" class="easyui-combobox" name="dept.id" 
                   data-options="valueField:'id',textField:'name',url:'/department/query', panelHeight:'auto'"></td>
                </tr>
                <tr>
                    <td>入职时间</td>
                    <td><input class="easyui-datebox" name="inputTime"></td>
                </tr>
                <tr>
                	<td>部门状态</td>
    				<div>
    					<td><input data-checked="checked" type="radio" name="state" value="0">在职
    					<input type="radio" name="state" value="1">停职</td>
    				</div>
                </tr>
                <tr>
                    <td>角色</td><td><input id="employee_role_combobox" class="easyui-combobox" 
                    data-options="multiple:true, valueField:'id',textField:'name',panelHeight:'auto',url:'/role/query'" /></td>
                </tr>
                <tr> 
                    <!-- <td>超级管理员</td>
    				<td>
    					<input class="easyui-textbox" prompt="请输入邮箱" name="admin">
    				</td> -->
    				<td>超级管理员</td>
    				<div>
    					<td><input  type="radio" name="admin" value="false">是
    					<input data-checked1="checked" type="radio" name="admin" value="true">否</td>
    				</div>
    				 
                </tr>
            </table>
        </form>
    </div>
    <div id="employee_diaglog_buttons">
        <a data-cmd="save" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
        <a data-cmd="cancel" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
    </div>
</body>
</html>
