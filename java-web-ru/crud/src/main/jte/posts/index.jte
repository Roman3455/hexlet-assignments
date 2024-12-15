@import exercise.util.NamedRoutes
@import exercise.repository.PostRepository
@import exercise.dto.posts.PostsPage
@param PostsPage page

@template.layout.page(
content = @`
    <h1 class="display-6">Все посты</h1>
    <table class="table table-striped table-hover">
        <tr>
            <th>Id</th>
            <th>Post</th>
        </tr>
        @for(var post : page.getPosts())
            <tr>
                <td>${post.getId()}</td>
                <td><a href="${NamedRoutes.postPath(post.getId())}">${post.getName()}</a></td>
            </tr>
        @endfor
    </table>
    <nav aria-label="Posts navigation">
        <ul class="pagination">
            <li class="page-item">
                @if(page.getPageNumber() - 1 != 0)
                    <a class="page-link" href="?page=${page.getPageNumber() - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                @endif
            </li>
            <li class="page-item">
                <a class="page-link" href="${NamedRoutes.postsPath()}?page=${page.getPageNumber()}">${page.getPageNumber()}</a>
            </li>
            <li class="page-item">
                @if(PostRepository.getEntities().size() - page.getPageNumber() * 5 >= 0)
                    <a class="page-link" href="?page=${page.getPageNumber() + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                @endif
            </li>
        </ul>
    </nav>
`
)
