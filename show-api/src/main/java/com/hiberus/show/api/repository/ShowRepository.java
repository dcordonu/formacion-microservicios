package com.hiberus.show.api.repository;

import com.hiberus.show.api.domain.entity.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends MongoRepository<Show, String> { }
