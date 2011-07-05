/**
 * 获取服务器信息及内存CPU实时监控
 * 
 * @author XiongChun
 * @since 2010-11-27
 */
Ext.onReady(function() {

			var serverInfoGrid = new Ext.grid.PropertyGrid({
						title : '<span class="commoncss">服务器信息</span>',
						width : 320,
						collapsible : true,
						split : true,
						region : 'west',
						source : jsonInfo
					});

			serverInfoGrid.on("beforeedit", function(e) {
						// e.cancel = true;
						// return false;
					});

			var jvmMemPanel = new Ext.Panel({
						title : '<span class="commoncss">JVM内存监控视图(数据精确)</span>',
						contentEl : 'jvmMemChart_div',
						region : 'north',
						split : true,
						collapsible : true,
						autoScroll:true,
						height : document.body.clientHeight / 2
					});

			var hostMemPanel = new Ext.Panel({
						title : '<span class="commoncss">主机物理内存监控视图(数据仅供参考)</span>',
						contentEl : 'hostMemChart_div',
						autoScroll:true,
						region : 'center'
					});

			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [serverInfoGrid, {
									region : 'center',
									layout : 'border',
									border : false,
									items : [jvmMemPanel, hostMemPanel]
								}]
					});

			var jvmtask = {
				run : function() {
					updateJVMChart();
				},
				interval : 3000
			};
			
			var hostmemtask = {
				run : function() {
					updateHosMemChart();
				},
				interval : 5000
			};

			var taskRunner = new Ext.util.TaskRunner();
			taskRunner.start(hostmemtask);
			taskRunner.start(jvmtask);

			function updateJVMChart() {
				Ext.Ajax.request({
							url : 'serverInfo.ered?reqCode=updateJvmChart',
							success : function(response, opts) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								var xmlstring = resultArray.xmlstring;
								updateChartXML('jvmMemChart', xmlstring);
							},
							failure : function(response, opts) {
								Ext.MessageBox.alert('提示', '获取监控数据失败');
							},
							params : {}
						});
			}
			
			function updateHosMemChart() {
				Ext.Ajax.request({
							url : 'serverInfo.ered?reqCode=updateHostMemChart',
							success : function(response, opts) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								var xmlstring = resultArray.xmlstring_hostmem;
								updateChartXML('hostMemChart', xmlstring);
							},
							failure : function(response, opts) {
								Ext.MessageBox.alert('提示', '获取监控数据失败');
							},
							params : {}
						});
			}

		});