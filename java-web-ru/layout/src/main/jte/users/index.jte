@import exercise.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
    content = @`
    @if(page.getUsers().isEmpty())
        <p>Users not found</p>
    @else
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Email</th>
            </tr>
            @for(var user : page.getUsers())
                <tr>
                    <th>${user.getId()}</th>
                    <th><a href="/users/${user.getId()}">${user.getFirstName()} ${user.getLastName()}</a></th>
                    <th>${user.getEmail()}</th>
                </tr>
            @endfor
        </table>
    @endif
    `
)
