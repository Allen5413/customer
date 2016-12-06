<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pop_content" style="width: 95%">
  <table id="linkManTable" class="table_slist" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <th width="5%" >序号</th>
      <th width="13%">姓名</th>
      <th width="15%">电话</th>
      <th width="13%">职务</th>
      <th width="10%">访谈次数</th>
      <th width="35%">备注</th>
      <th>操作</th>
    </tr>
  </table>
</div>
<script>
  $(function(){
    searchData();
  });

  function searchData(){
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/findLinkmanByCustomerId/find.htm",
      data:{"customerId":${customerId}},
      async: false,
      success: function(data) {
        if(data.state == 0){
          var table = $("#linkManTable");
          var num = 0;
          table.find("tr").each(function(){
            if(num > 0){
              $(this).remove();
            }
            num++;
          });

          var linkManList = data.linkmanList;
          if(linkManList.length > 0){

            for(var i = 0; i < linkManList.length; i++){
              var linkMan = linkManList[i];
              var tr = $("<tr></tr>");
              var td = $("<td>"+(i+1)+"</td>");
              var td2 = $("<td>"+linkMan.name+"</td>");
              var td3 = $("<td>"+linkMan.phone+"</td>");
              var td4 = $("<td>"+linkMan.post+"</td>");
              var td5 = $("<td>"+linkMan.interviewCount+"</td>");
              var td6 = $("<td>"+linkMan.remark+"</td>");
              var td7;
              if(linkMan.interviewCount > 0){
                td7 = $("<td></td>")
              }else{
                td7 = $("<td><a href='#' style='color: #0092DC' onclick='del("+linkMan.id+")'>删除</a></td>")
              }
              tr.append(td).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
              table.append(tr);
            }
          }
        }else{
          alert(data.msg);
        }
      }
    });
  }

  function del(id){
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/delLinkmanById/del.htm",
      data:{"id":id},
      async: false,
      success: function(data) {
        if(data.state == 0){
          alert("操作成功");
          searchData();
        }else{
          alert(data.msg);
        }
      }
    });
  }
</script>