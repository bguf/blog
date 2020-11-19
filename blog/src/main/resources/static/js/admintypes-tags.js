/*初始化关闭按钮，点击x可以关闭*/
$('.message .close')
    .on('click', function ()
    {
        $(this)
            .closest('.message')
            .transition('fade');
    });

/*表单验证*/
$('.ui.form').form({
    fields: {
        name: {
            identifier: 'name',
            rules: [{
                type: 'empty',
                prompt: '分类名称不能为空'
            }]
        }
    }
});