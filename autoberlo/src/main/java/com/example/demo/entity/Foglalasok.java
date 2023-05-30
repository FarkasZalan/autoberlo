package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "foglalasok")
public class Foglalasok {

    public Foglalasok() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nyugta_id", nullable = false)
    private Integer nyugta_id;

    @Column(name = "foglalo_email", nullable = false)
    private String foglalo_email;

    @Column(name = "foglalo_cim", nullable = false)
    private String foglalo_cim;

    @Column(name = "foglalo_telefonszam", nullable = false)
    private String foglalo_telefonszam;

    @Column(name = "fizetendo_osszeg", nullable = false)
    private Integer fizetendo_osszeg;

    @Column(name = "foglalando_auto_rendszam", nullable = false)
    private String foglalando_auto_rendszam;

    public Integer getNyugta_id() {
        return nyugta_id;
    }

    public String getFoglalo_email() {
        return foglalo_email;
    }

    public String getFoglalo_cim() {
        return foglalo_cim;
    }

    public String getFoglalo_telefonszam() {
        return foglalo_telefonszam;
    }

    public Integer getFizetendo_osszeg() {
        return fizetendo_osszeg;
    }

    public String getFoglalando_auto_rendszam() {
        return foglalando_auto_rendszam;
    }

    public LocalDate getKezdo_datum() {
        return kezdo_datum;
    }

    public LocalDate getVegzo_datum() {
        return vegzo_datum;
    }

    @Column(name = "kezdo_datum", nullable = false)
    private LocalDate kezdo_datum;

    public String getFoglalo_neve() {
        return foglalo_neve;
    }

    @Column(name = "foglalo_neve", nullable = false)
    private String foglalo_neve;

    public String getFoglalas_allapota() {
        return foglalas_allapota;
    }

    @Column(name = "foglalas_allapota", nullable = false)
    private String foglalas_allapota;

    public Foglalasok(String foglalo_email, String foglalo_neve, String foglalo_cim, String foglalo_telefonszam, Integer fizetendo_osszeg, String foglalando_auto_rendszam, LocalDate kezdo_datum, LocalDate vegzo_datum, Integer auto_id, String foglalas_allapota) {
        this.foglalo_email = foglalo_email;
        this.foglalo_neve = foglalo_neve;
        this.foglalo_cim = foglalo_cim;
        this.foglalo_telefonszam = foglalo_telefonszam;
        this.fizetendo_osszeg = fizetendo_osszeg;
        this.foglalando_auto_rendszam = foglalando_auto_rendszam;
        this.kezdo_datum = kezdo_datum;
        this.vegzo_datum = vegzo_datum;
        this.auto_id = auto_id;
        this.foglalas_allapota = foglalas_allapota;
    }

    @Column(name = "vegzo_datum", nullable = false)
    private LocalDate vegzo_datum;

    public Integer getAuto_id() {
        return auto_id;
    }

    @Column(name = "auto_id", nullable = false)
    private Integer auto_id;
}
