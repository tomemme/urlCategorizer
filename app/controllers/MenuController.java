package controllers;


import play.mvc.Controller;
import play.mvc.Result;

public class MenuController extends Controller
{

    public Result getMenu()
    {
        return ok(views.html.menu.render());
    }
}
