package com.emrebaglayici.myhremrebaglayici.Entities.Concretes;

import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name = "id",referencedColumnName = "id")
//@Table(name = "hr",schema = "public")
public class Hr extends User{

    //Hr takes id's from User
    @NotNull
    private String name;

//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "role_id")
//    private Role role;

}
