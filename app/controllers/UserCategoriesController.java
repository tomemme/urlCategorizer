package controllers;


import models.Category;
import models.Link;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;


public class UserCategoriesController extends BaseController
{
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public UserCategoriesController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getCategoryLinks()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        int userId = getUserId();

        String categoryId = form.get("categoryId");

        Integer catId = 0;

        if (categoryId != null && categoryId.length() > 0)
        {
            catId = Integer.parseInt(categoryId);
        }

        Logger.debug("categoryId " + categoryId);

        List<Category> categories =
                jpaApi.em().
                        createQuery("SELECT c FROM Category c WHERE user_Id = :id " +
                                "ORDER BY category_name", Category.class).setParameter("id", userId)
                        .getResultList();

        List<Link> links = jpaApi.em().createQuery("SELECT l FROM Link l WHERE category_Id = :catId " +
                                                " AND user_id = :id", Link.class)
                                                .setParameter("id", userId).setParameter("catId", catId)
                                                .getResultList();

        return ok(views.html.usercategories.render(categories, links, catId));
    }








}
