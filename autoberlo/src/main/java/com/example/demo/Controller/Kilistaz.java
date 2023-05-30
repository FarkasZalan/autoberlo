package com.example.demo.Controller;

import com.example.demo.entity.Auto;
import com.example.demo.entity.Foglalasok;
import com.example.demo.repository.AutoRepo;
import com.example.demo.repository.FoglalasokRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class Kilistaz {

    @Autowired
    private final AutoRepo autoRepo;
    @Autowired
    private final FoglalasokRepo foglalasokRepo;

    List<Auto> autokLista;

    public Kilistaz(AutoRepo autoRepo, FoglalasokRepo foglalasokRepo) {
        this.autoRepo = autoRepo;
        this.foglalasokRepo = foglalasokRepo;
    }

    @RequestMapping(value = {"/", "/fooldal"})
    public String fooldal(Model model) {
        autokLista = autoRepo.autokListaja();
        model.addAttribute("autok", autokLista);
        return "fooldal";
    }

    @PostMapping(value = "/szures")
    public String szures(
            @RequestParam(value = "szuresKezdoDatum", required = false) String szuresKezdoDatum,
            @RequestParam(value = "szuresVegzoDatum", required = false) String szuresVegzoDatum, Model model
    ) {
        if (!((szuresKezdoDatum == null || szuresKezdoDatum.isEmpty()) && (szuresVegzoDatum == null || szuresVegzoDatum.isEmpty()))) {
            assert szuresKezdoDatum != null;
            LocalDate kezdo = LocalDate.parse(szuresKezdoDatum);
            LocalDate vegzo = LocalDate.parse(szuresVegzoDatum);
            List<Foglalasok> foglaltak = foglalasokRepo.szabadAutok(kezdo, vegzo);
            List<Auto> szabadok = autoRepo.autokListaja();

            for (Foglalasok foglalasok : foglaltak) {
                Auto torlendo = autoRepo.findById(foglalasok.getAuto_id()).get();
                if (foglalasok.getFoglalas_allapota().equals("aktív")) {
                    szabadok.remove(torlendo);
                }
            }
            model.addAttribute("autok", szabadok);

        } else {
            model.addAttribute("autok", autokLista);
        }

        return "/fooldal";
    }
}
