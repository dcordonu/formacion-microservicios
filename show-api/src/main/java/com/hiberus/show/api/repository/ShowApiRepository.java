package com.hiberus.show.api.repository;

import com.hiberus.show.library.repository.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowApiRepository extends MongoRepository<Show, String> { }
