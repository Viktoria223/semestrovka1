<#-- @ftlvariable name="userPair" type="ru.itis.servletsapp.dto.UserDto" -->

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Поиск пары</title>

    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/menu.css" rel="stylesheet">
    <link href="/resources/css/profile.css" rel="stylesheet">
    <script src="/resources/js/match.js"></script>
    <script src="/resources/js/jquery.min.js"></script>

</head>
<body>

<div class="container">

    <#include "menu.ftl">

    <div class="center-content">
        <div class="container">
            <#if userPair??>
            <div id="profile" class="white-container">

                <#if userPair.avatarId??>
                    <img class="user-avatar" alt="IMAGE" src="/files/${userPair.avatarId}"/>
                <#else>
                    <img class="user-avatar" alt="IMAGE" src="/no-avatar.png"/>
                </#if>

                <div class="user-info-text">
                    <strong class="user-info">${userPair.firstName} ${userPair.lastName}</strong>
                    <div class="user-info">${userPair.gender}</div>
                    <div class="user-info">Возраст: ${userPair.age}</div>
                    Описание:
                    <div class="user-info">${userPair.description}</div>
                </div>

            </div>


            <div class="divider"></div>
            <div class="user-info-text" style="display: flex">
                <button id="dislikeButton" class="button" onclick="disLikeButton(${userPair.id})">Не нравится</button>
                <button id="likeButton" class="button" onclick="likeButton(${userPair.id})">Нравится</button>
            </div>
            <#else>
                <strong>Похоже, пользователи кончились....</strong>
            </#if>
        </div>
        </div>

    </div>
</body>
</html>