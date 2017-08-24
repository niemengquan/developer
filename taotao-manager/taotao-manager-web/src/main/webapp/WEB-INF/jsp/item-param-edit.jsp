<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<table cellpadding="5" style="margin-left: 30px" id="itemParamEditTable" class="itemParam">
	<tr>
		<td>商品类目:</td>
		<td><span style="margin-left:10px;" cid="163">${tbItemParam.itemCatName}</span>
			<input type="hidden" name="cid" style="width: 280px;" value="${tbItemParam.itemCatId}"></input>
			<input type="hidden" name="id" style="width: 280px;" value="${tbItemParam.id}"></input>
		</td>
	</tr>
	<tr class="hide addGroupTr">
		<td>规格参数:</td>
		<td>
			<ul>
				<li><a href="javascript:void(0)" class="easyui-linkbutton addGroup">添加分组</a></li>
			</ul>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton submit">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton close">关闭</a>
		</td>
	</tr>
</table>
<div  class="itemParamAddTemplate" style="display: none;">
	<li class="param">
		<ul>
			<li>
				<input class="easyui-textbox" style="width: 150px;" name="group"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton addParam"  title="添加参数" data-options="plain:true,iconCls:'icon-add'"></a>
			</li>
			<li>
				<span>|-------</span><input  style="width: 150px;" class="easyui-textbox" name="param"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>
			</li>
		</ul>
	</li>
</div>
<script>
    function initParams(){
        $(".addGroup").parent().siblings().remove();
        $(".addGroupTr").show();
        /*初始化规格参数的值的列表*/
        var paramData='${tbItemParam.paramData}';
        var json = JSON.parse(paramData);
        $.each(json,function(i,e){
            var t1="<li class=\"param\">\n" +
                "\t\t<ul>\n" +
                "\t\t\t<li>\n" +
                "\t\t\t\t<input class=\"textbox-f\" style=\"width: 150px; display: none;\" textboxname=\"group\">" +
				"<span class=\"textbox\" style=\"width: 148px; height: 20px;\">" +
				"<input type=\"text\" class=\"textbox-text validatebox-text textbox-prompt\" value="+e.group+" autocomplete=\"off\" placeholder=\"\" style=\"margin-left: 0px; margin-right: 0px; padding-top: 3px; padding-bottom: 3px; width: 140px;\"><input type=\"hidden\" class=\"textbox-value\" name=\"group\" value=\"\"></span>&nbsp;<a href=\"javascript:void(0)\" class=\"addParam l-btn l-btn-small l-btn-plain\" title=\"添加参数\" data-options=\"plain:true,iconCls:'icon-add'\" group=\"\" id=\"\"><span class=\"l-btn-left l-btn-icon-left\">" +
				"<span class=\"l-btn-text l-btn-empty\">&nbsp;</span>" +
				"<span class=\"l-btn-icon icon-add\">&nbsp;</span></span></a>\n" +
                "\t\t\t</li>";
            /*$(".addGroup").parent().parent().append(t1)*/
            var paramsArray=e.params;
             $.each(paramsArray,function(iParam,paramE){
                t1=t1+"<li>\n" +
                    "\t\t\t\t<span>|-------</span>" +
					"<input style=\"width: 150px; display: none;\" class=\" textbox-f\" textboxname=\"param\">" +
					"<span class=\"textbox\" style=\"width: 148px; height: 20px;\">" +
					"<input type=\"text\" class=\"textbox-text validatebox-text textbox-prompt\" value="+paramE+" autocomplete=\"off\" placeholder=\"\" style=\"margin-left: 0px; margin-right: 0px; padding-top: 3px; padding-bottom: 3px; width: 140px;\">" +
					"<input type=\"hidden\" class=\"textbox-value\" name=\"param\" value=\"\"></span>&nbsp;" +
					"<a href=\"javascript:void(0)\" class=\"delParam l-btn l-btn-small l-btn-plain\" title=\"删除\" data-options=\"plain:true,iconCls:'icon-cancel'\" group=\"\" id=\"\">" +
					"<span class=\"l-btn-left l-btn-icon-left\">" +
					"<span class=\"l-btn-text l-btn-empty\">&nbsp;</span>" +
					"<span class=\"l-btn-icon icon-cancel\">&nbsp;</span></span></a>\n" +
                    "\t\t\t</li>";
                 /**/
            });
                t1=t1+"</ul>\n" +
                    "\t</li>";
            $(".addGroup").parent().parent().append(t1);
        });
        $(".addGroup").parent().parent().find(".addParam").click(function(){
            var li = $(".itemParamAddTemplate li").eq(2).clone();
            li.find(".delParam").click(function(){
                $(this).parent().remove();
            });
            li.appendTo($(this).parentsUntil("ul").parent());
        });
        $(".addGroup").parent().parent().find(".delParam").click(function(){
            $(this).parent().remove();
        });
    }
    $(function(){

    });
    $(function(){
        initParams();
        $(".addGroup").click(function(){
            var temple = $(".itemParamAddTemplate li").eq(0).clone();
            $(this).parent().parent().append(temple);
            temple.find(".addParam").click(function(){
                var li = $(".itemParamAddTemplate li").eq(2).clone();
                li.find(".delParam").click(function(){
                    $(this).parent().remove();
                });
                li.appendTo($(this).parentsUntil("ul").parent());
            });
            temple.find(".delParam").click(function(){
                $(this).parent().remove();
            });
        });

        $("#itemParamEditTable .close").click(function(){
            $(".panel-tool-close").click();
        });

        $("#itemParamEditTable .submit").click(function(){
            var params = [];
            var groups = $("#itemParamEditTable [name=group]");
            groups.each(function(i,e){
                var p = $(e).parentsUntil("ul").parent().find("[name=param]");
                var _ps = [];
                p.each(function(_i,_e){
                    var _val = $(_e).siblings("input").val();
                    if($.trim(_val).length>0){
                        _ps.push(_val);
                    }
                });
                var _val = $(e).siblings("input").val();
                if($.trim(_val).length>0 && _ps.length > 0){
                    params.push({
                        "group":_val,
                        "params":_ps
                    });
                }
            });
            console.log(JSON.stringify(params));
            var url = "/item/param/update/";
            $.post(url,{"paramData":JSON.stringify(params),
						"id":$("#itemParamEditTable [name=id]").val(),
						"itemCatId":$("#itemParamEditTable [name=cid]").val()},function(data){
                if(data.status == 200){
                    $.messager.alert('提示','修改商品规格成功!',undefined,function(){
                        $(".panel-tool-close").click();
                        $("#itemParamList").datagrid("reload");
                    });
                }
            });
        });
    });
</script>