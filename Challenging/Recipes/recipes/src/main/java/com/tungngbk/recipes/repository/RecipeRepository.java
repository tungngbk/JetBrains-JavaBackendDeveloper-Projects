package com.tungngbk.recipes.repository;
import com.tungngbk.recipes.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer>{
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameIgnoreCaseContainsOrderByDateDesc(String name);
    List<Recipe> findAllByCategoryIgnoreCaseAndNameIgnoreCaseContainsOrderByDateDesc(String category, String name);
}
