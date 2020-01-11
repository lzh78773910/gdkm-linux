function post() {
    var parentId = $('#parentId').val();
    // var qId = $('#qId').val();
    var content = $('#content').val();
    commentTarget(parentId, content);
}

function commentTarget(parentId, content) {
    if (!content) {
        alert("不能回复空内容");
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/linux/byadmin/comment/addComment',
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": parentId,
            // "qId":qId,
            "content": content
        }),
        success: function (response) {
            if (response.code == 0) {
                window.location.reload();
            } else {
                alert("回复失败，试试登录后重试！");
            }
        }
    });
}

//二级评论回复
function comment(e) {
    var parentId = e.getAttribute("data-id");
    var content = $("#input-" + parentId).val();
    commentTarget(parentId, content);
}

//展开二级评论
function collapseComments(e) {
    var cId = e.getAttribute("data-id");
    var comments = $("#comment-" + cId);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + cId);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/linux/byadmin/comment/commentDetail/" + cId, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-circle testImg",
                        "src": comment.user.userIcon
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.userNickname
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.createtime).format('YYYY-MM-DD h:mm:ss')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}