package controllers;

import models.User;
import models.UserForm;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserController extends BaseController
{
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public UserController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result addUser()
    {
        List<String> errorMessages = new ArrayList<>();
        return ok(views.html.newuser.render(new UserForm(), errorMessages));
    }

    @Transactional
    public Result addNewUser()
    {
        Result result;
        List<String> errorMessages = new ArrayList<>();

        DynamicForm form = formFactory.form().bindFromRequest();

        UserForm userForm = new UserForm();
        userForm.firstName = form.get("firstname");
        userForm.lastName = form.get("lastname");
        userForm.userUsername = form.get("userusername");
        userForm.password = form.get("password");
        userForm.userCreated = form.get("usercreated");

        boolean valid = true;

        if (userForm.firstName.length() > User.FIRST_NAME_MAX_LENGTH)
        {
            valid = false;
            errorMessages.add("First Name must be " + User.FIRST_NAME_MAX_LENGTH + " characters or less");
        }

        if (userForm.lastName.length() > User.LAST_NAME_MAX_LENGTH)
        {
            valid = false;
            errorMessages.add("Last Name must be " + User.LAST_NAME_MAX_LENGTH + " characters or less");
        }

        if (valid)
        {
            User user = new User();
            user.setFirstName(userForm.firstName);
            user.setLastName(userForm.lastName);
            user.setUserUsername(userForm.userUsername);
            //TODO fix password
            user.setPassword(userForm.password.getBytes());
            user.setUserCreated(LocalDateTime.now());

            jpaApi.em().persist(user);

            result = redirect(routes.UserController.getUser(user.getUserId()));
        }
        else
        {
            result = ok(views.html.newuser.render(userForm, errorMessages));
        }

        return result;
    }

    @Transactional
    public Result updateUser()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        int userId = Integer.parseInt(form.get("id"));
        String firstName = form.get("firstname");
        String lastName = form.get("lastname");


        User user =
                jpaApi.em().
                        createQuery("SELECT u FROM User u WHERE userId = :id", User.class).
                        setParameter("id", userId).
                        getSingleResult();

        user.setFirstName(firstName);
        user.setLastName(lastName);

        jpaApi.em().persist(user);

        return ok(views.html.menu.render());


    }

    @Transactional
    public Result getMyUser()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        int userId = getUserId();

        User user = jpaApi.em().
                createQuery("SELECT u FROM User u WHERE user_Id = :id", User.class).
                setParameter("id", userId).getSingleResult();



        return ok(views.html.user.render(user));
    }



    @Transactional(readOnly = true)
    public Result getUser(Integer id)
    {
        User user =
                jpaApi.em().
                        createQuery("SELECT u FROM User u WHERE userId = :id", User.class).
                        setParameter("id", id).
                        getSingleResult();


        Query usersQuery = jpaApi.em().createQuery("SELECT u FROM User u " +
                "WHERE userId <> :id " +
                "ORDER BY last_name, first_name", User.class);

        usersQuery.setParameter("id", id);
        List<User> users = usersQuery.getResultList();

        User none = new User();
        none.setUserId(-1);
        none.setLastName("None");
        users.add(0, none);

        return ok(views.html.user.render(user));
    }

    @Transactional(readOnly = true)
    public Result getUsers()
    {
        Result result = unauthorized("No soup for you!");

        if (loggedIn())
        {
            List<User> users =
                    jpaApi.em().
                            createQuery("SELECT u FROM User u ORDER BY last_name, first_name", User.class).getResultList();

            result = ok(views.html.users.render(users));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Result userSearch()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        String searchLastName = form.get("lastname");
        Logger.debug(searchLastName);

        Query query = jpaApi.em().
                createQuery("SELECT u FROM User u WHERE Last_Name LIKE :searchLastName ORDER BY last_name, first_name", User.class);

        query.setParameter("searchLastName", searchLastName + "%");

        List<User> users = query.getResultList();

        return ok(views.html.users.render(users));
    }


}
