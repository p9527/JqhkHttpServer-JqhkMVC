<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <!-- style -->
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all" />
    <#--markdown-->
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
    <!-- scripts -->
    <script src="//static2.cnodejs.org/public/index.min.f7c13f64.min.js"></script>
    <title>${t.title} - CNode技术社区</title>
</head>
<body>
<!-- navbar -->
<div class='navbar'>
    <div class='navbar-inner'>
        <div class='container'>
            <ul class='nav pull-right'>
                <li><a href='/'>Home</a></li>
                <li><a href='/login'>Login</a></li>
                <li><a href='/ajax/todo'>Todo</a></li>
                <li><a href="/register">Register</a></li>
            </ul>
        </div>
    </div>
</div>
<div id='main'>
    <div id='sidebar'>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>作者</span>
            </div>
            <div class='inner'>
                <div class='user_card'>
                    <div>
                        <a class='user_avatar' href="/">
                            <img src="https://www.kuaibiancheng.com/uploads/avatar/default.gif" title="username"/>
                        </a>
                        <span class='user_name'><a class='dark' href="/">${t.userName(t.userId)}</a></span>
                        <div class="space clearfix"></div>
                        <span class="signature">
                            “
                                这家伙很懒，什么个性签名都没有留下。
                            ”
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id='content'>
        <#--帖子主内容-->
        <div class='panel'>
            <div class='header topic_header'>
                <span class="topic_full_title">
                    ${t.title}
                </span>
                <div class="changes">
                    <span>
                      发布于${t.formattedTime(t.createdTime)}
                    </span>
                    <span>
                      作者 <a href="#">${t.userName(t.userId)}</a>
                    </span>
                    <span>
                      ${t.viewCount} 次浏览
                    </span>
                    <span> 来自 ${t.tabFormat()}</span>
                    <#--<input class="span-common span-success pull-right collect_btn" type="submit" value="收藏" action="collect">-->
                </div>
                <div id="manage_topic">
                </div>
            </div>
            <div class='inner topic'>
                <div class='topic_content'>
                    <div class="markdown-text"> ${t.content}</div>
                </div>
            </div>
        </div>
        <#--评论-->
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>${t.replyCount} 回复</span>
            </div>
            <#list comments as c>
                <div class='cell reply_area reply_item'>
                    <div class='author_content'>
                        <a href="#" class="user_avatar">
                            <img src="https://www.kuaibiancheng.com/uploads/avatar/default.gif" title="alsotang"/>
                        </a>
                        <div class='user_info'>
                            <a class='dark reply_author' href="#">${c.userName(c.userId)}</a>
                            <a class="reply_time" href="#">${c.floor}楼•${c.formattedTime(c.createdTime)}</a>
                        </div>
                        <div class='user_action'>
                        </div>
                    </div>
                    <div class='reply_content from-alsotang'>
                        <div class="markdown-text">${c.content}</div>
                    </div>
                    <div class='clearfix'>
                    </div>
                </div>

            </#list>
        </div>
        <#--添加回复-->
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>添加回复</span>
            </div>
            <div class='inner reply'>
                <form id='reply_form' action='/comment/add' method='post'>
                    <div class='markdown_editor in_editor'>
                        <div class='markdown_in_editor'>
                            <input name="topicId" value="${t.id}" hidden>
                            <textarea class='editor' name='content' rows='8'></textarea>
                            <div class='editor_buttons'>
                                <input class='span-primary submit_btn' type="submit" data-loading-text="回复中.." value="回复" >
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
</div>
    <script>
        var markList = document.querySelectorAll(".markdown-text")
        for (var i = 0; i < markList.length; i++) {
            var e = markList[i]
            var text = e.innerHTML;
            // text = strip(text)
            e.innerHTML = marked(text)
        }
    </script>
</body>
</html>
