package com.tungngbk.recipes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    private static final String emailRegex = "(?i)[\\w!#$%&'*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,6}";
    @Id
    @NotBlank
    @Email(regexp = emailRegex)
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
