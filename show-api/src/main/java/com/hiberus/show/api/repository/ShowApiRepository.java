package com.hiberus.show.api.repository;

import com.hiberus.show.api.domain.entity.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowApiRepository extends MongoRepository<Show, String> { }
