package com.ykotsiuba.bookapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_seq_gen")
    @SequenceGenerator(name = "members_seq_gen", sequenceName = "member_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private LocalDate membershipDate;

    @PrePersist
    protected void onCreate() {
        this.membershipDate = LocalDate.now();
    }

    @ManyToMany
    @JoinTable(
            name = "book_member",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();
}
