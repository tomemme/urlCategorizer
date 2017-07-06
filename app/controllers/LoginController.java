package controllers;


import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;

public class LoginController extends BaseController
{
    FormFactory formFactory;

    @Inject
    public LoginController(FormFactory formFactory)
    {
        this.formFactory = formFactory;
    }

    public Result getLogin()
    {
        return ok(views.html.login.render());
    }

    public Result login()
    {
        Result result = unauthorized("Intruder Alert!");

        DynamicForm form = formFactory.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");

        if (password.equals("Passw0rd"))
        {
            login(username);
            result = redirect(routes.UserController.getUser(1));
        }

        return result;
    }
}
