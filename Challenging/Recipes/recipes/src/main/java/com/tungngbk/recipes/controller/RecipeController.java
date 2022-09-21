package com.tungngbk.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.tungngbk.recipes.entity.Recipe;
import com.tungngbk.recipes.service.RecipeService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> retrieveRecipe(@PathVariable int id){
        boolean isRecipeExist = recipeService.isRecipeExist(id);
        if(!isRecipeExist) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Recipe recipe = recipeService.getRecipe(id);
        return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addRecipe(@RequestBody @Valid Recipe newRecipe){
        LocalDateTime now = LocalDateTime.now();
        newRecipe.setDate(now);
        newRecipe.setEmail(getLoggedInUser());
        Recipe recipe = recipeService.addRecipe(newRecipe);
        return new ResponseEntity<>("{\n\"id\":" + recipe.getId()+ "\n}", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable int id){
        boolean isRecipeExist = recipeService.isRecipeExist(id);
        if(!isRecipeExist) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Recipe recipe = recipeService.getRecipe(id);
        if(!recipe.getEmail().equals(getLoggedInUser())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody @Valid Recipe newRecipe, @PathVariable int id){
        boolean isRecipeExist = recipeService.isRecipeExist(id);
        if(!isRecipeExist) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Recipe recipe = recipeService.getRecipe(id);
        if(!recipe.getEmail().equals(getLoggedInUser())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        LocalDateTime now = LocalDateTime.now();
        newRecipe.setDate(now);
        recipeService.updateRecipe(id, newRecipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> search(@RequestParam(required = false) String category,
                                         @RequestParam(required = false) String name){
        if(category == null && name == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Recipe> searchList;
        if(category == null){
            searchList = recipeService.searchByName(name);
        } else if (name == null){
            searchList = recipeService.searchByCategory(category);
        } else {
            searchList = recipeService.searchByCategoryAndName(category, name);
        }
        return new ResponseEntity<>(searchList, HttpStatus.OK);
    }

    public String getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
