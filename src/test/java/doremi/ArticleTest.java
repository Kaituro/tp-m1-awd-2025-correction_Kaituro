package doremi;

import doremi.domain.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


public class ArticleTest {

    private static Validator validator;

    @BeforeAll
    public static void setupContext() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testIdTitreCategorieOK() {
        // given: un Article avec un id positif, un titre et une catégorie valides
        Article a = new Article("Titre de l'article", "Categorie de l'article");
        // when: on vérifie les contraintes de validation de l'Article
        // then: il n'y a pas d'erreur de validation
        Assertions.assertTrue(validator.validate(a).isEmpty());
    }

    @Test
    public void testTitreVide() {
        // given: un Article avec un titre vide
        Article a = new Article("", "Sortie");
        // when: on vérifie les contraintes de validation de l'Article
        // then: il y a une erreur de validation
        Assertions.assertFalse(validator.validate(a).isEmpty());
    }

    @Test
    public void testTitreNull() {
        // given: un Article avec un titre null
        Article a = new Article( null, "Sortie");
        // when: on vérifie les contraintes de validation de l'Article
        // then: il y a une erreur de validation
        Assertions.assertFalse(validator.validate(a).isEmpty());
    }

    @Test
    public void testCategorieNull() {
        // given: un Article avec une catégorie null
        Article a = new Article("Nouvel album de Sparks cette année", null);
        // when: on vérifie les contraintes de validation de l'Article
        // then: il n'y a pas d'erreur de validation
        Assertions.assertTrue(validator.validate(a).isEmpty());
    }

}
