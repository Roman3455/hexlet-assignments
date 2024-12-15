@import exercise.dto.articles.BuildArticlePage
@param BuildArticlePage page

@template.layout.page(
    content = @`
        @if(page.getErrors() != null)
            <ul>
                @for(var val : page.getErrors().values())
                    @for(var err : val)
                        <li>${err.getMessage()}</li>
                    @endfor
                @endfor
            </ul>
        @endif

        <form action="/articles" method="post">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="floatingInput" name="title">
                <label for="floatingInput">Title</label>
            </div>
            <div class="form-floating">
            <textarea class="form-control" id="floatingTextarea" style="height: 100px" name="content"></textarea>
                <label for="floatingTextarea">Content</label>
            </div>
            <input type="submit" class="btn btn-primary" value="Submit"/>
        </form>
    `
)
