
Ext.onReady(function() {

			var panel = new Ext.Panel({
						region : 'center',
						title : '面板标题', // 标题
						iconCls : 'book_previousIcon', // 图标
						collapsible : true, // 是否允许折叠
						// width : 400,
						//frame : true,
						//bodyStyle : 'background-color:#FFFFFF',
						height : 100,
						html:"<iframe name='mainFrame'  src='http://127.0.0.1:8899/eRedG4/demo/test.ered?reqCode=test2Init' scrolling='auto' frameborder='0' width='100%' height='100%' ></iframe>"
					});

			// 布局模型
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [panel]
					});
		});