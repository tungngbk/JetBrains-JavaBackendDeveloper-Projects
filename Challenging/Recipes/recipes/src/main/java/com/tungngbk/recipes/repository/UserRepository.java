package com.tungngbk.recipes.repository;

import com.tungngbk.recipes.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String> {
}
