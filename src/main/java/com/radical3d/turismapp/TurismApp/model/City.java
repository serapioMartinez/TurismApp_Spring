package com.radical3d.turismapp.TurismApp.model;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.radical3d.turismapp.TurismApp.model.security.UserAdmin;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;
import com.radical3d.turismapp.TurismApp.utils.LoggerHelper;
import com.radical3d.turismapp.TurismApp.utils.Validations;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(2)")
    private String region;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String municipality;

    @Column(columnDefinition = "VARCHAR(50)")
    private String mail;

    @Column(columnDefinition = "VARCHAR(10)")
    private String phoneNumber;

    @Column()
    private String mapsURL;

    @Column(nullable = false, columnDefinition = "BOOL DEFAULT FALSE")
    @Builder.Default()
    private boolean magicTown = false;

    @Column(columnDefinition = "VARCHAR(10)")
    private String emergencies;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private int rating;

    @Column(columnDefinition = "int default 0")
    private int raters;

    @Column(nullable = false)
    private String description;

    @Column()
    private String photo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "admin_id", nullable = false, unique = true)
    private UserAdmin userAdmin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(
        name = "city_tourism_types",
        joinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
    private Set<TourismType> tourismTypes;

    public static final Map<String, String> REGIONES = AppUtils.retrievePropertiesFromFile("regions");    
   

    public static boolean isCiudadValidForPersistance(City ciudad){
        if ( ciudad.getUserAdmin().getId() == 0
            || !Validations.validateText(ciudad.name)
            || !REGIONES.containsKey(ciudad.region)
            || !ciudad.validateTiposTurismo()
            || !Validations.validateText(ciudad.municipality)
            || !Validations.validateText(ciudad.description)) {
            LoggerHelper.error("Validations not passed by the city");
            return false;
        }
        return true;
    }

    public boolean validateTiposTurismo(){
        Map<String, String> tiposT = AppUtils.retrievePropertiesFromFile("tourismType");

        for(TourismType tt:this.tourismTypes){
            if (!tiposT.containsKey(tt.getId())) return false;
        }

        return true;
    }
}
