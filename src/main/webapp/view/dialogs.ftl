<#-- @ftlvariable name="user" type="ru.itis.servletsapp.dto.UserDto" -->

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Друзья</title>

    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/menu.css" rel="stylesheet">
    <link href="/resources/css/headers.css" rel="stylesheet">
    <link href="/resources/css/profile.css" rel="stylesheet">

    <script src="/resources/js/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <#include "menu.ftl">
    <div class="center-content">
        <button class = "button text-center" onclick="location.href='/friends'" >Новый диалог</button>
        <div class="container emp-profile">
            <div class="container">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3" id="service-list">
                    <#if dialogs??>
                        <#list dialogs as dialog>
                            <div class="col" id = "${dialog.id}" >
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <div class="card-header text-center">
                                            <strong>${dialog.id}</strong>
                                        </div>
                                        <p class="card-text">${dialog.lastMsg?string("dd MMMM yyyy 'г.,' HH:mm")}</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="btn-group">
                                                <a href="/im?sel=${dialog.id}" type="button" class="btn btn-secondary" style="margin-right: 3%">Открыть диалог</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="divider"></div>
                        </#list>
                    <#else>
                        <h6>У тебя нет диалогов</h6>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>