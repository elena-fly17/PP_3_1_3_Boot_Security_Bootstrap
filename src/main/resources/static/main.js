
$('document').ready(function() {

    $('.table #editButton').on('click',function(event){
        event.preventDefault();
        var href= $(this).attr('href');
        $.get(href, function(user, status){
           $('#idEdit').val(user.id);
           $('#nameEdit').val(user.name);
           $('#surnameEdit').val(user.surname);
           $('#ageEdit').val(user.age);
           $('#emailEdit').val(user.email);
        });
        $('#editModal').modal();
    });

    $('.table #deleteButton').on('click',function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function(user, status){
            $('#idDelete').val(user.id);
            $('#nameDelete').val(user.name);
            $('#surnameDelete').val(user.surname);
            $('#ageDelete').val(user.age);
            $('#emailDelete').val(user.email);
        });
        $('#deleteModal').modal();
    });
});