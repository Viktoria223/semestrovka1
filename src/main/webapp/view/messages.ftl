<#-- @ftlvariable name="user" type="ru.itis.servletsapp.dto.UserDto" -->
<#-- @ftlvariable name="msgs" type="java.util.List<ru.itis.servletsapp.dto.MsgDto>" -->

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Сообщения</title>

    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/menu.css" rel="stylesheet">
    <link href="/resources/css/profile.css" rel="stylesheet">

    <script src="/resources/js/jquery.min.js"></script>
    <script src="/resources/js/messages.js"></script>
</head>
<body>

<div class="container">

    <#include "menu.ftl">

    <div class="center-content">
        <div class="container">
            <div class="title">Диалог</div>
            <textarea id="dialogIdArea" style="visibility: hidden">${dialog.id}</textarea>
            <div id="profile" class="white-container" style="display: flex;">
                <#if interloc.avatarId??>
                    <img class="user-avatar" style="width: 55px; height: 55px; margin: 2% 2% 2% 2%;" alt="IMAGE"
                         src="/files/${interloc.avatarId}"/>
                <#else>
                    <img class="user-avatar" style="width: 55px; height: 55px; margin: 2% 2% 2% 2%;" alt="IMAGE"
                         src="/no-avatar.png"/>
                </#if>
                <div style="display: flex;">
                    <div class="user-info" style="margin: auto 2%;">${interloc.firstName}</div>
                    <div class="user-info" style="margin: auto 2%;">${interloc.lastName}</div>
                </div>
                <button class="button" style="margin: auto" onclick="location.href='/profile?userId=${interloc.id}'">
                    Открыть профиль
                </button>
            </div>

            <form id="add-msg-form" action="/add-msg" method="post">
                <label>
                    Ваше сообщение:
                    <textarea id="content" class="input_green" required name="content"></textarea>
                </label>
                <input class="button1" value="Отправить" type="submit">
            </form>

            <div class="divider"></div>

            <div id="msg-list">
                <#if messages??>
                    <#list messages as msg>
                        <div id="msg${msg.id}"
                                <#if msg.author.id == user.id>
                                    style="text-align: end;"
                                <#else>
                                    style="text-align: start;"
                                </#if>
                        >
                            <div class="light_blue text">${msg.createdAt?string("dd MMMM yyyy 'г.,' HH:mm")}</div>
                            <div class="text">${msg.author.firstName ! " NO NAME"}</div>
                            <div class="text">${msg.content}</div>

                        </div>
                    </#list>
                <#else>
                    <h6 class = "text-center"> Сообщений пока нет </h6>
                </#if>
            </div>
        </div>
    </div>

</div>
</body>
</html>