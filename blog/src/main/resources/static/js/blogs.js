/*向下滚动，功能菜单栏显示，向上滚动到一定位置，功能菜单栏隐藏*/
var waypoint = new Waypoint({
    element: document.getElementById('waypoint'),
    handler: function (direction)
    {
        if (direction == 'down')
        {
            $('#toolbar').show();
        } else
        {
            $('#toolbar').hide();
        }
    }
});
/*点击向上的图标，向上滚动*/
$('#toTop-btn').click(function ()
{
    $(window).scrollTo(0, 500);
});

/*鼠标经过此按钮，弹出生成的二维码*/
$('.wechat-blog').popup({
    popup : $('#wechatQRId'),
    position : 'left center'
});
/*
  * 目录结构tocbot初始化
  * */
tocbot.init({
    // Where to render the table of contents.
    tocSelector: '.js-toc',
    // Where to grab the headings to build the table of contents.
    contentSelector: '.js-toc-content',
    // Which headings to grab inside of the contentSelector element.
    headingSelector: 'h1, h2, h3',
    // For headings inside relative or absolute positioned containers within content.
    hasInnerContainers: true,
});

/*生成目录*/
$('.catalog-btn').popup({
    popup: $('.catalog.popup'),
    on: 'click',
    position: 'left center'
});
/*点击赞赏按钮，弹出两个二维码*/
$('#paybutton').popup({
    popup : $('.pay'),
    on : 'click',
    position : 'top center'
});

/*回复评论*/
function reply(obj)
{
    var commentid = $(obj).data('commentid');
    var commentnickname = $(obj).data('commentnickname');
    $("[name='content']").attr("placeholder", "@"+commentnickname).focus();
    $("[name='parentComment.id']").val(commentid);
    $(window).scrollTo($("#comment-form"), 500);
}

/*表单验证*/
$('.ui.form').form({
    fields : {
        content : {
            identifier : 'content',
            rules : [{
                type : 'empty',
                prompt : '评论内容不能为空'
            }]
        },
        nickname : {
            identifier : 'nickname',
            rules : [{
                type : 'empty',
                prompt : '昵称不能为空'
            }]
        },
        email : {
            identifier : 'email',
            rules : [{
                type : 'email',
                prompt : 'email格式有误'
            }]
        }
    }
});
//下拉框
$('.ui.dropdown').dropdown({
    on : 'hover'
});
