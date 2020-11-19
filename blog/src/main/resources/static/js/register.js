//表单验证
$('.ui.form').form({
    fields: {
        password: {
            identifier: 'password',
            rules: [{
                type: 'empty',
                prompt: '密码不能为空'
            }]
        },
        nickname: {
            identifier: 'nickname',
            rules: [{
                type: 'empty',
                prompt: '昵称不能为空'
            }]
        },
        email: {
            identifier: 'email',
            rules: [{
                type: 'empty',
                prompt: '邮箱不能为空'
            }]
        },
        avatar: {
            identifier: 'avatar',
            rules: [{
                type: 'empty',
                prompt: '图像不能为空'
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