package controllers;


import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;

public class CategoryController extends Controller
{
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    //TODO add and edit categories
    public CategoryController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }


}
