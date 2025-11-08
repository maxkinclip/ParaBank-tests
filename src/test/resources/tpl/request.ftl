<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<h3>Request</h3>

<#if method??>
    <p><b>Method:</b> ${method}</p>
</#if>

<#if url??>
    <p><b>URL:</b> ${url}</p>
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