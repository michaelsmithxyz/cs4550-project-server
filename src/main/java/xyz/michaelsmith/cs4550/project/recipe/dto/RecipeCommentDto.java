package xyz.michaelsmith.cs4550.project.recipe.dto;

import xyz.michaelsmith.cs4550.project.user.dto.UserDto;

import java.util.Date;

public class RecipeCommentDto {
    private UserDto author;
    private Date posted;
    private String text;

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
