//表单验证
$('.ui.form').form({
    fields: {
        managername: {
            identifier: 'nickname',
            rules: [{
                type: 'empty',
                prompt: '昵称不能为空'
            }]
        },
        password: {
            identifier: 'password',
            rules: [{
                type: 'empty',
                prompt: '密码不能为空'
            }]
        }
    }
});

/*初始化关闭按钮，点击x可以关闭*/
$('.message .close')
    .on('click', function ()
    {
        $(this)
            .closest('.message')
            .transition('fade');
    });
