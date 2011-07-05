<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="${sysTitle}" showLoading="false" exportParams="true" isSubPage="false">
<eRedG4:import src="/arm/js/login.js" />
<eRedG4:body>
	<div id="hello-win" class="x-hidden">
		<div id="hello-tabs">
			<img border="0" width="450" height="70" src="<%=request.getAttribute("bannerPath") == null ? request.getContextPath() + "/resource/image/login_banner.png" : request.getAttribute("bannerPath")  %>" />
		</div>
	</div>
	<div id="aboutDiv" class="x-hidden"
		style='color: black; padding-left: 10px; padding-top: 10px; font-size: 12px'>
		易道系统集成与应用开发平台 V1.1 (eRedG4&reg)<br> <br> 版权所有 &copy 2010
		eRedLab&reg <br> <br> <b>技术支持</b><br>
		Email:eredlab@vip.qq.com<br> <br> 访问项目主页:<a
			href="http://code.google.com/p/g4-xiongchun/" target="_blank"
			title="点此访问eRedG4资源下载站">http://code.google.com/p/g4-xiongchun/</a>
	</div>
	<div id="infoDiv" class="x-hidden"
		style='color: black; padding-left: 10px; padding-top: 10px; font-size: 12px'>
		请注册后登录...<br> <br> 做最厚道的开源项目,将开源进行到底!<br> 
	</div>
</eRedG4:body>
</eRedG4:html>