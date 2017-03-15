<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form method="get" action="events.do"> 
<input type="submit" value="Refresh" />
</form>

<table>
     <thead>
		<tr>
			<th>Seq.No</th>
			<th>Device.ID</th>
			<th>(Gas) Units Leaked</th>
			<th>Leak Date</th>			
		</tr>
     </thead>
      <tbody>
        <c:forEach items="${events}" var="event">
          <tr>
          	<td><c:out value="${event.seqno}"/></td>  
          	<td><c:out value="${event.deviceid}"/></td>  
           	<td><c:out value="${event.payload}"/></td>
           	<td><c:out value="${event.enqueuetime}"/></td>           	          	
         		                        	         	
          </tr>
        </c:forEach>
      </tbody> 



</table>