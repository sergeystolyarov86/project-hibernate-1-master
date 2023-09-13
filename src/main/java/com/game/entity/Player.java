package com.game.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQuery;

import java.util.Date;
@NamedQuery(name = "getCount",
        query = "select count(p) from Player p")

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player", schema = "rpg")
public class Player {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 12, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String title;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Race race;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Profession profession;
    @Column(nullable = false)
    private Date birthday;
    @Column(nullable = false)
    private Boolean banned;
    @Column(nullable = false)
    private Integer level;
}



