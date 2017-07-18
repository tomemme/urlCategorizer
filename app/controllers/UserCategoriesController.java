package controllers;


import play.mvc.Result;

public class UserCategoriesController extends BaseController
{
    public Result getUserCategoriesMenu()
    {
        return ok(views.html.usercategories.render());
    }
}
