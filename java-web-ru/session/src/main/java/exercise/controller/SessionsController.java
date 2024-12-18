package exercise.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import exercise.dto.MainPage;
import exercise.dto.LoginPage;
import exercise.repository.UsersRepository;

import static exercise.util.Security.encrypt;

import exercise.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class SessionsController {

    // BEGIN
    public static void index(Context ctx) {
        var page = new MainPage(ctx.sessionAttribute("currentUser"));
        ctx.render("index.jte", model("page", page));
    }

    public static void build(Context ctx) {
        String name = ctx.formParam("name");
        var page = new LoginPage(name, null);
        ctx.render("build.jte", model("page", page));
    }

    public static void create(Context ctx) {
        String name = ctx.formParam("name");
        String password = encrypt(ctx.formParam("password"));

        try {
            var user = UsersRepository.findByName(name)
                    .orElseThrow(() -> new NotFoundResponse("Wrong username or password"));
            if (user.getPassword().equals(password)) {
                ctx.sessionAttribute("currentUser", name);
                ctx.redirect(NamedRoutes.rootPath());
            } else {
                throw new NotFoundResponse("Wrong username or password");
            }

        } catch (Exception e) {
            var page = new LoginPage(name, e.getMessage());
            ctx.render("build.jte", model("page", page));
        }
    }

    public static void destroy(Context ctx) {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect(NamedRoutes.rootPath());
    }
    // END
}
