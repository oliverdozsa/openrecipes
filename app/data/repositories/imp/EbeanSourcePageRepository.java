package data.repositories.imp;

import data.DatabaseExecutionContext;
import data.entities.SourcePage;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Query;
import lombokized.repositories.Page;
import data.repositories.SourcePageRepository;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class EbeanSourcePageRepository implements SourcePageRepository {
    private EbeanServer ebean;
    private DatabaseExecutionContext executionContext;

    @Inject
    public EbeanSourcePageRepository(EbeanConfig dbConfig, DatabaseExecutionContext executionContext) {
        this.ebean = Ebean.getServer(dbConfig.defaultServer());
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Page<SourcePage>> allSourcePages() {
        return supplyAsync(() -> {
                    Query<SourcePage> q = ebean.find(SourcePage.class);
                    return new Page<>(q.findList(), q.findCount());
                }
                , executionContext);
    }
}
