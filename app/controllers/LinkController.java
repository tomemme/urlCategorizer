package controllers;


import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.mvc.Result;

public class LinkController extends BaseController
{

    public Result getLinksMenu()
    {
        return ok(views.html.linksmenu.render());
    }
}
