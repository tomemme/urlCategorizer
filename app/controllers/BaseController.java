package controllers;

import play.mvc.Controller;

public class BaseController extends Controller
{
    private static String usernameToken = "username";

    public void login(String userName)
    {
        session().put(usernameToken, userName);
    }

    public boolean loggedIn()
    {
        String userName = session().get(usernameToken);

        return (userName != null && userName.length() > 0);
    }

    public void logout()
    {
        session().remove(usernameToken);
    }
}
