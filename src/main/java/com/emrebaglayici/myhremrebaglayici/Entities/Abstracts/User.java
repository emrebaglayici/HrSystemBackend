package com.emrebaglayici.myhremrebaglayici.Entities.Abstracts;


import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Role;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users",schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
}
