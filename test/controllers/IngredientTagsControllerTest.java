package controllers;

import clients.IngredientTagsTestClient;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import play.mvc.Result;
import rules.RuleChainForTests;

import static extractors.DataFromResult.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.BAD_REQUEST;
import static play.test.Helpers.OK;

public class IngredientTagsControllerTest {
    private final RuleChainForTests ruleChainForTests = new RuleChainForTests();

    @Rule
    public RuleChain chain = ruleChainForTests.getRuleChain();

    private IngredientTagsTestClient client;

    @Before
    public void setup() {
        client = new IngredientTagsTestClient(ruleChainForTests.getApplication());
    }

    @Test
    // Given
    @DataSet(value = "datasets/yml/ingredienttags.yml", disableConstraints = true, cleanBefore = true)
    public void testListTags() {
        // When
        Result result = client.page("languageId=1&nameLike=_tag_");

        // Then
        assertThat(statusOf(result), equalTo(OK));
        assertThat(totalCountOf(result), equalTo(7));
    }

    @Test
    // Given
    @DataSet(value = "datasets/yml/ingredienttags.yml", disableConstraints = true, cleanBefore = true)
    public void testPaging() {
        int limit = 2;
        int offset = 0;
        boolean lastPageNotReached = true;

        while (lastPageNotReached) {
            // When
            String queryParams = String.format("languageId=1&nameLike=_tag_&limit=%d&offset=%d", limit, offset);
            Result result = client.page(queryParams);

            // Then
            assertThat(statusOf(result), equalTo(OK));
            assertThat(totalCountOf(result), equalTo(7));
            assertThat(itemsSizeOf(result), lessThanOrEqualTo(limit));

            int totalCount = totalCountOf(result);
            lastPageNotReached = offset + limit < totalCount;
            offset += limit;
        }
    }

    @Test
    // Given
    @DataSet(value = "datasets/yml/ingredienttags.yml", disableConstraints = true, cleanBefore = true)
    public void testInvalidRequest() {
        // When
        String invalidLenghtNameLikeParams = "languageId=1&nameLike=a";
        Result result = client.page(invalidLenghtNameLikeParams);

        // Then
        assertThat(statusOf(result), equalTo(BAD_REQUEST));
    }

}
