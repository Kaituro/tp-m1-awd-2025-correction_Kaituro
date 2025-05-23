package doremi;

import doremi.domain.Album;
import doremi.domain.Article;
import doremi.domain.Band;
import doremi.domain.Genre;
import doremi.services.ArticleService;
import doremi.services.BandAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    private BandAlbumService bandAlbumService;
    private ArticleService articleService;

    private Band theStrokes, joyDivision, lanaDelRey, pixies, publicEnemy;
    private Album album1, album2, album3, album4, album5, album6,
            album7, album8, album9, album10, album11, album12;
    private Article a1, a2, a3, a4, a5, a6, a7, a8, a9;

    @Autowired
    public DataLoader(BandAlbumService bandAlbumService, ArticleService articleService) {
        this.bandAlbumService = bandAlbumService;
        this.articleService = articleService;
    }

    @PostConstruct
    public void init() {
        try {
            initArticles();
            initBands();
            initAndsaves();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initArticles() {
        a1 = new Article("Retour sur le concert de Big Thief à la Cigale", "Concert");
        a2 = new Article("Tournée de Mitski au printemps", "Nouvelle");
        a3 = new Article("Nouvel album de Black Country, New Road", "Sortie");
        a4 = new Article("Les 10 meilleures chansons de Burt Bacharach", "Selection");
        a5 = new Article("Benjamin Biolay était au Bikini hier", "Concert");
        a6 = new Article("Les 10 meilleures morceaux de Philip Glass", "Selection");
        a7 = new Article("Séparation du groupe The Long Blondes", "Nouvelle");
        a8 = new Article("On a écouté le nouvel album de The Weeknd", "Evaluation");
        a9 = new Article("Neon Golden est sorti il y a 20 ans", "Anniversaire");
        a1 = articleService.saveArticle(a1);
        a2 = articleService.saveArticle(a2);
        a3 = articleService.saveArticle(a3);
        a4 = articleService.saveArticle(a4);
        a5 = articleService.saveArticle(a5);
        a6 = articleService.saveArticle(a6);
        a7 = articleService.saveArticle(a7);
        a8 = articleService.saveArticle(a8);
        a9 = articleService.saveArticle(a9);
    }

    private void initBands() {
        theStrokes = new Band("The Strokes", true);
        joyDivision = new Band("Joy Division", false);
        lanaDelRey = new Band("Lana Del Rey", true);
        pixies = new Band("Pixies", true);
        publicEnemy = new Band("Public Enemy", true);
    }

    private void initAndsaves() {
        album1 = new Album("Is This It", Genre.INDIE, 2001);
        album1.setBand(theStrokes);
        album1 = bandAlbumService.save(album1);
        theStrokes = album1.getBand();

        album2 = new Album("Room on Fire", Genre.INDIE, 2003);
        album2.setBand(theStrokes);
        album2 = bandAlbumService.save(album2);
        theStrokes = album2.getBand();

        album3 = new Album("Unknown Pleasures", Genre.ROCK, 1979);
        album3.setBand(joyDivision);
        album3 = bandAlbumService.save(album3);
        joyDivision = album3.getBand();

        album4 = new Album("Closer", Genre.ROCK, 1980);
        album4.setBand(joyDivision);
        album4 = bandAlbumService.save(album4);
        joyDivision = album4.getBand();

        album5 = new Album("Born to Die", Genre.POP, 2012);
        album5.setBand(lanaDelRey);
        album5 = bandAlbumService.save(album5);
        lanaDelRey = album5.getBand();

        album6 = new Album("Chemtrails over the Country Club", Genre.FOLK, 2021);
        album6.setBand(lanaDelRey);
        album6 = bandAlbumService.save(album6);
        lanaDelRey = album6.getBand();

        album7 = new Album("Norman Fucking Rockwell", Genre.INDIE, 2019);
        album7.setBand(lanaDelRey);
        album7 = bandAlbumService.save(album7);
        lanaDelRey = album7.getBand();

        album8 = new Album("Surfer Rosa", Genre.INDIE, 1988);
        album8.setBand(pixies);
        album8 = bandAlbumService.save(album8);
        pixies = album8.getBand();

        album9 = new Album("Doolittle", Genre.INDIE, 1989);
        album9.setBand(pixies);
        album9 = bandAlbumService.save(album9);
        pixies = album9.getBand();

        album10 = new Album("Beneath the Eyrie", Genre.INDIE, 2019);
        album10.setBand(pixies);
        album10 = bandAlbumService.save(album10);
        pixies = album10.getBand();

        album11 = new Album("Bossanova", Genre.INDIE, 1990);
        album11.setBand(pixies);
        album11 = bandAlbumService.save(album11);
        pixies = album11.getBand();

        album12 = new Album("It Takes a Nation of Millions to Hold Us Back", Genre.RAP, 1988);
        album12.setBand(publicEnemy);
        album12 = bandAlbumService.save(album12);
        publicEnemy = album12.getBand();
    }

    public void setBandAlbumService(BandAlbumService bandAlbumService) {
        this.bandAlbumService = bandAlbumService;
    }

    public BandAlbumService getBandAlbumService() {
        return bandAlbumService;
    }

    public Band getTheStrokes() {
        return theStrokes;
    }

    public Band getJoyDivision() {
        return joyDivision;
    }

    public Band getLanaDelRey() {
        return lanaDelRey;
    }

    public Band getPixies() {
        return pixies;
    }

    public Band getPublicEnemy() {
        return publicEnemy;
    }

    public Album getAlbum1() {
        return album1;
    }

    public Album getAlbum2() {
        return album2;
    }

    public Album getAlbum3() {
        return album3;
    }

    public Album getAlbum4() {
        return album4;
    }

    public Album getAlbum5() {
        return album5;
    }

    public Album getAlbum6() {
        return album6;
    }

    public Album getAlbum7() {
        return album7;
    }

    public Album getAlbum8() {
        return album8;
    }

    public Album getAlbum9() {
        return album9;
    }

    public Album getAlbum10() {
        return album10;
    }

    public Album getAlbum11() {
        return album11;
    }

    public Album getAlbum12() {
        return album12;
    }
}
