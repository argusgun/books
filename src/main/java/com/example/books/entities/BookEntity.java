package com.example.books.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
@Data
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String author;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CommentEntity> comments;

}
