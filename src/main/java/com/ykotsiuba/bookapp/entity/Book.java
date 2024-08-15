package com.ykotsiuba.bookapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq_gen")
    @SequenceGenerator(name = "books_seq_gen", sequenceName = "book_id_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String author;

    private int amount;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private List<Member> members = new ArrayList<>();

    public void increaseAmount() {
        amount++;
    }

    public void decreaseAmount() {
        amount--;
    }
}
