<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<template:page pageTitle="${pageTitle}">


<iframe width="100%" height="350px" frameborder="0" src="https://a.tiles.mapbox.com/v4/settu.p8de0lfo.html?access_token=pk.eyJ1Ijoic2V0dHUiLCJhIjoiY2lsMXhiNG8zM2E2aXV5a3NzZGQxdHZnciJ9._INN7xEP0ijCqOSKQuBh_A"></iframe>

	<form:form method="post" commandName="registerForm" action="/abhraincstorefront/electronics/en/contactus">
		<div class="form_field-elements js-recaptcha-captchaaddon">
			<formElement:formSelectBox idKey="register.title" labelKey="register.title" path="titleCode" mandatory="true" skipBlank="false" skipBlankMessageKey="form.select.empty" items="${titles}"/>
			<formElement:formInputBox idKey="register.firstName" labelKey="register.firstName" path="firstName" inputCSS="text" mandatory="true"/>
			<formElement:formInputBox idKey="register.lastName" labelKey="register.lastName" path="lastName" inputCSS="text" mandatory="true"/>
			<formElement:formInputBox idKey="register.email" labelKey="register.email" path="email" inputCSS="text" mandatory="true"/>
			<formElement:formPasswordBox idKey="password" labelKey="register.pwd" path="pwd" inputCSS="text password strength" mandatory="true"/>
			<formElement:formPasswordBox idKey="register.checkPwd" labelKey="register.checkPwd" path="checkPwd" inputCSS="text password" mandatory="true"/>

		</div>
		<div class="form-actions clearfix">
			<ycommerce:testId code="register_Register_button">
				<button type="submit" class="positive">Submit</button>
			</ycommerce:testId>
		</div>
	</form:form>

</template:page>