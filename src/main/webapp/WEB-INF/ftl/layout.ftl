<#macro bannerLayout>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>关爱通企业收银台</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/qrcode.js"></script>
</head>

<body>

<div class="container">

    <#include "header.ftl">

    <#nested >

    <#include "footer.ftl">


</div>

</body>

</html>
</#macro>