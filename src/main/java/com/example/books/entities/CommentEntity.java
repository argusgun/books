package com.example.books.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "comment")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String text;
    private float rating;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="book_id",foreignKey = @ForeignKey(name ="fk_book"))
    private BookEntity book;
}
