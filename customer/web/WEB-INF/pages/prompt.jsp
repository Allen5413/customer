<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/taglibs.jsp"%>
</head>
<body style="background:#f8f8f8">
welcome

<FORM  name="submitnotify" METHOD=POST ACTION="/bankNotify/notify.htm">
    <font face='Arial' size='4' color='white'>银行通知信息提交页面</font>
    <table width="778"  border="1">
        <tr>
            <td>请输入notifyData数据</td>
            <td>
                <INPUT TYPE="text" NAME="notifyData" VALUE="PD94bWwgIHZlcnNpb249IjEuMCIgZW5jb2Rpbmc9IkdCSyIgc3RhbmRhbG9uZT0ibm8iID8+PEIyQ1Jlcz48aW50ZXJmYWNlTmFtZT5JQ0JDX1BFUkJBTktfQjJDPC9pbnRlcmZhY2VOYW1lPjxpbnRlcmZhY2VWZXJzaW9uPjEuMC4wLjExPC9pbnRlcmZhY2VWZXJzaW9uPjxvcmRlckluZm8+PG9yZGVyRGF0ZT4yMDEwMDkwMTE2MjE0Mjwvb3JkZXJEYXRlPjxjdXJUeXBlPjAwMTwvY3VyVHlwZT48bWVySUQ+MDIwMEVDMjAwMDExMTk8L21lcklEPjxzdWJPcmRlckluZm9MaXN0PjxzdWJPcmRlckluZm8+PG9yZGVyaWQ+MjAxMDA5MDExNjIxNDIwPC9vcmRlcmlkPjxhbW91bnQ+MTAwPC9hbW91bnQ+PGluc3RhbGxtZW50VGltZXM+MTwvaW5zdGFsbG1lbnRUaW1lcz48bWVyQWNjdD4wMjAwMDI2MDA5MDE4MzcyMjEyPC9tZXJBY2N0Pjx0cmFuU2VyaWFsTm8+SEZHMDAwMDAwMDAwMDAwNDIxPC90cmFuU2VyaWFsTm8+PC9zdWJPcmRlckluZm8+PHN1Yk9yZGVySW5mbz48b3JkZXJpZD4yMDEwMDkwMTE2MjE0MjE8L29yZGVyaWQ+PGFtb3VudD4xMjA8L2Ftb3VudD48aW5zdGFsbG1lbnRUaW1lcz4xPC9pbnN0YWxsbWVudFRpbWVzPjxtZXJBY2N0PjAyMDAwMjYwMDkwMTgzNzIyMTI8L21lckFjY3Q+PHRyYW5TZXJpYWxObz5IRkcwMDAwMDAwMDAwMDA0MjI8L3RyYW5TZXJpYWxObz48L3N1Yk9yZGVySW5mbz48L3N1Yk9yZGVySW5mb0xpc3Q+PC9vcmRlckluZm8+PGN1c3RvbT48dmVyaWZ5Sm9pbkZsYWc+MDwvdmVyaWZ5Sm9pbkZsYWc+PEpvaW5GbGFnPjwvSm9pbkZsYWc+PFVzZXJOdW0+PC9Vc2VyTnVtPjwvY3VzdG9tPjxiYW5rPjxUcmFuQmF0Y2hObz4wMDAwMDAwMDAwMDAwOTg8L1RyYW5CYXRjaE5vPjxub3RpZnlEYXRlPjIwMTAwOTEwMTc0MDUwPC9ub3RpZnlEYXRlPjx0cmFuU3RhdD4xPC90cmFuU3RhdD48Y29tbWVudD69u9LXs8m5pqOhPC9jb21tZW50PjwvYmFuaz48L0IyQ1Jlcz4=">
                <INPUT TYPE="text" NAME="signMsg" VALUE="auCDrEmFk0Hr0ItAsNZwL0CZD6LEpI/WKm/SMc1mnlZNZ8Hx4iZS1P1c+DrXG67BKh60YhcdjVyko18BN9k4QpVBMAsuRl57wffkMm8rNubS4foCyXhKN3RRjYwtu8lrxvGHvLWPNJv+eMjyrIoHUH5ZQYGuZYQGcY2iz+IldaY=">
                <INPUT TYPE="text" NAME="merVAR" VALUE="test">
            </td>
        </tr>
        <tr>
            <td>银行证书私钥密码</td>
            <td><INPUT type="password" NAME="bankCertKeyPasswd"  value="12345678"></td>
        </tr>
    </table>
    <input type="Submit" name = "submit" value = "submit"/>
</FORM>
</body>
</html>
