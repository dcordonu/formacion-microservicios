package com.hiberus.show.sink.repository;

import com.hiberus.show.library.repository.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowSinkRepository extends MongoRepository<Show, String> {

    Optional<Show> findByIsan(final String isan);
}
