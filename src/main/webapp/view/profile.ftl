<#-- @ftlvariable name="user" type="ru.itis.servletsapp.dto.UserDto" -->

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль</title>

    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/menu.css" rel="stylesheet">
    <link href="/resources/css/profile.css" rel="stylesheet">

</head>
<body>

<div class="container">

    <#include "menu.ftl">

    <div class="center-content">
        <div class="container">
            <div class="title">Профиль</div>
            <div id="profile" class="white-container">

                <#if user.avatarId??>
                    <img class="user-avatar" alt="IMAGE" src="/files/${user.avatarId}"/>
                <#else>
                    <img class="user-avatar" alt="IMAGE" src="/no-avatar.png"/>
                </#if>

                <div class="user-info-text">
                    <div class="user-info">Имя: ${user.firstName}</div>
                    <div class="user-info">Фамилия: ${user.lastName}</div>
                    <div class="user-info">${user.gender}</div>
                    <div class="user-info">${user.email}</div>
                    <div class="user-info">Возраст: ${user.age}</div>
                </div>

            </div>


            <div class="divider"></div>
            <div class="user-info-text">
                Описание:
                <div class="user-info">${user.description}</div>
            </div>
        </div>
    </div>

</div>
</body>
</html>