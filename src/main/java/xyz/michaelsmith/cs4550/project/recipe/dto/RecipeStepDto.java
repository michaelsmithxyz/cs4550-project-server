package xyz.michaelsmith.cs4550.project.recipe.dto;

public class RecipeStepDto {
    private String text;
    private Long sortOrder;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}
