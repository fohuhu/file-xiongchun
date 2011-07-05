/**
 * UI组件托管
 * 
 * @author XiongChun
 * @since 2011-06-29
 */

Ext.onReady(function() {
	var root = new Ext.tree.AsyncTreeNode({
				text : root_menuname,
				expanded : true,
				id : '01'
			});
	var menuTree = new Ext.tree.TreePanel({
				loader : new Ext.tree.TreeLoader({
							baseAttrs : {},
							dataUrl : 'part.ered?reqCode=queryMenuItems'
						}),
				root : root,
				title : '',
				applyTo : 'menuTreeDiv',
				autoScroll : false,
				animate : false,
				useArrows : false,
				border : false
			});

	menuTree.on('click', function(node) {
				menuid = node.attributes.id;
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize,
								menuid : menuid
							}
						});
			});

	menuTree.root.select();

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : 'UI组件编号',
				dataIndex : 'partid',
				width : 120
			}, {
				header : '菜单编号',
				dataIndex : 'menuid',
				width : 120
			}, {
				header : '组件ID',
				dataIndex : 'cmpid',
				sortable : true,
				width : 120
			}, {
				header : '组件类型',
				dataIndex : 'cmptype',
				width : 120,
				renderer : function(value) {
					if (value == '1')
						return '按钮组件';
					else if (value == '2')
						return '表单输入组件';
					else if (value == '3')
						return '容器面板组件';
					else
						return value;
				}
			}, {
				header : '菜单类型',
				dataIndex : 'menutype',
				renderer : function(value) {
					if (value == '1')
						return '系统菜单';
					else if (value == '0')
						return '业务菜单';
					else
						return value;
				}
			},{
				id : 'remark',
				header : '备注',
				dataIndex : 'remark'
			}]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'part.ered?reqCode=queryParts'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'TOTALCOUNT',
							root : 'ROOT'
						}, [{
									name : 'partid'
								}, {
									name : 'menuid'
								}, {
									name : 'cmpid'
								}, {
									name : 'cmptype'
								}, {
									name : 'remark'
								}])
			});

	// 翻页排序时带上查询条件
	store.on('beforeload', function() {
				this.baseParams = {};
			});

	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				hiddenName : 'pagesize',
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'],
									[50, '50条/页'], [100, '100条/页'],
									[250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '50',
				editable : false,
				width : 85
			});
			
	var number = parseInt(pagesize_combo.getValue());
	
	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(comboBox.getValue());
				number = parseInt(comboBox.getValue());
				store.reload({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			});

	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
			});

	var grid = new Ext.grid.GridPanel({
				title : '<span class="commoncss">托管UI组件列表</span>',
				iconCls : 'application_view_listIcon',
				height : 500,
				autoScroll : true,
				region : 'center',
				store : store,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				stripeRows : true,
				frame : true,
				autoExpandColumn : 'remark',
				cm : cm,
				sm : sm,
				tbar : [{
							text : '新增一行',
							iconCls : 'addIcon',
							handler : function() {
								addInit();
							}
						}, '-', {
							text : '保存新增',
							iconCls : 'acceptIcon',
							handler : function() {
								editInit();
							}
						}, '-', {
							text : '保存修改',
							iconCls : 'acceptIcon',
							handler : function() {
								editInit();
							}
						}, '-', {
							text : '删除',
							iconCls : 'deleteIcon',
							handler : function() {
								deleteMenuItems('1', '');
							}
						}, '->', {
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.reload();
							}
						}],
				bbar : bbar
			});
			
	store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});

	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [{
							title : '<span style="font-weight:normal">功能菜单</span>',
							iconCls : 'layout_contentIcon',
							tools : [{
										id : 'refresh',
										handler : function() {
											menuTree.root.reload()
										}
									}],
							collapsible : true,
							width : 210,
							minSize : 160,
							maxSize : 280,
							split : true,
							region : 'west',
							autoScroll : true,
							items : [menuTree]
						}, {
							region : 'center',
							layout : 'fit',
							border : false,
							items : [grid]
						}]
			});
});