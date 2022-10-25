package com.ultimate.infits;

import android.graphics.Bitmap;

public class List_Food {
    String name,time,serving,link,nutritions,ingredient,category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNutritions() {
        return nutritions;
    }

    public void setNutritions(String nutritions) {
        this.nutritions = nutritions;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    Bitmap image;

    public byte[] getImage64() {
        return image64;
    }

    String instruction;

    public void setImage64(byte[] image64) {
        this.image64 = image64;
    }

    byte[] image64;

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    private String calories;
    private String proteins;
    private String fats;
    private String carbs;

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFibres() {
        return fibres;
    }

    public void setFibres(String fibres) {
        this.fibres = fibres;
    }

    private String fibres;

    public List_Food(String name, String time, Bitmap image, String serving) {
        this.name = name;
        this.time = time;
        this.image = image;
        this.serving = serving;
    }


    public List_Food(String name, String time, Bitmap image, String serving, String link, String calories,String proteins,String fats,String carbs,String fibres, String ingredient, String category,byte[] image64,String instruction) {
        this.name = name;
        this.time = time;
        this.image = image;
        this.serving = serving;
        this.link = link;
        this.calories = calories;
        this.fibres = fibres;
        this.fats = fats;
        this.carbs = carbs;
        this.proteins = proteins;
//        this.nutritions = nutritions;
        this.ingredient = ingredient;
        this.category = category;
        this.image64 = image64;
        this.instruction = instruction;
    }
}
