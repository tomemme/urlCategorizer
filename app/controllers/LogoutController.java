package controllers;


import play.mvc.Result;

//TODO not sure how to implement
public class LogoutController extends BaseController
{
    public Result getLogout()
    {
        return ok(views.html.login.render());
    }
}
