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
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Builder
public class Candidates extends User {
    @NotNull
    private String name;
}
