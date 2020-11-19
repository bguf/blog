//移动端显示功能菜单，pc端隐藏功能菜单
$('#showbar').click(function ()
{
    $('.nav-item').toggleClass('mobile-hidden');
});
//下拉框
$('.ui.dropdown').dropdown({
    on : 'hover'
});