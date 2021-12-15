function likeButton(likedId){
    $.ajax("/set-match", {
        method: "POST",
        data: "likedId=" + likedId,
        headers: {
            'Encoding-Type': 'UTF-8',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (data) {
            window.location.reload();
        }
    })

    console.log(likedId)
}
function disLikeButton(disLikedId){
    $.ajax("/set-match", {
        method: "POST",
        data: "disLikedId=" + disLikedId,
        headers: {
            'Encoding-Type': 'UTF-8',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (data) {
            window.location.reload();
        }
    })

    console.log(disLikedId)
}