package doremi;

import doremi.domain.Article;
import doremi.repositories.ArticleRepositoryInt;
import doremi.services.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    private ArticleService articleService;

    @MockBean
    private ArticleRepositoryInt articleRepository;

    @MockBean
    private Article article;

    @BeforeEach
    public void setup() {
        articleService = new ArticleService();
        articleService.setArticleRepository(articleRepository);
    }

    @Test
    public void testTypeRepository() {
        // le dépôt associé au service est de type CrudRepository
        assertThat(articleService.getArticleRepository(), instanceOf(CrudRepository.class));
        // le dépôt associé au service est de type ArticleRepositoryInt
        assertThat(articleService.getArticleRepository(), instanceOf(ArticleRepositoryInt.class));
    }

    @Test
    public void testFindAllArticlesIsDelegatedToRepository() {
        // when: findAllArticles() est appelé sur un articleService
        articleService.findAllArticles();
        // then: findAll() du dépôt associé au service est invoqué
        verify(articleService.getArticleRepository()).findAll();
    }

    @Test
    public void testFindByIdArticleIsDelegatedToRepository() {
        // when: findArticleById() est appelé sur un articleService
        articleService.findArticleById(1L);
        // then: findById() du dépôt associé au service est invoqué
        verify(articleService.getArticleRepository()).findById(1L);
    }

    @Test
    public void testSaveArticleIsDelegatedToRepository() {
        // when: saveArticle() est appelé sur un articleService
        articleService.saveArticle(article);
        // then: saveArticle() du dépôt associé au service est invoqué
        verify(articleService.getArticleRepository()).save(article);
    }

}
