<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<html>
  <head>
    <title>Paypal Payment Method</title>
  </head>
  <script>
  window.onload = function()
{
    
    setTimeout(function(){
    	window.close();
    },2000);
}

</script>
  <body>
<c:if test="${PaymentStatus== true}">
Thank you, your payment is successfull, please continue to place the order
</c:if>
<c:if test="${PaymentStatus== false}">
Your transaction is failed, Please try again 
</c:if>
  </body>
</html>