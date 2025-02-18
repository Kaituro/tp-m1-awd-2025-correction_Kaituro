package doremi.repositories;

import doremi.domain.Album;
import doremi.domain.Band;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
        return entityManager.find(Band.class, id);
    }

    public List<Band> findAllBand() {
        TypedQuery<Band> query = entityManager.createQuery("select b from Band b order by b.name", Band.class);
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