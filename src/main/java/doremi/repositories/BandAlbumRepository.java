package doremi.repositories;

import doremi.domain.Album;
import doremi.domain.Band;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

import java.util.List;

@Repository
public class BandAlbumRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Album save(Album album) {
        if (album == null) {
            throw new IllegalArgumentException("Album cannot be null");
        }
        Band savedBand = this.save(album.getBand());
        album.setBand(savedBand);
        Album savedAlbum = entityManager.merge(album);
        savedBand.addAlbum(savedAlbum);
        return savedAlbum;
    }

    @Transactional
    public Band save(Band band) {
        if (band == null) {
            throw new IllegalArgumentException("Band cannot be null");
        }
        return entityManager.merge(band);
    }

    public Album findAlbumById(Long id) {
        return entityManager.find(Album.class, id);
    }

    public Band findBandById(Long id) {
        // erreur "failed to lazily initialize a collection of role..."
        // solution 1 : garder l'appel à findBandById et passer en EAGER
        // le chargement des entités dans l'annotation @OneToMany (classe Band)
        // return entityManager.find(Band.class, id);

        // solution 2 : changer la façon de requêter et utiliser une jointure pour charger les albums
        try {
            return entityManager.createQuery("select b from Band b left join fetch b.albums where b.id = :id", Band.class)
                    .setParameter("id", id).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<Band> findAllBand() {
        TypedQuery<Band> query = entityManager.createQuery("select b from Band b order by b.name", Band.class);
        return query.getResultList();
    }

    public List<Album> findAlbumsByYear(int year) {
        return entityManager.createQuery("select a from Album a where a.year = :year", Album.class).setParameter("year", year).getResultList();
    }

    @Transactional
    public void deleteBandById(Long id) {
        entityManager.remove(findBandById(id));
    }

    public List<Band> findAllActiveBands(boolean active) {
        TypedQuery<Band> query = entityManager.createQuery("select b from Band b where b.active=:active order by b.name", Band.class);
        query.setParameter("active", active);
        return query.getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Cette méthode est utilisée uniquement pour injecter un EntityManager mocké dans les tests unitaires
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}