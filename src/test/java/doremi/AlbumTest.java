package doremi;

import doremi.domain.Album;
import doremi.domain.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class AlbumTest {

    private static Validator validator;

    @BeforeAll
    public static void setupContext() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAlbumTitreCategorieAnneeOK() {
        // given: un Album avec un titre, un genre et une année valides
        Album a = new Album("Is This It", Genre.INDIE, 2001);
        // when: on vérifie les contraintes de validation de l'Album
        // then: il n'y a pas d'erreur de validation
        Assertions.assertTrue(validator.validate(a).isEmpty());
    }

    @Test
    public void testAlbumTitreNullNOK() {
        // given: un Album sans titre mais avec un genre et une année valides
        Album a = new Album(null, Genre.INDIE, 2001);
        // when: on vérifie les contraintes de validation de l'Album
        // then: il y a une erreur de validation
        Assertions.assertFalse(validator.validate(a).isEmpty());
    }

    @Test
    public void testAlbumTitreEmptyOK() {
        // given: un Album avec un titre vide, un genre et une année valides
        Album a = new Album("", Genre.INDIE, 2001);
        // when: on vérifie les contraintes de validation de l'Album
        // then: il n'y a pas d'erreur de validation
        Assertions.assertTrue(validator.validate(a).isEmpty());
    }

    @Test
    public void testAlbumAnneeAvant1950NOK() {
        // given: un Album avec une année avant 1950
        Album a = new Album("", Genre.INDIE, 1949);
        // when: on vérifie les contraintes de validation de l'Album
        // then: il y a une erreur de validation
        Assertions.assertFalse(validator.validate(a).isEmpty());
    }

    @Test
    public void testAlbumAnneeFutureNOK() {
        // given: un Album avec une année après l'année courante
        Album a = new Album("", Genre.INDIE, 2028);
        // when: on vérifie les contraintes de validation de l'Album
        // then: il y a une erreur de validation
        Assertions.assertFalse(validator.validate(a).isEmpty());
    }
}
