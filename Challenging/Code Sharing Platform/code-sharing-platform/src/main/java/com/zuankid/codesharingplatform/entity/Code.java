package com.zuankid.codesharingplatform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "code")
public class Code {
    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "code")
    private String code;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "time")
    private long time;
    @Column(name = "views")
    private long views;
    @JsonIgnore
    @Column(name = "no_time_restriction")
    private boolean noTimeRestriction;
    @JsonIgnore
    @Column(name = "no_view_restriction")
    private boolean noViewsRestriction;
}
