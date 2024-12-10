@import exercise.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
    content = @`
        <form action="/users" method="get">
            <div class="mb-3">
                <label for="formGroupExampleInput" class="form-label">Users search</label>
                <input type="search" name="term" value="${page.getTerm()}" class="form-control" id="formGroupExampleInput" placeholder="Search users ..." />
            </div>
        </form>

    @if(!page.getUsers().isEmpty())
<%--        <table>--%>
<%--            <tr>--%>
<%--                <th>Id</th>--%>
<%--                <th>Name</th>--%>
<%--                <th>Email</th>--%>
<%--            </tr>--%>
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
