package exercise;

import io.javalin.Javalin;
import exercise.controller.UsersController;
import exercise.util.NamedRoutes;
import io.javalin.rendering.template.JavalinJte;

public final class App {

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // BEGIN
        app.get(NamedRoutes.buildUserPath(), UsersController::build);
        app.post(NamedRoutes.usersPath(), UsersController::create);
        // END

        app.get(NamedRoutes.userPath("{id}"), UsersController::show);
        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
