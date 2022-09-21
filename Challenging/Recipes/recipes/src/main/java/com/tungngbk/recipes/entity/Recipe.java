package com.tungngbk.recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @NotEmpty
    @Column(name = "ingredients")
    @ElementCollection(targetClass=String.class)
    private List<String> ingredients;

    @NotEmpty
    @Column(name = "directions")
    @ElementCollection(targetClass=String.class)
    private List<String> directions;

    @Column(name = "category")
    @NotBlank
    private String category;

    @Column(name = "date")
    @LastModifiedDate
    private LocalDateTime date;

    @JsonIgnore
    private String email;
}
