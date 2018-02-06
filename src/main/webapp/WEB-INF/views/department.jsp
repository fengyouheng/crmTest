<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>部门管理</title>
	<%@include file="common.jsp" %>
</head>
<body>
	<div id="department_toolbar">
		<a data-cmd="toSave" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
		<a data-cmd="toUpdate" tag="toUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
		<a data-cmd="delete" tag="leave" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
		<a data-cmd="reload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
	</div>
	<table id="department_datagrid"></table>  
	
	<div id="department_dialog" class="easyui-dialog" title="新增" style="width:400px;height:300px;"   
        data-options="modal:true,closed:true,buttons:'#dialog_buttons'">   
    	<form id="department_form" method="post">
    		<input type="hidden" name="id">
    		<table align="center" style="margin-top: 10px;">
    			<tr>
    				<td>部门编码</td>
    				<td><input type="text" name="sn"> </td>
    			</tr>
    			<tr>
    				<td>部门名称</td>
    				<td><input type="text" name="name"> </td>
    			</tr>
    			<tr>
    				<td>部门经理</td>
    				 <td><input id="manager_combobox" class="easyui-combobox" name="manager.id"
                   data-options="valueField:'id',textField:'realname',url:'/employee/queryEmployee', panelHeight:'auto'">
    			</tr>
    			<tr>
    				<td>上级部门</td>
    				 <td><input class="easyui-combobox" name="parent.id" 
                   data-options="valueField:'id',textField:'name',url:'/department/query', panelHeight:'auto'"></td>
    			</tr>
    			<tr>
    				<td>部门状态</td>
    				<div>
    					<td><input data-checked="checked" type="radio" name="state" value="0">正常
    					<input  type="radio" name="state" value="1">停用</td>
    				</div>
    			</tr>
    		</table>
    	</form>   
	</div> 
	<div id="dialog_buttons">
		<a data-cmd="save"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		<a data-cmd="cancel" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
	</div>

	<script>
		var departmentDatagrid = $('#department_datagrid');
		var departmentForm = $('#department_form');
		var departmentDialog = $('#department_dialog');
		
		departmentDatagrid.datagrid({
			url:'/department/list',//拉取分页数据
			fit:true,
			fitColumns:true,
			singleSelect:true,
			pagination:true,
			toolbar:'#department_toolbar',
			 columns:[[    
		        {field:'sn',title:'部门编码',width:150,align:'center'},    
		        {field:'name',title:'部门名称',width:150,align:'center'},    
		        {field:'manager',title:'部门经理',width:150,align:'center',formatter:function (value,row,index) {
                    return value ? value.realname : '';
                }},    
		        {field:'parent',title:'上级部门',width:150,align:'center',formatter:function (value,row,index) {
                    return value ? value.name : '';
                }},    
		        {field:'state',title:'部门状态',width:150,align:'center',formatter:function (value,row,index) {
                    return value == 0 ? '<span style="color: #00ee00">正常</span>' : '<span style="color: #CC2222">停用</span>';
                }}
		    ]]
		});
		
		var cmdObject = {
			toSave:function(){
				departmentForm.form('clear');
				departmentDialog.dialog('setTitle','新增');
				$("input[data-checked='checked']").prop("checked","checked");
				departmentDialog.dialog('open');
				
			},
			toUpdate:function(){
				var rowData = departmentDatagrid.datagrid('getSelected'); //选中一行
				if(!rowData){
					$.messager.alert("温馨提示","请选择你要编辑的数据!","error");
					return;
				}
				rowData['manager.id'] = rowData.manager.id;
				rowData['parent.id'] = rowData.parent.id;
				departmentForm.form('clear');
				departmentDialog.dialog('setTitle','编辑');
				//数据回显
				departmentForm.form('load',rowData);
				 
				departmentDialog.dialog('open');
			},
			delete:function(){
				var rowData = departmentDatagrid.datagrid('getSelected');
				if(!rowData){
					$.messager.alert("温馨提示","请选择一行数据！",'error');
					return ;
				}
				$.messager.confirm('温馨提示','您确认想要删除该部门吗？',function(r){    
				    if (!r){    
				       return; 
				    }
				    //发送ajax,一般不是查询,使用post
				    $.post('/department/delete?id='+rowData.id,function(data){
				    	if(!data.success){
				    		$.messager.alert("溫馨提示",data.msg,"error");
							return ;
				    	}
				    	departmentDatagrid.datagrid('reload');
				    },'json');
				}); 
			},
			cancel:function(){
				departmentDialog.dialog('close');
			},
            reload:function(){
            	departmentDatagrid.datagrid('reload');
            },
			save:function(){
				//$("input[data-checked='checked']").prop("checked","checked");
				var url =$('input[name="id"]').val() ?  '/department/update' : '/department/save' ;
				console.log(url)
				departmentForm.form('submit',{
					url:url,
					success:function(data){
						data = JSON.parse(data);
						if(!data.success){
							$.messager.alert("溫馨提示",data.msg,"error");
							return;
						}
						$.messager.alert("溫馨提示",data.msg,"info",function(){
							departmentDialog.dialog('close');
							departmentDatagrid.datagrid('reload');
						});
					}
				})
			}
		}	
		$('a').click(function(){
			var cmd = $(this).data('cmd');
			if(cmd){
				cmdObject[cmd]();
			}
		});
	</script>
</body>
</html>