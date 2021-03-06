package data.repositories.imp;

import data.DatabaseExecutionContext;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Query;
import data.entities.IngredientName;
import data.repositories.IngredientNameRepository;
import lombokized.repositories.Page;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class EbeanIngredientNameRepository implements IngredientNameRepository {
    private EbeanServer ebean;
    private DatabaseExecutionContext executionContext;

    @Inject
    public EbeanIngredientNameRepository(EbeanConfig config, DatabaseExecutionContext executionContext) {
        this.ebean = Ebean.getServer(config.defaultServer());
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Page<IngredientName>> page(String nameLike, Long languageId, int limit, int offset) {
        return supplyAsync(() -> {
            Query<IngredientName> query = ebean.createQuery(IngredientName.class);

            query.where().ilike("name", "%" + nameLike + "%");
            query.where().eq("language.id", languageId);

            query.setFirstRow(offset);
            query.setMaxRows(limit);

            return new Page<>(query.findList(), query.findCount());
        }, executionContext);
    }
}
