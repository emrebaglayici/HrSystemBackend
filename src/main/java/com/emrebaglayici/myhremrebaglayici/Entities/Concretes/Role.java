package com.emrebaglayici.myhremrebaglayici.Entities.Concretes;


import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;


}
