package com.example.User.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAuthTokenQueryDaoImpl implements UserAuthTokenQueryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserAuthToken findById(String id) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("user_id").is(id));
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, UserAuthToken.class);
    }

    @Override
    public void save(UserAuthToken userAuthToken) {
        mongoTemplate.save(userAuthToken);
    }

    @Override
    public List<UserAuthToken> getAll() {
        return mongoTemplate.findAll(UserAuthToken.class);
    }
}
