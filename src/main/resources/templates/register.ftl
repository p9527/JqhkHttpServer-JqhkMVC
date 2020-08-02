<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- meta -->
    <meta charset="utf-8"/>
    <!-- style -->
    <link rel="stylesheet" href="//static2.cnodejs.org/public/stylesheets/index.min.23a5b1ca.min.css" media="all"/>
    <!-- scripts -->
    <script src="//static2.cnodejs.org/public/index.min.f7c13f64.min.js"></script>
    <title>test</title>
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
                <span class='col_fade'>关于</span>
            </div>
            <div class='inner'>
                <p>test</p>

                <p>在这里你可以：</p>
                <ul>
                    <li>向别人提出你遇到的问题</li>
                    <li>帮助遇到问题的人</li>
                    <li>分享自己的知识</li>
                    <li>和其它人一起进步</li>
                </ul>
            </div>
        </div>
    </div>
    <div id='content'>
        <div class='panel'>
            <div class='header'>
                <ul class='breadcrumb'>
                    <li><a href='/'>主页</a><span class='divider'>/</span></li>
                    <li class='active'>注册</li>
                </ul>
            </div>
            <div class='inner'>
                <form id='signin_form' class='form-horizontal' action='/register' method='post'>
                    <div class='control-group'>
                        <label class='control-label' for='name'>用户名</label>
                        <div class='controls'>
                            <input class='input-xlarge' id='name' name='username' size='30' type='text'/>
                        </div>
                    </div>
                    <div class='control-group'>
                        <label class='control-label' for='pass'>密码</label>
                        <div class='controls'>
                            <input class='input-xlarge' id='pass' name='password' size='30' type='password'/>
                        </div>
                    </div>

                    <div class='control-group'>
                        <label class='control-label' for='pass'>昵称</label>
                        <div class='controls'>
                            <input class='input-xlarge' id='nick' name='nickname' size='30' type='text'/>
                        </div>
                    </div>
                    <div class='form-actions'>
                        <input type='submit' class='span-primary' value='注册'/>
                    </div>
                    <h3 class="text-center">${registerResult}</h3>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>