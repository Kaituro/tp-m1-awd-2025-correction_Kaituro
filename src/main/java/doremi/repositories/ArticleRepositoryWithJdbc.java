package doremi.repositories;

import doremi.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

}
