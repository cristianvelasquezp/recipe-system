package org.cristianvelasquezp.recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @NotBlank
    @NotNull
    @NotEmpty
    private String description;
    @NotBlank
    @NotNull
    @NotEmpty
    private String category;
    @UpdateTimestamp
    private LocalDateTime date;
    @NotNull
    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
    @NotNull
    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    private List<Direction> directions;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Recipe() {
    }

    public Recipe(String name, String description, String category, List<Ingredient> ingredients, List<Direction> directions, User user) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.directions = directions;
        this.user = user;
    }

    public Recipe(Long id, String name, String description, String category, LocalDateTime date, List<Ingredient> ingredients, List<Direction> directions, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = date;
        this.ingredients = ingredients;
        this.directions = directions;
        this.user = user;
    }

    private Recipe(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setDescription(builder.description);
        setCategory(builder.category);
        setDate(builder.date);
        setIngredients(builder.ingredients);
        setDirections(builder.directions);
        setUser(builder.user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @NotBlank @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotNull @NotBlank @NotEmpty String name) {
        this.name = name;
    }

    public @NotBlank @NotNull @NotEmpty String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank @NotNull @NotEmpty String description) {
        this.description = description;
    }

    public @NotBlank @NotNull @NotEmpty String getCategory() {
        return category;
    }

    public void setCategory(@NotBlank @NotNull @NotEmpty String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public @NotNull @NotEmpty List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(@NotNull @NotEmpty List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public @NotNull @NotEmpty List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(@NotNull @NotEmpty List<Direction> directions) {
        this.directions = directions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                ", user=" + user +
                '}';
    }


    public static final class Builder {
        private Long id;
        private @NotNull
        @NotBlank
        @NotEmpty String name;
        private @NotBlank
        @NotNull
        @NotEmpty String description;
        private @NotBlank
        @NotNull
        @NotEmpty String category;
        private LocalDateTime date;
        private @NotNull
        @NotEmpty List<Ingredient> ingredients;
        private @NotNull
        @NotEmpty List<Direction> directions;
        private User user;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(@NotNull @NotBlank @NotEmpty String val) {
            name = val;
            return this;
        }

        public Builder description(@NotBlank @NotNull @NotEmpty String val) {
            description = val;
            return this;
        }

        public Builder category(@NotBlank @NotNull @NotEmpty String val) {
            category = val;
            return this;
        }

        public Builder date(LocalDateTime val) {
            date = val;
            return this;
        }

        public Builder ingredients(@NotNull @NotEmpty List<Ingredient> val) {
            ingredients = val;
            return this;
        }

        public Builder directions(@NotNull @NotEmpty List<Direction> val) {
            directions = val;
            return this;
        }

        public Builder user(User val) {
            user = val;
            return this;
        }

        public Recipe build() {
            return new Recipe(this);
        }
    }
}
