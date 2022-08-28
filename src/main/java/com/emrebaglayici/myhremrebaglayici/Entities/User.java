package com.emrebaglayici.myhremrebaglayici.Entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Table(name = "users",schema = "public")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String role;

}
