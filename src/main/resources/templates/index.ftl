<html>
<head>
    <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
    <meta charset="utf-8">
    <!-- title 是浏览器显示的页面标题 -->
    <title>主页</title>
    <!-- style -->
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all" />
    <!-- scripts -->
    <script src="https://static2.cnodejs.org/public/index.min.f7c13f64.min.js"></script>
    <link rel="stylesheet" href="/static?file=index.css" media="all"/>
    <style>
        .topic-tab:hover {
            color: #80bd01
        }
    </style>
</head>
<body>
<!-- navbar -->
<div class='navbar'>
    <div class='navbar-inner'>
        <div class='container'>
            <ul class='nav pull-right'>
                <li><a href='/'>Home</a></li>
                <li><a href='/login'>Login</a></li>
                <li><a href='/todo'>Todo</a></li>
                <li><a href="/register">Register</a></li>
            </ul>
        </div>
    </div>
</div>
<div id='main'>
    <!-- sidebar -->
    <div id='sidebar'>
        <div class='panel'>
            <div class='header'>
                <span class='col_fade'>个人信息</span>
            </div>
            <div class='inner'>
                <div class='user_card'>
                    <div>
                        <a class='user_avatar' href="/">
                            <img src="https://www.kuaibiancheng.com/uploads/avatar/default.gif" title="username"/>
                        </a>
                        <span class='user_name'><a class='dark' href="/">${u.username}</a></span>
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
        <div class="panel">
            <div class='inner'>
                <a href='/topic/create' id='create_topic_btn'>
                    <span class='span-success'>发布话题</span>
                </a>
            </div>
        </div>
    </div>

    <div id="content">
        <div class="panel">
            <div class="header">

                <a href="/?tab=all"
                   class="topic-tab current-tab">全部</a>

                <a href="/?tab=share"
                   class="topic-tab ">分享</a>

                <a href="/?tab=ask"
                   class="topic-tab ">问答</a>
            </div>
            <div class="inner no-padding">
                <div id="topic_list">
                    <#list topics as t>
                    <div class="cell">
<#--                        开头-->
                        <a class="user_avatar pull-left" href="/">
                            <img src="https://www.kuaibiancheng.com/uploads/avatar/default.gif" title="i5ting">
                        </a>
                        <span class="reply_count pull-left">
                            <span class="count_of_replies" title="回复数">
                              ${t.replyCount}
                            </span>
                            <span class="count_seperator">/</span>
                            <span class="count_of_visits" title="点击数">
                              ${t.viewCount}
                            </span>
                        </span>
<#--                        尾部-->
                        <a class="last_time pull-right" href="/topic?id=${t.id}">
                            <img class="user_small_avatar"
                                 src="https://www.kuaibiancheng.com/uploads/avatar/default.gif">
                            <span class="last_active_time">${t.formattedTime(t.createdTime)}</span>
                        </a>
<#--                         中间-->
                        <div class="topic_title_wrapper">

                            <#--<span class="put_top"></span>-->
                            <span class="topiclist-tab">${t.tabFormat()}</span>

                            <a class="topic_title" href="/topic?id=${t.id}"
                               title="${t.title}">
                                ${t.title}
                            </a>
                        </div>
                    </div>
                    </#list>
                </div>
                <div class='pagination' current_page='1'>
                    <ul>
                        <li class='disabled'><a>«</a></li>
                        <li class='disabled'><a>1</a></li>
                        <li><a href='/?tab=all&amp;page=2'>2</a></li>
                        <li><a href='/?tab=all&amp;page=3'>3</a></li>
                        <li><a href='/?tab=all&amp;page=4'>4</a></li>
                        <li><a href='/?tab=all&amp;page=5'>5</a></li>
                        <li><a>...</a></li>
                        <li><a href='/?tab=all&amp;page=41'>»</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div id='backtotop'>回到顶部</div>
<div id='footer'>
    <div id='footer_main'>
        <h3>HAHAHAHAHAHAH</h3>
    </div>
</div>
</body>
</html>