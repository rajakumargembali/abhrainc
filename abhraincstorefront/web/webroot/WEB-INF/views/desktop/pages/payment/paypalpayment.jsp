<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
  <head>
    <title>Paypal Payment Method</title>
  </head>
  <body>

	<iframe src='https://pilot-payflowlink.paypal.com?SECURETOKEN="${SECURETOKEN}"&SECURETOKENID="${SECURETOKENID}"'
width='490' height='565'border='0' frameborder='0' scrolling='no' allowtransparency='true'></iframe>

  </body>
</html>