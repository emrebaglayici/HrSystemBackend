package com.emrebaglayici.myhremrebaglayici.Entities.Concretes;

import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id",referencedColumnName = "id")
public class Candidates extends User {

    @Column(name = "role_id")
    private int role_id;
}
