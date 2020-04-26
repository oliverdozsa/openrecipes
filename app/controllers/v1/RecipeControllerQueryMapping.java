package controllers.v1;

import static lombokized.repositories.RecipeRepositoryParams.QueryTypeNumber;
import static lombokized.repositories.RecipeRepositoryParams.QueryTypeRatio;
import static lombokized.repositories.RecipeRepositoryParams.Common;
import static lombokized.repositories.RecipeRepositoryParams.IncludedIngredients;
import static lombokized.repositories.RecipeRepositoryParams.AdditionalIngredients;
import static lombokized.repositories.RecipeRepositoryParams.Relation;

public class RecipeControllerQueryMapping {
    public static QueryTypeNumber toQueryTypeNumber(RecipesControllerQuery.Params queryParams) {
        QueryTypeNumber.Builder builder = QueryTypeNumber.builder();

        builder.common(toCommon(queryParams));
        builder.includedIngredients(toIncludedIngredients(queryParams));
        builder.goodIngredients(queryParams.goodIngs);
        builder.goodIngredientsRelation(Relation.fromString(queryParams.goodIngsRel));
        builder.unknownIngredients(queryParams.unknownIngs);
        builder.unknownIngredientsRelation(Relation.fromString(queryParams.unknownIngsRel));
        builder.additionalIngredients(toAdditionalIngredients(queryParams));

        return builder.build();
    }

    public static QueryTypeRatio toQueryTypeRatio(RecipesControllerQuery.Params queryParams) {
        QueryTypeRatio.Builder builder = QueryTypeRatio.builder();

        builder.common(toCommon(queryParams));
        builder.goodIngredientsRatio(queryParams.goodIngsRatio);
        builder.includedIngredients(toIncludedIngredients(queryParams));

        return builder.build();
    }

    public static Common toCommon(RecipesControllerQuery.Params queryParams) {
        Common.Builder builder = Common.builder();

        if (queryParams.exIngs != null && queryParams.exIngs.size() > 0) {
            builder.excludedIngredients(queryParams.exIngs);
        }

        if (queryParams.exIngTags != null && queryParams.exIngTags.size() > 0) {
            builder.excludedIngredientTags(queryParams.exIngTags);
        }

        builder.limit(queryParams.limit);
        builder.offset(queryParams.offset);
        builder.maximumNumberOfIngredients(queryParams.maxIngs);
        builder.minimumNumberOfIngredients(queryParams.minIngs);
        builder.nameLike(queryParams.nameLike);
        builder.orderBy(queryParams.orderBy);
        builder.orderBySort(queryParams.orderBySort);
        builder.sourcePageIds(queryParams.sourcePages);

        return builder.build();
    }

    private static IncludedIngredients toIncludedIngredients(RecipesControllerQuery.Params queryParams) {
        IncludedIngredients.Builder builder = IncludedIngredients.builder();

        if (queryParams.inIngs != null && queryParams.inIngs.size() > 0) {
            builder.includedIngredients(queryParams.inIngs);
        }

        if (queryParams.inIngTags != null && queryParams.inIngTags.size() > 0) {
            builder.includedIngredientTags(queryParams.inIngTags);
        }

        return builder.build();
    }

    private static AdditionalIngredients toAdditionalIngredients(RecipesControllerQuery.Params queryParams) {
        if (queryParams.goodAdditionalIngs == null) {
            return null;
        }

        AdditionalIngredients.Builder builder = AdditionalIngredients.builder();

        builder.goodAdditionalIngredients(queryParams.goodAdditionalIngs);
        if (queryParams.addIngs != null) {
            builder.additionalIngredients(queryParams.addIngs);
        }
        builder.additionalIngredientTags(queryParams.addIngTags);

        return builder.build();
    }
}
