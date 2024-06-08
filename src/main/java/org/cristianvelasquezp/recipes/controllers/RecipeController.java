package org.cristianvelasquezp.recipes.controllers;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.cristianvelasquezp.recipes.entities.Direction;
import org.cristianvelasquezp.recipes.entities.Ingredient;
import org.cristianvelasquezp.recipes.entities.Recipe;
import org.cristianvelasquezp.recipes.entities.User;
import org.cristianvelasquezp.recipes.models.RecipeResponse;
import org.cristianvelasquezp.recipes.repositories.RecipeRepository;
import org.cristianvelasquezp.recipes.repositories.UserRepository;
import org.cristianvelasquezp.recipes.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final UserRepository userRepository;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(UserRepository userRepository, RecipeService recipeService) {
        this.userRepository = userRepository;
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<RecipeResponse> getRecipeModels() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody RecipeResponse getRecipeModel(@PathVariable Long id) {
        try {
            return recipeService.getRecipeById(id);
        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<RecipeResponse> getRecipeModelsByCategory(@RequestParam Map<String, String> parameters) {
        List<RecipeResponse> recipeResponses;
        String name = parameters.get("name");
        String category = parameters.get("category");
        if (parameters.size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (category != null && !category.isEmpty()) {
            recipeResponses = recipeService.getRecipeByCategory(category);
        } else if (name !=null && !name.isEmpty()) {
            recipeResponses = recipeService.getRecipeByName(name);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return recipeResponses;
    }

    @PostMapping("/new")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody RecipeResponse setRecipeModelNew(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Recipe recipe) {
        return recipeService.saveRecipeWithUser(recipe, details.getUsername());
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable Long id, @Valid @RequestBody Recipe recipeEntity) {
        try {
            recipeEntity.setId(id);
            recipeService.updateRecipeWithUser(recipeEntity, details.getUsername());
        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable Long id) {
        try {
           recipeService.deleteRecipeById(id, details.getUsername());
        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: User with email " + ex.getMessage().split(" ")[5] + " already exists!");
    }
}
