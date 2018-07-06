<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="ts" value="<%=new java.util.Date().getTime()%>" />
<script type="text/javascript">
	var _path = '${ctx}/';
</script>
<body>
</body>
</html>