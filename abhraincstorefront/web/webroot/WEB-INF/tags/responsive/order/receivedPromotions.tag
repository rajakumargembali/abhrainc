<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ attribute name="containerCSS" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty order.appliedOrderPromotions}">
    <ycommerce:testId code="order_recievedPromotions_label">
        <c:forEach items="${order.appliedOrderPromotions}" var="promotion">
            <div class="order-savings text-right">
                <ycommerce:testId code="orderDetails_orderPromotion_label">
                    <c:choose>
                        <c:when test="${not empty promotion.description}">
                            ${fn:escapeXml(promotion.description)}
                        </c:when>
                        <c:otherwise>
                            ${fn:escapeXml(promotion.promotionData.description)}
                        </c:otherwise>
                    </c:choose>
                </ycommerce:testId>
            </div>
        </c:forEach>
    </ycommerce:testId>
</c:if>
