package exercise;

import io.javalin.Javalin;
import io.javalin.http.UnprocessableContentResponse;
import io.javalin.validation.ValidationException;
import java.util.List;
import exercise.model.Article;
import exercise.dto.articles.ArticlesPage;
import exercise.dto.articles.BuildArticlePage;
import static io.javalin.rendering.template.TemplateUtil.model;
import io.javalin.rendering.template.JavalinJte;

import exercise.repository.ArticleRepository;

public final class App {

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        app.get("/articles", ctx -> {
            List<Article> articles = ArticleRepository.getEntities();
            var page = new ArticlesPage(articles);
            ctx.render("articles/index.jte", model("page", page));
        });

        // BEGIN
        app.get("/articles/build", ctx -> {
            var page = new BuildArticlePage();
            ctx.render("articles/build.jte", model("page", page));
        });

        app.post("/articles", ctx -> {
            String title;
            String content;
            try {
                title = ctx.formParamAsClass("title", String.class)
                        .check(ArticleRepository::existsByTitle, "This article already exists")
                        .check(value -> value.length() >= 2, "Title length must be more than 2 symbol")
                        .get();
                content = ctx.formParamAsClass("content", String.class)
                        .check(value -> value.length() >= 10, "Content must be more than 10 symbols")
                        .get();
                var article = new Article(title, content);
                ArticleRepository.save(article);
                ctx.redirect("/articles");
            } catch (ValidationException e) {
                title = ctx.formParam("title");
                content = ctx.formParam("content");
                var page = new BuildArticlePage(title, content, e.getErrors());
                ctx.render("articles/build.jte", model("page", page));
            }
        });
        // END

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
