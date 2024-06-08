package org.cristianvelasquezp.recipes.services;

import org.cristianvelasquezp.recipes.entities.Direction;
import org.cristianvelasquezp.recipes.entities.Ingredient;
import org.cristianvelasquezp.recipes.entities.Recipe;
import org.cristianvelasquezp.recipes.models.RecipeResponse;
import org.cristianvelasquezp.recipes.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private UserService userService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    public List<RecipeResponse> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(this::convertRecipeToRecipeResponse)
                .collect(toList());
    }

    public RecipeResponse getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(IndexOutOfBoundsException::new);
        return convertRecipeToRecipeResponse(recipe);
    }

    public List<RecipeResponse> getRecipeByCategory(String category) {
        List<Recipe> recipes = List.of(recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category));
        return recipes.stream()
                .map(this::convertRecipeToRecipeResponse)
                .collect(toList());
    }

    public List<RecipeResponse> getRecipeByName(String name) {
        List<Recipe> recipes = List.of(recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name));
        return recipes.stream()
                .map(this::convertRecipeToRecipeResponse)
                .collect(toList());
    }

    public RecipeResponse saveRecipeWithUser(Recipe recipe, String username) {
        recipe.setUser(userService.findUserByEmail(username));
        Recipe newRecipe = recipeRepository.save(recipe);
        return convertRecipeToRecipeResponse(newRecipe);

    }

    public void updateRecipeWithUser(Recipe recipe, String username) {
        Recipe existingRecipe = recipeRepository.findById(recipe.getId()).orElseThrow(IndexOutOfBoundsException::new);
        if (!existingRecipe.getUser().getEmail().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipe.setUser(existingRecipe.getUser());
        recipeRepository.save(recipe);
    }

    public void deleteRecipeById(Long id, String username) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(IndexOutOfBoundsException::new);
        if (!recipe.getUser().getEmail().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeRepository.deleteById(id);
    }

    private RecipeResponse convertRecipeToRecipeResponse(Recipe recipe) {
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setName(recipe.getName());
        recipeResponse.setDescription(recipe.getDescription());
        recipeResponse.setCategory(recipe.getCategory());
        recipeResponse.setDate(recipe.getDate());
        List<String> ingredients = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredients.add(ingredient.getValue());
        }
        recipeResponse.setIngredients(ingredients);
        List<String> directions = new ArrayList<>();
        for (Direction direction : recipe.getDirections()) {
            directions.add(direction.getValue());
        }
        recipeResponse.setDirections(directions);
        return recipeResponse;
    }
}
