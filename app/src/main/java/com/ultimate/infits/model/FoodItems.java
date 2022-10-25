package com.ultimate.infits.model;

import android.graphics.Bitmap;

public class FoodItems {

    public FoodItems(){
    }

    String name;
    String rating;
    String calories;
    Bitmap image;
    String steps;
    String fats;
    String proteins;
    String carbs;
    String course;
    String category;
    String preparationTime;
    String cookingTime;
    String servings;
    String fibres;
    String[] ingredientname;
    String[] ingredientquantity;
    String[] directions;
    byte[] imageByte;

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public FoodItems(String name, String rating, String calories, Bitmap image, String steps,
                     String fats, String proteins, String carbs, String course, String category,
                     String preparationTime, String cookingTime, String servings, String fibres,
                     String[] ingredientname, String[] ingredientquantity, String[] directions,byte[] imageByte) {
        this.name = name;
        this.rating = rating;
        this.calories = calories;
        this.image = image;
        this.steps = steps;
        this.fats = fats;
        this.proteins = proteins;
        this.carbs = carbs;
        this.course = course;
        this.category = category;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.fibres = fibres;
        this.ingredientname = ingredientname;
        this.ingredientquantity = ingredientquantity;
        this.directions = directions;
        this.imageByte=imageByte;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getFibres() {
        return fibres;
    }

    public void setFibres(String fibres) {
        this.fibres = fibres;
    }

    public String[] getIngredientname() {
        return ingredientname;
    }

    public void setIngredientname(String[] ingredientname) {
        this.ingredientname = ingredientname;
    }

    public String[] getIngredientquantity() {
        return ingredientquantity;
    }

    public void setIngredientquantity(String[] ingredientquantity) {
        this.ingredientquantity = ingredientquantity;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }


    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }


}
