package clients;

import controllers.v1.routes;
import play.Application;
import play.mvc.Http;
import play.mvc.Result;

import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class IngredientTagsTestClient {
    private final Application application;

    public IngredientTagsTestClient(Application application) {
        this.application = application;
    }

    public Result page(String queryParams) {
        Http.RequestBuilder request = createPageRequest(queryParams);
        return route(application, request);
    }

    private Http.RequestBuilder createPageRequest(String queryParams) {
        return new Http.RequestBuilder().method(GET)
                .uri(routes.IngredientTagsController.pageTags().url() + "?" + queryParams);
    }
}
