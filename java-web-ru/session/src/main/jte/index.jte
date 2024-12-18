@import exercise.util.NamedRoutes
@import exercise.dto.MainPage
@param MainPage page

@template.layout.page(
    content = @`
        @if (page.getName() != null)
            <p>${page.getName().toString()}</p>
            <form action="${NamedRoutes.logoutPath()}" method="post">
                <input type="button" class="btn btn-primary" value="Выйти" />
            </form>
        @else
            <form action="${NamedRoutes.buildSessionPath()}" method="get">
                <input type="button" class="btn btn-primary" value="Войти" />
            </form>
        @endif
    `
)
