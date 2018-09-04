<html>

<body>
<h2>ssm demo</h2>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.js"></script>
<script type="text/javascript">

    $.getJSON("/blog/detail/35", function (data) {
        console.log(data)
    })

    $.get("/blog/msg", function (data) {
        console.log(data)
    })

</script>
</body>
</html>
