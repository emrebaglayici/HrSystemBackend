package com.emrebaglayici.myhremrebaglayici.Entities.Concretes;

import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id",referencedColumnName = "id")
@Table(name = "hr",schema = "public")
public class Hr extends User {

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "role_id")
    private Role role;

}
