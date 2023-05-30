package com.example.demo.Controller;

import com.example.demo.entity.Auto;
import com.example.demo.entity.Foglalasok;
import com.example.demo.repository.AutoRepo;
import com.example.demo.repository.FoglalasokRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
public class AdminFunkciok {
    public AdminFunkciok(AutoRepo autoRepo, FoglalasokRepo foglalasokRepo) {
        this.autoRepo = autoRepo;
        this.foglalasokRepo = foglalasokRepo;
    }

    @Autowired
    private final AutoRepo autoRepo;
    @Autowired
    private final FoglalasokRepo foglalasokRepo;

    List<Auto> autokLista;
    List<Foglalasok> foglalasokLista;
    String regiKep = "";

    @RequestMapping(value = {"/admin"})
    public String adminOldal(Model model) {
        return "adminOldal";
    }

    @RequestMapping(value = {"/foglalasok"})
    public String foglalasok(Model model) {
        foglalasokLista = foglalasokRepo.foglalasokListaja();
        model.addAttribute("foglalasok", foglalasokLista);
        return "foglalasok";
    }

    @RequestMapping(value = {"/autok"})
    public String autok(Model model) {
        autokLista = autoRepo.autokListajaAdmin();
        model.addAttribute("autok", autokLista);
        return "autokListaja";
    }

    @RequestMapping(value = {"/auto_innaktivalasa"})
    public String auto_innaktivalasa(Model model, RedirectAttributes redirectAttributes, @RequestParam("innaktivalandoAutoId") String innaktivalandoAutoId) {
        Auto aktualisAuto;
        List<Foglalasok> lemondandoFoglalasok;
        Integer id = Integer.valueOf(innaktivalandoAutoId);
        aktualisAuto = autoRepo.findById(id).get();
        if (aktualisAuto.getInnaktiv().equals("elerheto")) {
            aktualisAuto.setInnaktiv("innaktív");
        } else {
            aktualisAuto.setInnaktiv("elerheto");
        }

        autoRepo.autoElerhetosege(aktualisAuto.getInnaktiv(), id);
        if (aktualisAuto.getInnaktiv().equals("innaktív")) {
            lemondandoFoglalasok = foglalasokRepo.aktivfoglalasokListaja(id);
            for (Foglalasok foglalasok : lemondandoFoglalasok) {
                if (foglalasok.getKezdo_datum().equals(LocalDate.now()) || foglalasok.getKezdo_datum().isAfter(LocalDate.now())) {
                    foglalasokRepo.foglalasLemondasa(foglalasok.getNyugta_id(), "lemondva");
                }
            }
            redirectAttributes.addFlashAttribute("Uzenet", "Sikeresen innaktiváltad a(z) " + aktualisAuto.getAuto_rendszam() + " rendszámú autódat és lemondtad a rá vonatkozó foglalásokat!");
        } else {
            redirectAttributes.addFlashAttribute("Uzenet", "Sikeresen elérhetővé tetted a(z) " + aktualisAuto.getAuto_rendszam() + " rendszámú autódat!");
        }
        return "redirect:/autok";
    }

    @RequestMapping(value = "/szerkeszt/{auto_id}")
    public String autoSzerkesztes(
            @PathVariable("auto_id") int id,
            Model model
    ) {
        Auto jelenlegiAuto = autoRepo.findById(id).get();
        model.addAttribute("auto", jelenlegiAuto);

        return "autoSzerkeztese";
    }

    @RequestMapping(value = "/auto_szerkesztese")
    public String auto_szerkesztese(
            @RequestParam("auto_id") String auto_id, @RequestParam("rendszam") String rendszam, @RequestParam("auto_marka") String auto_marka, @RequestParam("auto_nev") String auto_nev,
            @RequestParam("tipus") String tipus, @RequestParam("motor_kobcenti") String motor_kobcenti, @RequestParam("evjarat") String evjaratString, @RequestParam("ulesek_szama") String ulesek_szama,
            @RequestParam("berles_dija") String berles_dija, @RequestParam(name = "kep", required = false) MultipartFile kep,
            RedirectAttributes redirectAttributes
    ) {
        String modositandoKep = "";
        Integer id = Integer.valueOf(auto_id);
        Auto frissitendoAuto = autoRepo.findById(id).get();
        Integer berlesDija = Integer.valueOf(berles_dija);
        Integer evjarat = Integer.valueOf(evjaratString);
        Integer kobcenti = Integer.valueOf(motor_kobcenti);
        Integer ulesekSzama = Integer.valueOf(ulesek_szama);

        if (!frissitendoAuto.getKep().equals("")) {
            regiKep = frissitendoAuto.getKep();
        }

        if (kep != null) {
            String kepNeve = StringUtils.cleanPath(Objects.requireNonNull(kep.getOriginalFilename()));
            if (kepNeve.contains("..")) {
                redirectAttributes.addFlashAttribute("Uzenet", "Érvénytelen képformátum!");
                return "redirect:/szerkeszt/" + auto_id;
            }
        }
        try {
            assert kep != null;
            float kepMerete = kep.getSize();
            if (kepMerete <= 800000) {
                modositandoKep = Base64.getEncoder().encodeToString(kep.getBytes());
                if (modositandoKep.length() >= 100000) {
                    redirectAttributes.addFlashAttribute("Uzenet", "Túl nagy felbontású képet próbálsz meg feltölteni, maximum felbontás: 1400 x 670!");
                    return "redirect:/szerkeszt/" + auto_id;
                } else {
                    if (modositandoKep.equals("") && regiKep.equals("")) {
                        autoRepo.autoKepe(modositandoKep, id);
                    }
                    if (!modositandoKep.equals("")) {
                        autoRepo.autoKepe(modositandoKep, id);
                    }
                    if (!regiKep.equals("") && regiKep.equals(modositandoKep)) {
                        autoRepo.autoKepe(regiKep, id);
                    }
                }
            } else {
                redirectAttributes.addFlashAttribute("Uzenet", "Túl nagy méretű képet próbálsz meg feltölteni, a max méret 0,8Mb! Az általad feltöltött kép mérete: " + kepMerete / 1000000 + "Mb");
                return "redirect:/szerkeszt/" + auto_id;
            }


            autoRepo.alapadatFrissites(id, auto_nev, rendszam, berlesDija, evjarat, frissitendoAuto.getInnaktiv(), auto_marka, kobcenti, tipus, ulesekSzama);
            redirectAttributes.addFlashAttribute("Uzenet", "Sikeresen frissítetted a(z) " + rendszam + " rendszámú autódat!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("Uzenet", "Váratlan hiba történt, kérjük próbáld újra!");
            return "redirect:/szerkeszt/" + auto_id;
        }
        return "redirect:/szerkeszt/" + auto_id;
    }

    @RequestMapping(value = "/ujAuto")
    public String ujAuto(Model model) {
        return "ujAuto";
    }


    @RequestMapping(value = "/auto_hozzaadasa")
    public String auto_hozzaadasa(
            @RequestParam("rendszam") String rendszam, @RequestParam("auto_marka") String auto_marka, @RequestParam("auto_nev") String auto_nev,
            @RequestParam("tipus") String tipus, @RequestParam("motor_kobcenti") String motor_kobcenti, @RequestParam("evjarat") String evjaratString, @RequestParam("ulesek_szama") String ulesek_szama,
            @RequestParam("berles_dija") String berles_dija, @RequestParam(name = "kep", required = false) MultipartFile kep,
            RedirectAttributes redirectAttributes
    ) {
        String feltoltendoKep = "";

        Integer berlesDija = Integer.valueOf(berles_dija);
        Integer evjarat = Integer.valueOf(evjaratString);
        Integer kobcenti = Integer.valueOf(motor_kobcenti);
        Integer ulesekSzama = Integer.valueOf(ulesek_szama);
        Auto ujAuto;

        if (kep != null) {
            String kepNeve = StringUtils.cleanPath(Objects.requireNonNull(kep.getOriginalFilename()));
            if (kepNeve.contains("..")) {
                redirectAttributes.addFlashAttribute("Uzenet", "Érvénytelen képformátum!");
                return "redirect:/ujAuto";
            }
        }
        try {
            assert kep != null;
            float kepMerete = kep.getSize();
            if (kepMerete <= 800000) {
                feltoltendoKep = Base64.getEncoder().encodeToString(kep.getBytes());
                if (feltoltendoKep.length() >= 100000) {
                    redirectAttributes.addFlashAttribute("Uzenet", "Túl nagy felbontású képet próbálsz meg feltölteni!");
                    return "redirect:/ujAuto";
                } else {
                    ujAuto = new Auto(rendszam, auto_nev, auto_marka, tipus, kobcenti, evjarat, ulesekSzama, berlesDija, "", "elerheto");
                    autoRepo.save(ujAuto);
                    autoRepo.autoKepe(feltoltendoKep, ujAuto.getAuto_id());
                    redirectAttributes.addFlashAttribute("Uzenet", "Sikeresen létrehoztad a(z) " + rendszam + " rendszámú autódat!");
                    return "redirect:/autok";
                }
            } else {
                redirectAttributes.addFlashAttribute("Uzenet", "Túl nagy méretű képet próbálsz meg feltölteni, a max méret 0,8Mb! Az általad feltöltött kép mérete: " + kepMerete / 1000000 + "Mb");
                return "redirect:/ujAuto";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("Uzenet", "Váratlan hiba történt, kérjük próbáld újra!");
            return "redirect:/ujAuto";
        }
    }

}
