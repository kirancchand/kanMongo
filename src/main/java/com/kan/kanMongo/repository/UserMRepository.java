package com.kan.kanMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kan.kanMongo.vo.UserM;

public interface UserMRepository extends MongoRepository<UserM, String> {
}