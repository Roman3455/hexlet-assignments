package exercise;

import io.javalin.Javalin;

import java.util.Comparator;
import java.util.List;

import io.javalin.http.NotFoundResponse;
import exercise.model.User;
import exercise.dto.users.UserPage;
import exercise.dto.users.UsersPage;

import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.rendering.template.JavalinJte;

public final class App {

    // Каждый пользователь представлен объектом класса User
    private static final List<User> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // BEGIN
        app.get("/users", ctx -> {
            var page = new UsersPage(USERS.stream()
                    .sorted(Comparator.comparingLong(User::getId))
                    .toList());
            ctx.render("users/index.jte", model("page", page));
        });

        app.get("/users/{id}", ctx -> {
            var id = Long.parseLong(ctx.pathParam("id"));
            var userId = USERS.stream().filter(u -> u.getId() == id).findFirst();

            if (userId.isPresent()) {
                var page = new UserPage(userId.get());
                ctx.render("users/show.jte", model("page", page));
            } else {
                throw new NotFoundResponse("User not found");
            }
        });
        // END

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
