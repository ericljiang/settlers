package me.ericjiang.settlers.auth;

import spark.Request;
import spark.Response;

public abstract class Authenticator {

    public abstract Object renderLoginPage(Request request, Response response);

    public abstract Object login(Request request, Response response);

    public abstract Object logout(Request request, Response response);

    public boolean sessionIsAuthenticated(Request request, Response response) {
        String userId = (String) request.session().attribute("userId");
        return userId != null;
    }
}