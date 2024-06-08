package org.cristianvelasquezp.recipes.services;

import org.cristianvelasquezp.recipes.entities.Direction;
import org.cristianvelasquezp.recipes.entities.Ingredient;
import org.cristianvelasquezp.recipes.entities.Recipe;
import org.cristianvelasquezp.recipes.entities.User;
import org.cristianvelasquezp.recipes.models.RecipeResponse;
import org.cristianvelasquezp.recipes.repositories.RecipeRepository;
import org.hibernate.grammars.hql.HqlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @InjectMocks
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UserService userService;

    List<RecipeResponse> recipeResponses;
    List<Recipe> recipes;

    @BeforeEach
    public void setUp() {
        recipes = createRecipeList();
        recipeResponses = convertRecipeListToRecipeResponse(recipes);
    }


    @Test
    @DisplayName("Test for getAllRecipes method")
    public void getAllRecipes_returnsRecipeList() {
        when(recipeRepository.findAll()).thenReturn(recipes);
        List<RecipeResponse> response = recipeService.getAllRecipes();
        assertEquals(5, response.size());
        assertEquals(recipeResponses.get(1), response.get(1));
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test for getRecipeById method")
    public void getRecipeById_returnsRecipe() {
        Recipe recipe = recipes.get(0);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        RecipeResponse response = recipeService.getRecipeById(1L);

        assertEquals(recipeResponses.get(0), response);
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test for getRecipeByCategory method")
    public void getRecipeByCategory_returnsRecipeList() {
        Recipe[] recipesByCategory = recipes.stream().filter(recipe -> recipe.getCategory().equalsIgnoreCase("beverage")).toArray(Recipe[]::new);
        List<RecipeResponse> recipeResponsesByCategory = recipeResponses.stream().filter(recipe -> recipe.getCategory().equalsIgnoreCase("beverage")).toList();

        when(recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc("category")).thenReturn(recipesByCategory);

        List<RecipeResponse> response = recipeService.getRecipeByCategory("category");

        assertEquals(2, response.size());
        verify(recipeRepository, times(1)).findAllByCategoryIgnoreCaseOrderByDateDesc("category");
    }

    @Test
    @DisplayName("Test for getRecipeByName method")
    public void getRecipeByName_returnsRecipeList() {
        Recipe[] recipesByName = recipes.stream().filter(recipe -> recipe.getName().contains("Tea")).toArray(Recipe[]::new);
        List<RecipeResponse> recipeResponsesByName = recipeResponses.stream().filter(recipe -> recipe.getName().contains("Tea")).toList();

        when(recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc("Tea")).thenReturn(recipesByName);

        List<RecipeResponse> response = recipeService.getRecipeByName("Tea");

        assertEquals(1, response.size());
        verify(recipeRepository, times(1)).findAllByNameContainingIgnoreCaseOrderByDateDesc("Tea");
    }

    @Test
    @DisplayName("Test for saveRecipeWithUser method")
    public void saveRecipeWithUser_returnsRecipeResponse() {
        Recipe recipe = recipes.get(0);
        when(userService.findUserByEmail("Cook_Programmer@somewhere.com")).thenReturn(createUser());
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        RecipeResponse response = recipeService.saveRecipeWithUser(recipe, "Cook_Programmer@somewhere.com");

        assertEquals(recipeResponses.get(0), response);
        verify(userService, times(1)).findUserByEmail("Cook_Programmer@somewhere.com");
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    @DisplayName("Test for updateRecipeWithUser method")
    public void updateRecipeWithUser_returnsNothing() {
        Recipe recipe = recipes.get(0);
        recipe.setUser(createUser());
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        recipeService.updateRecipeWithUser(recipe, "Cook_Programmer@somewhere.com");

        verify(recipeRepository, times(1)).findById(1L);

    }

    private List<Recipe> createRecipeList() {
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe1 = new Recipe.Builder()
                .id(1L)
                .name("Fresh Mint Tea")
                .category("beverage")
                .description("Light, aromatic and refreshing beverage")
                .ingredients(Arrays.asList(new Ingredient("boiled water"), new Ingredient("honey"), new Ingredient("fresh mint leaves")))
                .directions(Arrays.asList(new Direction("Boil water"), new Direction("Pour boiling hot water into a mug"), new Direction("Add fresh mint leaves"), new Direction("Mix and let the mint leaves seep for 3-5 minutes"), new Direction("Add honey and mix again")))
                .build();
        recipes.add(recipe1);

        Recipe recipe2 = new Recipe.Builder()
                .id(2L)
                .name("Hot Chocolate")
                .category("beverage")
                .description("Rich, creamy and utterly indulgent hot chocolate")
                .ingredients(Arrays.asList(new Ingredient("milk"), new Ingredient("cocoa powder"), new Ingredient("sugar")))
                .directions(Arrays.asList(new Direction("Heat milk"), new Direction("Add cocoa powder and sugar"), new Direction("Stir until well mixed")))
                .build();
        recipes.add(recipe2);

        Recipe recipe3 = new Recipe.Builder()
                .id(3L)
                .name("Chicken Salad")
                .category("main course")
                .description("Healthy and delicious chicken salad")
                .ingredients(Arrays.asList(new Ingredient("chicken"), new Ingredient("lettuce"), new Ingredient("tomatoes"), new Ingredient("cucumbers")))
                .directions(Arrays.asList(new Direction("Cook chicken"), new Direction("Chop lettuce, tomatoes, and cucumbers"), new Direction("Mix all ingredients")))
                .build();
        recipes.add(recipe3);

        Recipe recipe4 = new Recipe.Builder()
                .id(4L)
                .name("Apple Pie")
                .category("dessert")
                .description("Classic apple pie with a flaky crust")
                .ingredients(Arrays.asList(new Ingredient("apples"), new Ingredient("sugar"), new Ingredient("flour"), new Ingredient("butter")))
                .directions(Arrays.asList(new Direction("Prepare the crust"), new Direction("Mix apples with sugar and flour"), new Direction("Fill crust with apple mixture"), new Direction("Bake until golden brown")))
                .build();
        recipes.add(recipe4);

        Recipe recipe5 = new Recipe.Builder()
                .id(5L)
                .name("Tomato Soup")
                .category("starter")
                .description("Warm and comforting tomato soup")
                .ingredients(Arrays.asList(new Ingredient("tomatoes"), new Ingredient("onion"), new Ingredient("garlic"), new Ingredient("basil")))
                .directions(Arrays.asList(new Direction("Saute onions and garlic"), new Direction("Add tomatoes and cook until soft"), new Direction("Blend until smooth"), new Direction("Serve with a sprinkle of basil")))
                .build();
        recipes.add(recipe5);

        return recipes;
    }

    private List<RecipeResponse> convertRecipeListToRecipeResponse(List<Recipe> recipes) {
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        for (Recipe recipe : recipes) {
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
            recipeResponses.add(recipeResponse);
        }
        return recipeResponses;
    }

    private User createUser() {
        return new User("user", "Cook_Programmer@somewhere.com", "12345");
    }
}
