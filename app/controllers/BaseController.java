package controllers;

import play.mvc.Controller;

public class BaseController extends Controller
{
    private static String userIdToken = "userId";

    public void login(int userId)
    {
        session().put(userIdToken, "" + userId);
    }

    public boolean loggedIn()
    {
        String userId = session().get(userIdToken);

        return (userId != null && userId.length() > 0);
    }

    public void logout()
    {
        session().remove(userIdToken);
    }

    public int getUserId()
    {
        int userId = Integer.parseInt(session().get(userIdToken));


        return userId;
    }
}
