package controllers;

import clients.IngredientNamesTestClient;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import play.mvc.Result;
import rules.RuleChainForTests;

import static extractors.DataFromResult.itemsSizeOf;
import static extractors.DataFromResult.statusOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.OK;

public class IngredientNamesControllerTest {
    private final RuleChainForTests ruleChainForTests = new RuleChainForTests();

    @Rule
    public RuleChain chain = ruleChainForTests.getRuleChain();

    private IngredientNamesTestClient client;

    @Before
    public void setup() {
        client = new IngredientNamesTestClient(ruleChainForTests.getApplication());
    }

    @Test
    // Given
    @DataSet(value = "datasets/yml/ingredientnames.yml", disableConstraints = true, cleanBefore = true)
    public void testListNamesHu() {
        // When
        Result result = client.page("languageId=1&nameLike=hu");

        // Then
        assertThat(statusOf(result), equalTo(OK));
        assertThat(itemsSizeOf(result), equalTo(5));
    }

    @Test
    // Given
    @DataSet(value = "datasets/yml/ingredientnames.yml", disableConstraints = true, cleanBefore = true)
    public void testPaging() {
        // When
        Result result = client.page("languageId=1&nameLike=hu&offset=2&limit=6");

        // Then
        assertThat(statusOf(result), equalTo(OK));
        assertThat(itemsSizeOf(result), equalTo(3));
    }
}
