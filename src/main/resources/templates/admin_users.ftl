<!DOCTYPE html>
<html>
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>Admin</title>
    </head>
    <!-- body 中是浏览器要显示的内容 -->
    <h1>Users</h1>
    <body>
        <form action="/admin/user/update" method="post">
            <input type="text" name="id" placeholder="请输入ID">
            <br>
            <input type="text" name="password" placeholder="请输入密码">
            <br>
            <button type="submit">修改</button>
        </form>
        <div>
            <#list users as u>
                <div>${u.id}: ${u.username} : ${u.password} </div>
            </#list>
        </div>
    </body>
</html>
