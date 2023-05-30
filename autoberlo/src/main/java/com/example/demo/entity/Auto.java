package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auto")
public class Auto {

    public Auto() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_id", nullable = false)
    private Integer auto_id;

    public String getAuto_rendszam() {
        return auto_rendszam;
    }

    public void setAuto_rendszam(String auto_rendszam) {
        this.auto_rendszam = auto_rendszam;
    }

    public void setAuto_nev(String auto_nev) {
        this.auto_nev = auto_nev;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public void setMotor_kobcenti(Integer motor_kobcenti) {
        this.motor_kobcenti = motor_kobcenti;
    }

    public void setInnaktiv(String innaktiv) {
        this.innaktiv = innaktiv;
    }

    public void setEvjarat(Integer evjarat) {
        this.evjarat = evjarat;
    }

    public void setUlesek_szama(Integer ulesek_szama) {
        this.ulesek_szama = ulesek_szama;
    }

    public void setBerles_dija(Integer berles_dija) {
        this.berles_dija = berles_dija;
    }

    public void setKep(String kep) {
        this.kep = kep;
    }

    @Column(name = "auto_rendszam", nullable = false, length = 128)
    private String auto_rendszam;

    @Column(name = "auto_nev", nullable = false, length = 128)
    private String auto_nev;

    @Column(name = "marka", nullable = false, length = 128)
    private String marka;

    @Column(name = "tipus", nullable = false, length = 128)
    private String tipus;

    @Column(name = "motor_kobcenti", nullable = false, length = 128)
    private Integer motor_kobcenti;

    public String getInnaktiv() {
        return innaktiv;
    }

    @Column(name = "innaktiv", nullable = false, length = 128)
    private String innaktiv;

    public Auto(String auto_rendszam, String auto_nev, String marka, String tipus, Integer motor_kobcenti, Integer evjarat, Integer ulesek_szama, Integer berles_dija, String kep, String innaktiv) {
        this.auto_rendszam = auto_rendszam;
        this.auto_nev = auto_nev;
        this.marka = marka;
        this.tipus = tipus;
        this.motor_kobcenti = motor_kobcenti;
        this.evjarat = evjarat;
        this.ulesek_szama = ulesek_szama;
        this.berles_dija = berles_dija;
        this.kep = kep;
        this.innaktiv = innaktiv;
    }

    @Column(name = "evjarat", nullable = false, length = 128)
    private Integer evjarat;

    public Integer getAuto_id() {
        return auto_id;
    }

    public String getAuto_nev() {
        return auto_nev;
    }

    public String getMarka() {
        return marka;
    }

    public String getTipus() {
        return tipus;
    }

    public Integer getMotor_kobcenti() {
        return motor_kobcenti;
    }

    public Integer getEvjarat() {
        return evjarat;
    }

    public Integer getUlesek_szama() {
        return ulesek_szama;
    }

    public String getKep() {
        return kep;
    }

    @Column(name = "ulesek_szama", nullable = false, length = 128)
    private Integer ulesek_szama;

    public Integer getBerles_dija() {
        return berles_dija;
    }

    @Column(name = "berles_dija", nullable = false, length = 128)
    private Integer berles_dija;

    @Lob
    @Column(name = "kep", nullable = false, columnDefinition = "MEDIUMBLOB")
    private String kep;
}
