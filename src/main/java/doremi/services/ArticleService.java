package doremi.services;

import doremi.domain.Article;
import doremi.repositories.ArticleRepositoryInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepositoryInt articleRepository;

    public List<Article> findAllArticles() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article saveArticle(Article article) {
        if (article == null)
            throw new InvalidDataAccessApiUsageException("Article must not be null");
        return articleRepository.save(article);
    }

    public ArticleRepositoryInt getArticleRepository() {
        return articleRepository;
    }

    public void setArticleRepository(ArticleRepositoryInt articleRepository) {
        this.articleRepository = articleRepository;
    }

    public long countArticle() {
        return articleRepository.count();
    }
}