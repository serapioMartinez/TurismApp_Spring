package com.radical3d.turismapp.TurismApp.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.radical3d.turismapp.TurismApp.model.City;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "user_admin")
public class UserAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String mail;
    private String position;
    private String photo;
    private char userType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userAdmin", orphanRemoval = true, optional = true)
    @JsonIgnore
    private City city;
}
