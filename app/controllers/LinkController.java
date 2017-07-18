package controllers;


import models.Category;
import models.Link;
import models.LinkForm;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LinkController extends BaseController
{
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public LinkController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result getLinkMenu()
    {
        return ok(views.html.linkmenu.render());
    }

    @Transactional(readOnly = true)
    public Result addLink()
    {
        List<String> errorMessages = new ArrayList<>();

        int userId = getUserId();
        List<Category> categories =
                jpaApi.em().
                        createQuery("SELECT c FROM Category c WHERE user_Id = :id " +
                                "ORDER BY category_name", Category.class).setParameter("id", userId)
                        .getResultList();

        return ok(views.html.newlink.render(new LinkForm(), categories, errorMessages));
    }

    @Transactional
    public Result addNewLink()
    {
        Result result;
        List<String> errorMessages = new ArrayList<>();

        DynamicForm form = formFactory.form().bindFromRequest();

        LinkForm linkForm = new LinkForm();
        linkForm.linkUrl = form.get("linkurl");
        linkForm.linkRating = form.get("linkrating");
        linkForm.linkWatchBy = form.get("linkwatchby");
        linkForm.linkComments = form.get("linkcomments");
        linkForm.categoryId = form.get("categoryId");

        boolean valid = true;

        if(linkForm.linkUrl.length() > Link.URL_LINK_MAX_LENGTH)
        {
            valid = false;
            errorMessages.add("Link Url must be " + Link.URL_LINK_MAX_LENGTH + " characters or less");
        }

        if(linkForm.linkComments.length() > Link.LINK_COMMENTS_MAX_LENGTH)
        {
            valid = false;
            errorMessages.add("Link Comments must be " + Link.LINK_COMMENTS_MAX_LENGTH + " characters or less");
        }

        if(linkForm.linkRating.length() > Link.LINK_RATING_MAX_LENGTH)
        {
            valid = false;
            errorMessages.add("Link Rating must be " + Link.LINK_RATING_MAX_LENGTH + " characters or less");
        }

        if (valid)
        {
            Link link = new Link();
            link.setUserId(getUserId());
            link.setCategoryId(Integer.parseInt(linkForm.categoryId));
            link.setLinkRating(Integer.parseInt(linkForm.linkRating));
            link.setLinkUrl(linkForm.linkUrl);
            link.setLinkComments(linkForm.linkComments);
            link.setLinkWatchBy(LocalDate.parse(linkForm.linkWatchBy));

            jpaApi.em().persist(link);

            result = ok(views.html.linkmenu.render());

        }
        else
        {
            int userId = getUserId();
            List<Category> categories =
                    jpaApi.em().
                            createQuery("SELECT c FROM Category c WHERE user_Id = :id " +
                                    "ORDER BY category_name", Category.class).setParameter("id", userId)
                            .getResultList();

            result = ok(views.html.newlink.render(linkForm, categories ,errorMessages));
        }

        return result;
    }

    //TODO change view to take all parameters and route to edit link
    @Transactional
    public Result updateLink()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        int linkId = Integer.parseInt(form.get("id"));
        int categoryId = Integer.parseInt(form.get("id"));
        String linkUrl = form.get("linkurl");
        int linkRating = Integer.parseInt(form.get("linkrating"));
        String linkComments = form.get("linkcomments");
        LocalDate linkWatchBy = LocalDate.parse(form.get("linkWatchBy"));

        Link link =
                jpaApi.em().
                        createQuery("SELECT l FROM Link l WHERE linkId = :id", Link.class).
                        setParameter("id", linkId).
                        getSingleResult();

        int userId = getUserId();
        List<Category> categories =
                jpaApi.em().
                        createQuery("SELECT c FROM Category c WHERE user_Id = :id " +
                                "ORDER BY category_name", Category.class).setParameter("id", userId)
                        .getResultList();

        link.setLinkId(linkId);
        link.setCategoryId(categoryId);
        link.setLinkUrl(linkUrl);
        link.setLinkRating(linkRating);
        link.setLinkComments(linkComments);
        link.setLinkWatchBy(linkWatchBy);

        jpaApi.em().persist(link);

        return ok(views.html.link.render(link, categories));
    }

    @Transactional(readOnly = true)
    public Result getLink(Integer id)
    {
        Link link = jpaApi.em().
                createQuery("SELECT l FROM Link l WHERE linkId = :id", Link.class).
                setParameter("id", id).
                getSingleResult();


        Query usersQuery = jpaApi.em().createQuery("SELECT l FROM Link l " +
                                                            "WHERE linkId <> :id " +
                                                            "ORDER BY link_url", Link.class);

        usersQuery.setParameter("id", id);
        List<Link> links = usersQuery.getResultList();

        int userId = getUserId();
        List<Category> categories =
                jpaApi.em().
                        createQuery("SELECT c FROM Category c WHERE user_Id = :id " +
                                "ORDER BY category_name", Category.class).setParameter("id", userId)
                        .getResultList();

        Link none = new Link();
        none.setLinkId(-1);
        links.add(0, none);

        return ok(views.html.link.render(link, categories));
    }

    @Transactional(readOnly = true)
    public Result getLinks()
    {
        Result result = unauthorized("No soup for you!");

        int userId = getUserId();

        if (loggedIn())
        {
            List<Link> links =
                            jpaApi.em().
                            createQuery("SELECT l FROM Link l " +
                                                "WHERE user_Id = :id " +
                                                "ORDER BY link_url", Link.class)
                                    .setParameter("id", userId).getResultList();

            result = ok(views.html.links.render(links));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Result linkSearch()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        int userId = getUserId();

        String searchLinkUrl = form.get("linkurl");
        Logger.debug(searchLinkUrl);

        Query query = jpaApi.em().
                createQuery("SELECT l FROM Link l " +
                                    "WHERE Link_Url LIKE :searchLinkUrl AND user_Id = :id " +
                                    "ORDER BY link_url", Link.class);

        query.setParameter("searchLinkUrl", searchLinkUrl + "%");
        query.setParameter("id", userId);

        List<Link> links = query.getResultList();

        return ok(views.html.links.render(links));
    }

}
