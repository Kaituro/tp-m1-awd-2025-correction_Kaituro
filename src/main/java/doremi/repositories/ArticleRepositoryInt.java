package doremi.repositories;

import doremi.domain.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepositoryInt extends CrudRepository<Article, Long> {

}