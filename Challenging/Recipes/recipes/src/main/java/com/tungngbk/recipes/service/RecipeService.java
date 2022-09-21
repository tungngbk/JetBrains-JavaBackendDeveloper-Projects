package com.tungngbk.recipes.service;

import com.tungngbk.recipes.entity.Recipe;
import com.tungngbk.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe getRecipe(int id){
        return recipeRepository.findById(id).get();
    }

    public Recipe addRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public boolean isRecipeExist(int id){
        return recipeRepository.existsById(id);
    }

    public void deleteRecipe(int id){
        recipeRepository.deleteById(id);
    }

    public void updateRecipe(int id, Recipe newRecipe){
        Recipe recipe = recipeRepository.findById(id).get();
        recipe.setDate(newRecipe.getDate());
        recipe.setCategory(newRecipe.getCategory());
        recipe.setDescription(newRecipe.getDescription());
        recipe.setDirections(newRecipe.getDirections());
        recipe.setIngredients(newRecipe.getIngredients());
        recipe.setName(newRecipe.getName());
        recipeRepository.save(recipe);
    }

    public List<Recipe> searchByCategory(String category){
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchByName(String name){
        return recipeRepository.findAllByNameIgnoreCaseContainsOrderByDateDesc(name);
    }

    public List<Recipe> searchByCategoryAndName(String category, String name){
        return recipeRepository.findAllByCategoryIgnoreCaseAndNameIgnoreCaseContainsOrderByDateDesc(category, name);
    }
}
