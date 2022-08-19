package com.emrebaglayici.myhremrebaglayici.Entities.Concretes;

import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id",referencedColumnName = "id")
@Table(name = "candidates",schema = "public")
public class Candidates extends User {


    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "role_id")
    private Role role;
}
