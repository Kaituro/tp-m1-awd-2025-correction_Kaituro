package doremi;

import doremi.domain.Article;
import doremi.services.ArticleService;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ArticleServiceIntegrationTest {

    @Autowired
    private ArticleService articleService;

    private Article newArticle;

    @BeforeEach
    public void setUp() {
        newArticle = new Article("Title", "Category");
    }

    @Test
    public void testSavedArticleHasId(){
        // given: un Article non persistée newArticle
        // then: newArticle n'a pas d'id
        assertNull(newArticle.getArticleId());
        // when: newArticle est persisté
        Article articleSaved = articleService.saveArticle(newArticle);
        // then: newArticle a un id
        assertNotNull(articleSaved.getArticleId());
    }

    @Test
    public void testSaveNullEntailsException() {
        // when: une requête pour sauver un Article null en base est émise (manuellement)
        // then: une exception est levée
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> { articleService.saveArticle(null); });
    }

    @Test
    public void testFetchedArticleIsNotNull() {
        // given: un Article persistée articleSaved
        Article articleSaved = articleService.saveArticle(newArticle);
        // when: on appelle findArticleById avec l'id de cet Article
        Article fetched = articleService.findArticleById(articleSaved.getArticleId());
        // then: le résultat n'est pas null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedArticleIsUnchangedForDescriptif() {
        // given: un Article persistée articleSaved
        Article articleSaved = articleService.saveArticle(newArticle);
        // when: on appelle findArticleById avec l'id de cet Article
        Article fetched = articleService.findArticleById(articleSaved.getArticleId());
        // then: l'Article obtenu en retour a le bon id
        assertEquals(fetched.getArticleId(), articleSaved.getArticleId());
        // then : l'Article obtenu en retour a le bon titre
        assertEquals(fetched.getTitle(), articleSaved.getTitle());
    }

    @Test
    @Transactional
    public void testUpdatedArticleIsUpdated() {
        // given: un Article persistée articleSaved
        Article articleSaved = articleService.saveArticle(newArticle);

        Article fetched = articleService.findArticleById(articleSaved.getArticleId());
        // when: le titre est modifié au niveau "objet"
        fetched.setTitle("Nouveau titre");
        // when: l'objet est mis à jour en base
        Article fetchedSaved = articleService.saveArticle(fetched);
        // when: l'objet est relu en base
        Article fetchedUpdated = articleService.findArticleById(articleSaved.getArticleId());
        // then: le titre a bien été mis à jour
        assertEquals(fetchedSaved.getTitle(), fetchedUpdated.getTitle());
    }

    @Test
    @Transactional
    public void testUpdateDoesNotCreateANewEntry() {
        // given: un Article persistée articleSaved
        Article articleSaved = articleService.saveArticle(newArticle);

        long count = articleService.countArticle();
        Article fetched = articleService.findArticleById(articleSaved.getArticleId());
        // when: le titre est modifié au niveau "objet"
        fetched.setTitle("Nouveau titre");
        // when: l'objet est mis à jour en base
        articleService.saveArticle(fetched);
        // then: une nouvelle entrée n'a pas été créée en base
        assertEquals(count, articleService.countArticle());
    }

    @Test
    public void testFindArticleWithUnexistingId() {
        // when:  findArticleById est appelé avec un id ne correspondant à aucun objet en base
        // then: null est retourné
        assertNull(articleService.findArticleById(1000L));
    }

    @Test
    public void testFindAllCardinal() {
        // when: une requête pour obtenir tous les articles en base est émise
        // then: une liste de 9 éléments est reçu en réponse
        int count = 0;
        for (Article article : articleService.findAllArticles()) {
            count++;
        }
        assertEquals(9, count, "the batch provides 9 articles");
    }

    @Test
    public void testFindAllPremiereCategorie() {
        // when: une requête pour obtenir le premier article en base est émise
        // then: la réponse est un article de la catégorie "Concert"
        assertEquals("Concert", (articleService.findAllArticles().iterator().next().getCategory()));
    }



}