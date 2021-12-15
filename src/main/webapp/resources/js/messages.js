function deleteMsg(tagId, postId) {

}

$(document).ready(function () {
    let form = $("#add-msg-form")
    let postsList = $("#msg-list")
    let dialogId = $("#dialogIdArea").val() //не знаю как лучше сделать, поэтому такой костыль
    form.on('submit', function () {
        let content = form.find("#content").val();
        if (content === '') {
            return false
        }
        $.ajax("/add-msg", {
            method: "POST",
            data: "content=" + content + "&dId=" + dialogId,
            headers: {
                'Encoding-Type': 'UTF-8',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (data) {
                let msgDto = JSON.parse(data)
                form.find("#content").val("")
                let postTag = $("<div style='text-align: end;'></div>")
                let options = { year: "numeric", month: "long", day: "numeric", hour: "numeric", minute: "numeric" }
                postTag.append("<div class=\"light_blue text\">" + new Date(msgDto.createdAt).toLocaleString('ru-RU', options) + "</div>")
                postTag.append("<div class=\"text\">" + msgDto.author.firstName + "</div>")
                postTag.append("<div class=\"text\">" + msgDto.content + "</div>")
                postTag.append("<div class=\"divider\"></div>")
                postTag.hide()
                postsList.prepend(postTag)
                postTag.show(300)
            }
        })
        return false
    })
})