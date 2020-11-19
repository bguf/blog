$('.ui.dropdown').dropdown({
    on : 'hover'
});

function page(obj)
{
    $("[name='page']").val($(obj).data('page'));
    loaddata();
}

$("#searchbtn").click(function ()
{
    $("[name='page']").val(0);
    loaddata();
});

function loaddata()
{
    $('#table-content').load(/*[[@{/admin/blogs/search}]]*/"/admin/blogs/search",
    {
        title : $("[name='title']").val(),
        typeId : $("[name='typeId']").val(),
        recommend : $("[name='recommend']").prop('checked'),
        page : $("[name='page']").val()
    });
}
/*初始化关闭按钮，点击x可以关闭*/
$('.message .close')
    .on('click', function ()
    {
        $(this)
            .closest('.message')
            .transition('fade');
    });

$('#clear')
    .on('click', function ()
    {
        $('.dropdown')
            .dropdown('clear')
    });