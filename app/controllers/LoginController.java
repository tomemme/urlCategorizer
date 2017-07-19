package controllers;


import models.Password;
import models.UserLogin;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class LoginController extends BaseController
{
    FormFactory formFactory;
    JPAApi jpaApi;

    @Inject
    public LoginController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result getLogin()
    {
        return ok(views.html.login.render());
    }

    @Transactional
    public Result login()
    {
        Result result = unauthorized("Intruder Alert!");

        DynamicForm form = formFactory.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");

        String sql = "SELECT user_Id, password, salt, user_username FROM User WHERE user_username = :username";

        @SuppressWarnings("unchecked")
        List<UserLogin> userLogins = jpaApi.em().
                createNativeQuery(sql, UserLogin.class).
                setParameter("username", username).
                getResultList();

        if (userLogins.size() == 1)
        {

            UserLogin userLogin = userLogins.get(0);
            byte[] hashedPassword = Password.hashPassword(password.toCharArray(), userLogin.getSalt());
            byte[] dbpassword = userLogin.getPassword();

            if (Arrays.equals(hashedPassword, dbpassword))
            {
                login(userLogin.getUserId());
                //result = redirect(routes.UserController.getUser(userLogin.getUserId()));
                //TODO redirect to the menu page
                result = redirect(routes.MenuController.getMenu());
            }
        }

        return result;
    }

    @Transactional
    public Result getLogout()
    {
        return ok(views.html.logout.render());
    }

}
