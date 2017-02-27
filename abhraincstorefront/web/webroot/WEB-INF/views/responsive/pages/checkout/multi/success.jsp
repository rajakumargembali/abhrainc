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
  <body>
	<!-- Thanks for making successful payment. Kinly click on the below Next button.  -->

	
	<iframe src='https://pilot-payflowlink.paypal.com?SECURETOKEN=${SECURETOKEN}&SECURETOKENID=${SECURETOKENID}'
	width='500' height='500'border='0' frameborder='0' scrolling='no' allowtransparency='true'></iframe>

  </body>
</html>