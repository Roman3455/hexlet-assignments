@import exercise.util.NamedRoutes
@import exercise.dto.LoginPage
@param LoginPage page

@template.layout.page(
    content = @`
        @if (page.getError() != null)
            <div class="mb-3">
                <ul>
                    <li>${page.getError()}</li>
                </ul>
            </div>
        @endif

        <main class="form-signin w-100 m-auto">
            <form action="${NamedRoutes.loginPath()}" method="post">
                <h2 class="h3 mb-3 fw-normal">Please sign in</h2>
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInput" name="name" value="${page.getName()}">
                    <label for="floatingInput">Username</label>
                </div>
                <div class="form-floating">
                    <input type="password" class="form-control" id="floatingPassword" name="password">
                    <label for="floatingPassword">Password</label>
                </div>
                <button class="btn btn-primary w-100 py-2" type="submit">Sign in</button>
            </form>
        </main>
    `
)
