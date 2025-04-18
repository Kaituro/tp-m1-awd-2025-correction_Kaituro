package doremi.controllers;

import doremi.domain.Band;
import doremi.services.BandAlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BandController {

    @Autowired
    private BandAlbumService bandAlbumService;

    @GetMapping("/bands")
    public String list(Model model) {
        model.addAttribute("bands", bandAlbumService.findAllBand());
        return "bands";
    }

    @GetMapping("band/{id}")
    public String showBand(@PathVariable Long id, Model model){
        Band band = bandAlbumService.findBandById(id);
        if (band == null) {
            model.addAttribute("customMessage", "Impossible. Id non valide");
            return "error";
        }
        model.addAttribute("band", band);
        return "bandShow";
    }

    @GetMapping("/band/new")
    public String createBand(Model model){
        model.addAttribute("band", new Band());
        return "bandForm";
    }

    @PostMapping(value = "/band")
    public String createOrUpdateBand(@Valid Band band,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "bandForm";
        }
        band = bandAlbumService.save(band);
        return "redirect:/band/" + band.getId();
    }

    @GetMapping("band/edit/{id}")
    public String editBand(@PathVariable Long id, Model model){
        Band band = bandAlbumService.findBandById(id);
        if (band == null) {
            model.addAttribute("customMessage", "Impossible. Id non valide");
            return "error";
        }
        model.addAttribute("band", band);
        return "bandForm";
    }

    @GetMapping("band/delete/{id}")
    public String deleteBand(@PathVariable Long id, Model model){
        Band band = bandAlbumService.findBandById(id);
        if (band == null) {
            model.addAttribute("customMessage", "Impossible. Id non valide");
            return "error";
        }
        if (band.getAlbums().size() != 0) {
            model.addAttribute("customMessage", "Impossible. Le groupe est associé à un album.");
            return "error";
        }
        bandAlbumService.deleteBandById(id);
        return "redirect:/bands";
    }

    @GetMapping("bands/search")
    public String searchBands(@RequestParam(value = "active",required = true) String active, Model model) {
        List<Band> band;
        if (active.equals("Y"))
            band = bandAlbumService.findAllActiveBands(true);
        else if (active.equals("N"))
            band = bandAlbumService.findAllActiveBands(false);
        else
            band = bandAlbumService.findAllBand();
        model.addAttribute("bands", band);
        return "bands";
    }

}
