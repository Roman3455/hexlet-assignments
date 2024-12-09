@import exercise.dto.users.UserPage
@param UserPage page

@template.layout.page(
content = @`
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <tr>
            <th>${page.getUser().getId()}</th>
            <th>${page.getUser().getFirstName()} ${page.getUser().getLastName()}</th>
            <th>${page.getUser().getEmail()}</th>
        </tr>
    </table>
`
)
