package org.cristianvelasquezp.recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @Column(name = "\"value\"")
    private String value;
    @JsonIgnore
    @ManyToOne
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
