<html>

<body>
<h2>ssm demo</h2>

<%=System.getProperty("catalina.base")%>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.js"></script>
<script type="text/javascript">

    // $.getJSON("/blog/detail/35", function (data) {
    //     console.log(data)
    // })
    //
    // $.get("/blog/msg", function (data) {
    //     console.log(data)
    // })

    //添加 @RequestBody
    var obj = {title: "title"};
    $.ajax({
        url: "/blog/add",
        type: "post",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify(obj),
        success: function (data) {
            console.log(data);
        },
        error: function () {
        }
    })
</script>
</body>
</html>
