<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<h3>Response</h3>

<#if statusCode??>
    <p><b>Status code:</b> ${statusCode}</p>
</#if>

<#if statusLine??>
    <p><b>Status line:</b> ${statusLine}</p>
</#if>

<#if headers??>
    <p><b>Headers:</b></p>
    <pre>${headers}</pre>
</#if>

<#if body?? && (body?length > 0)>
    <p><b>Body:</b></p>
    <pre>${body}</pre>
</#if>

</body>
</html>