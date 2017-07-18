package controllers;

import models.Category;
import models.CategoryForm;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CategoryController extends BaseController
{
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    //TODO add and edit categories
    @Inject
    public CategoryController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result getCategoryMenu()
    {
        return ok(views.html.categorymenu.render());
    }

    public Result addCategory()
    {
        List<String> errorMessages = new ArrayList<>();
        return ok(views.html.newcategory.render(new CategoryForm(), errorMessages));
    }

    @Transactional
    public Result addNewCategory()
    {
        Result result;
        List<String> errorMessages = new ArrayList<>();

        DynamicForm form = formFactory.form().bindFromRequest();

        CategoryForm categoryForm = new CategoryForm();
        categoryForm.categoryName = form.get("categoryname");


        boolean valid = true;

        if (categoryForm.categoryName.length() > Category.CATEGORY_NAME_MAX_LENGTH)
        {
            valid = false;
            errorMessages.add("Category Name must be " + Category.CATEGORY_NAME_MAX_LENGTH + " characters or less");
        }

        if (valid)
        {
            Category category = new Category();
            category.setCategoryName(categoryForm.categoryName);
            category.setUserId(getUserId());

            jpaApi.em().persist(category);

            result = ok(views.html.categorymenu.render());

        }
        else
        {
            result = ok(views.html.newcategory.render(categoryForm, errorMessages));
        }

        return result;
    }

    @Transactional
    public Result updateCategory()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        //TODO null number format exception
        int categoryId = Integer.parseInt(form.get("id"));
        String categoryName = form.get("categoryname");

        Category category =
                jpaApi.em().
                        createQuery("SELECT c FROM Category c WHERE categoryId = :id", Category.class).setParameter("id", categoryId).
                        getSingleResult();

        category.setCategoryName(categoryName);
        category.setCategoryId(categoryId);

        jpaApi.em().persist(category);

        return ok(views.html.category.render(category));

    }

    @Transactional
    public Result getCategory()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        int categoryId = Integer.parseInt(form.get("id"));

        Category category = jpaApi.em().
                createQuery("SELECT c FROM Category c WHERE categoryId = :id", Category.class).
                setParameter("id", categoryId).getSingleResult();


        return ok(views.html.category.render(category));
    }

    @Transactional(readOnly = true)
    public Result getCategory(Integer id)
    {
        Category category =
                jpaApi.em().
                        createQuery("SELECT c FROM Category c WHERE categoryId = :id", Category.class).
                        setParameter("id", id).
                        getSingleResult();


        Query usersQuery = jpaApi.em().createQuery("SELECT c FROM Category c " +
                                                            "WHERE categoryId <> :id " +
                                                            "ORDER BY category_name", Category.class);

        usersQuery.setParameter("id", id);
        List<Category> categories = usersQuery.getResultList();

        Category none = new Category();
        none.setCategoryId(-1);
        none.setCategoryName("None");
        categories.add(0, none);

        return ok(views.html.category.render(category));
    }

    @Transactional(readOnly = true)
    public Result getCategories()
    {
        Result result = unauthorized("No soup for you!");

        int userId = getUserId();

        if (loggedIn())
        {
            List<Category> categories =
                    jpaApi.em().
                            createQuery("SELECT c FROM Category c " +
                                                "WHERE user_Id = :id " +
                                                "ORDER BY category_name", Category.class)
                            .setParameter("id", userId)
                            .getResultList();

            result = ok(views.html.categories.render(categories));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Result categorySearch()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        int userId = getUserId();

        String searchCategoryName = form.get("categoryname");
        Logger.debug(searchCategoryName);

        Query query = jpaApi.em().
                createQuery("SELECT c FROM Category c WHERE Category_Name LIKE :searchCategoryName AND user_Id = :id ORDER BY category_name", Category.class);

        query.setParameter("searchCategoryName", searchCategoryName + "%");
        query.setParameter("id", userId);

        List<Category> categories = query.getResultList();

        return ok(views.html.categories.render(categories));
    }


}

