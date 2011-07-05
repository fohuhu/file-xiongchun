Ext.ux.form.HtmlEditor.Image2 = Ext.extend(Ext.util.Observable, {
	// Image language text
	langTitle : '<span style="font-weight:normal">插入图片</span>',
	urlSizeVars : ['width', 'height'],
	basePath : '',
	init : function(cmp) {
		this.cmp = cmp;
		this.cmp.on('render', this.onRender, this);
		this.cmp.on('initialize', this.onInit, this, {
					delay : 100,
					single : true
				});
	},
	onEditorMouseUp : function(e) {
		Ext.get(e.getTarget()).select('img').each(function(el) {
			var w = el.getAttribute('width'), h = el.getAttribute('height'), src = el
					.getAttribute('src')
					+ ' ';
			src = src.replace(new RegExp(this.urlSizeVars[0]
							+ '=[0-9]{1,5}([&| ])'), this.urlSizeVars[0] + '='
							+ w + '$1');
			src = src.replace(new RegExp(this.urlSizeVars[1]
							+ '=[0-9]{1,5}([&| ])'), this.urlSizeVars[1] + '='
							+ h + '$1');
			el.set({
						src : src.replace(/\s+$/, "")
					});
		}, this);

	},
	onInit : function() {
		Ext.EventManager.on(this.cmp.getDoc(), {
					'mouseup' : this.onEditorMouseUp,
					buffer : 100,
					scope : this
				});
	},
	onRender : function() {
		var btn = this.cmp.getToolbar().addButton({
					iconCls : 'x-edit-image',
					handler : this.selectImage,
					scope : this,
					tooltip : {
						title : this.langTitle
					},
					overflowText : this.langTitle
				});
	},
	isUrl : function(urlString) {
		regExp = /(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i;
		if (urlString.match(regExp))
			return true;
		else
			return false;
	},

	selectImage : function() {
		// alert('OK');
		if (!this.imgWindow2) {
			this.imgWindow2 = new Ext.Window({
				title : this.langTitle,
				id : 'imgWindow2',
				closeAction : 'hide',
				width : 400,
				height : 160,
				layout : 'fit',
				items : [new Ext.TabPanel({
					border : false,
					enableTabScroll : true,
					activeTab : 0,
					// autoWidth : true,
					height : 160,
					items : [{
						title : '<span style="font-weight:normal">上传本地图片</span> ',
						items : [{
									xtype : 'form',
									itemId : 'upload-img2',
									id : 'upload-img2',
									fileUpload : true,
									border : false,
									plain : true,
									bodyStyle : 'padding: 10px;',
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
												xtype : 'fileuploadfield',
												fieldLabel : '选择文件',
												name : 'file1',
												anchor : '100%',
												allowBlank : false,
												emptyText : '请选择图片(大小不能超过100KB)'
											}, {
												xtype : 'textfield',
												fieldLabel : '图片说明',
												name : 'up_name',
												maxLength : 100,
												anchor : '100%',
												emptyText : '简短的图片说明'
											}]
								}],
						buttons : [{
							text : '插入',
							iconCls : 'acceptIcon',
							id : 'btnUploadInset22',
							handler : function() {
								var frm = Ext.getCmp('upload-img2').getForm();
								if (!frm.isValid()) {
									return;
								}
								if (frm.isValid()) {
									file1 = frm.findField('file1').getValue();
									var point = file1.lastIndexOf(".");
									var type = file1.substr(point);
									if (type == ".jpg" || type == ".gif"
											|| type == ".JPG" || type == ".GIF") {
										// todo
									} else {
										Ext.MessageBox.alert('提示',
												'只支持上传jpg和gif格式的图片文件');
										return;
									}
									frm.submit({
										url : 'projectHome.ered?reqCode=doUpload',
										waitTitle : '提示',
										method : 'POST',
										waitMsg : '正在上传文件,请稍候...',
										timeout : 60000, // 60s
										success : function(form, action) {
											aUrl = action.result.aUrl;
											if (action.result.state == 'error') {
												Ext.MessageBox.alert('提示',
														action.result.msg);
												return;
											}
											var img = {
												// Width : 100,
												// Height : 100,
												Url : aUrl,
												Title : frm
														.findField('up_name')
														.getValue()
											};
											this.insertImage(img);
										},
										failure : function(response) {
											Ext.MessageBox
													.alert('提示', '文件上传失败');
										},
										scope : this
									});
									Ext.getCmp('imgWindow2').hide();
								} else {
									if (!frm.findField('file1').isValid()) {
										// frm.findField('url').getEl().frame();
									}
								}
							},
							scope : this
						}, {
							text : '取消',
							id : 'btnUploadCancel2',
							iconCls : 'deleteIcon',
							handler : function() {
								Ext.getCmp('imgWindow2').hide();
							},
							scope : this
						}]
					}, {
						title : '<span style="font-weight:normal">链接网络图片</span> ',
						items : [{
									xtype : 'form',
									itemId : 'insert-img2',
									id : 'insert-img2',
									border : false,
									plain : true,
									bodyStyle : 'padding: 10px;',
									labelWidth : 60,
									labelAlign : 'right',
									items : [{
												xtype : 'textfield',
												fieldLabel : '图片URL',
												name : 'url',
												anchor : '100%',
												allowBlank : false,
												emptyText : '请填入支持外链的长期有效的图片URL'
											}, {
												xtype : 'textfield',
												fieldLabel : '图片说明',
												name : 'name',
												maxLength : 100,
												anchor : '100%',
												emptyText : '简短的图片说明'
											}]
								}],
						buttons : [{
							text : '插入',
							iconCls : 'acceptIcon',
							id : 'btnLinkInset',
							handler : function() {
								var frm = Ext.getCmp('insert-img2').getForm();
								if (frm.isValid()) {
									url = frm.findField('url').getValue();

									if (!this.isUrl(url)) {
										Ext.Msg.alert('提示',
												'URL不合法.请重新输入.格式[http://****]');
										return;
									};

									var img = {
										// Width : 100,
										// Height : 100,
										Url : frm.findField('url').getValue(),
										ID : 'id_img_9999',
										Title : frm.findField('name')
												.getValue()
									};
									this.insertImage(img);
									this.imgWindow2.hide();
								} else {
									if (!frm.findField('url').isValid()) {
										// frm.findField('url').getEl().frame();
									}
								}
							},
							scope : this
						}, {
							text : '取消',
							id : 'btnLinkCancel2',
							iconCls : 'deleteIcon',
							handler : function() {
								Ext.getCmp('imgWindow2').hide();
							},
							scope : this
						}]
					}]
				})],
				listeners : {
					show : {
						fn : function() {
							var frm = Ext.getCmp('insert-img2').getForm();
							frm.reset();
							var frm1 = Ext.getCmp('upload-img2').getForm();
							frm1.reset();
						},
						scope : this,
						defer : 350
					}
				}
			});
			this.imgWindow2.show();
		} else {
			this.imgWindow2.show();
			// this.imgWindow2.getEl().frame();
		}
	},
	insertImage : function(img) {
		this.cmp.insertAtCursor('<img src="' + img.Url + '" title="' + img.Name
				+ '" alt="' + img.Name + '">');
	}

});