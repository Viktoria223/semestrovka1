<#-- @ftlvariable name="user" type="ru.itis.servletsapp.dto.UserDto" -->

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Настройки</title>

    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/menu.css" rel="stylesheet">
    <link href="/resources/css/profile.css" rel="stylesheet">

    <script src="/resources/js/jquery.min.js"></script>
    <script src="/resources/js/match.js"></script>
</head>
<body>

<div class="container">

    <#include "menu.ftl">

    <div class="center-content">
        <div class="container">
            <div class="title">Настройки</div>
            <div id="profile" class="white-container">

                <#if user.avatarId??>
                    <img class="user-avatar" alt="IMAGE" src="/files/${user.avatarId}"/>
                <#else>
                    <img class="user-avatar" alt="IMAGE" src="/no-avatar.png"/>
                </#if>

                <h4>Обновить аватар: </h4>
                <form action="/update-avatar" method="post" enctype="multipart/form-data">
                    <input type="file" name="file">
                    <input type="submit" value="File Upload">
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>