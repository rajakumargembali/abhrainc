<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
 
<template:page pageTitle="${pageTitle}">
    <div id="globalMessages">
        <common:globalMessages/>
    </div>
    
    
        <cms:pageSlot position="offersParagraph" var="feature" element="div" class="offersBannerParagraph">
        <cms:component component="${feature}" />
    </cms:pageSlot>
    <div class="offerBannerParentDiv">
    <cms:pageSlot position="offersBanner" var="feature" element="div" class="span-24 section5 offersBannerImg">
    <div class="offerBannerChildDiv">
        <cms:component component="${feature}" />
        </div>
    </cms:pageSlot>
    </div>
</template:page>