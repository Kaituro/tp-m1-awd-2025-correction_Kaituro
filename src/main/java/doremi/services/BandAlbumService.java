package doremi.services;

import doremi.domain.Album;
import doremi.domain.Band;
import doremi.repositories.BandAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BandAlbumService {

    private final BandAlbumRepository bandAlbumRepository;

    @Autowired
    public BandAlbumService(BandAlbumRepository bandAlbumRepository) {
        this.bandAlbumRepository = bandAlbumRepository;
    }

    public void save(Album album) {
        if (album == null) {
            throw new IllegalArgumentException("L'album ne peut pas être null");
        }
        bandAlbumRepository.save(album);
    }

    public void save(Band band) {
        if (band == null) {
            throw new IllegalArgumentException("Le groupe ne peut pas être null");
        }
        bandAlbumRepository.save(band);
    }

    public Album findAlbumById(Long id) {
        return bandAlbumRepository.findAlbumById(id);
    }

    public Band findBandById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return bandAlbumRepository.findBandById(id);
    }

    public BandAlbumRepository getBandAlbumRepository() {
        return bandAlbumRepository;
    }
}