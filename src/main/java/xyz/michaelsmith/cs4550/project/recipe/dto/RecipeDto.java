package xyz.michaelsmith.cs4550.project.recipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private UserDto author;
    private String duration;
    private String yield;
    private Date created;

    private List<RecipeIngredientDto> ingredients;
    private List<RecipeStepDto> steps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public List<RecipeIngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeStepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStepDto> steps) {
        this.steps = steps;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
