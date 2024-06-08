package org.cristianvelasquezp.recipes.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class RecipeResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private LocalDateTime date;
    private List<String> ingredients;
    private List<String> directions;

    public RecipeResponse() {
    }

    public RecipeResponse(Long id, String name, String description, String category, LocalDateTime date, List<String> ingredients, List<String> directions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = date;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeResponse that = (RecipeResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && Objects.equals(date, that.date) && Objects.equals(ingredients, that.ingredients) && Objects.equals(directions, that.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, category, date, ingredients, directions);
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                '}';
    }
}
