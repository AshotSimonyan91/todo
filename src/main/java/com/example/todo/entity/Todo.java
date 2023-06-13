package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ashot Simonyan on 13.06.23.
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
}
