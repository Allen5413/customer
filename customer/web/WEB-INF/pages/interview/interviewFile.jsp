<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <form id="editForm" name="editForm" action="${pageContext.request.contextPath}/editInterview/editor.htm" method="post">
    <input type="hidden" name="id" value="${interview.id}"/>
    <ul class="fill_form">
      <c:if test="${empty interviewFileList}">
        <li>
          该访谈记录没有上传图片
        </li>
      </c:if>
      <c:if test="${!empty interviewFileList}">
        <c:forEach var="interviewFile" items="${interviewFileList}">
          <li>
            <img src="${interviewFile.url}">
          </li>
        </c:forEach>
      </c:if>
    </ul>
  </form>
</div>

