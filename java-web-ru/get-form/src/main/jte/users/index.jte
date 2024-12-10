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
        ${page.getUsers().toString()}
    @endif
    `
)
