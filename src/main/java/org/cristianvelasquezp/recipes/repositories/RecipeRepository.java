package org.cristianvelasquezp.recipes.repositories;

import org.cristianvelasquezp.recipes.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

        Recipe[] findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

        Recipe[] findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
