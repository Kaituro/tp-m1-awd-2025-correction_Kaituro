package doremi.repositories;

import doremi.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepositoryWithJdbc implements ArticleRepositoryInt {

    @Autowired
    private DataSource dataSource;

    public List<Article> findAllArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT id, title, category FROM articles";

        try (Connection connexion = dataSource.getConnection();
             Statement statement = connexion.createStatement();
             ResultSet fetchedArticles = statement.executeQuery(sql)) {

            while (fetchedArticles.next()) {
                articles.add(new Article(
                        fetchedArticles.getInt("id"),
                        fetchedArticles.getString("title"),
                        fetchedArticles.getString("category")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des articles", e);
        }
        return articles;
    }

    public Article findArticleById(int id) {
        String sql = "SELECT id, title, category FROM articles WHERE id = ?";

        try (Connection connexion = dataSource.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet fetchedArticle = statement.executeQuery()) {
                if (fetchedArticle.next()) {
                    Article article = new Article();
                    article.setArticleId(fetchedArticle.getInt("id"));
                    article.setTitle(fetchedArticle.getString("title"));
                    article.setCategory(fetchedArticle.getString("category"));
                    return article;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'article avec id " + id, e);
        }

        return null;
    }

}
