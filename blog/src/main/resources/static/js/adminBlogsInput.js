/*
* markdown插件引入使用
**/
var editor;
$(function ()
{
    editor = editormd("markdown-content", {
        width: "100%",
        height: 640,
        syncScrolling: "single",
        path: "/lib/markdown/lib/"
    });
});

//表单验证
$('.ui.form').form({
    fields : {
        title : {
            identifier : 'title',
            rules : [{
                type : 'empty',
                prompt : '博客标题不能为空'
            }]
        },
        content : {
            identifier : 'content',
            rules : [{
                type : 'empty',
                prompt : '博客内容不能为空'
            }]
        },
        typeId : {
            identifier : 'typeId',
            rules : [{
                type : 'empty',
                prompt : '博客分类不能为空'
            }]
        },
        firstPicture : {
            identifier : 'firstPicture',
            rules : [{
                type : 'empty',
                prompt : '博客首图不能为空'
            }]
        },
        tagIds : {
            identifier : 'tagIds',
            rules : [{
                type : 'empty',
                prompt : '博客标签不能为空'
            }]
        }
    }
});

//点击发布或保存，提交
$('#save-btn').click(function ()
{
    $("[name='published']").val(false);
    $('#form-blog').submit();
});
$('#publish-btn').click(function ()
{
    $("[name='published']").val(true);
    $('#form-blog').submit();
});