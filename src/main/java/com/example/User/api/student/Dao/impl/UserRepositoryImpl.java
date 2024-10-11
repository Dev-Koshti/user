package com.example.User.api.student.Dao.impl;

import com.example.User.api.student.Dao.UserRepository;
import com.example.User.database.User;
import com.example.User.api.student.model.request.GetAllUserRequest;
import com.example.User.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void save(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public User update(String id, Update updateDocument) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findAndModify(query, updateDocument, User.class);
    }

    @Override
    public User findById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findAndRemove(query, User.class);
    }

    @Override
    public List<User> findAllWithFilter(GetAllUserRequest getAllUserRequest) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (Objects.nonNull(getAllUserRequest.getSearch_keyword()) && !getAllUserRequest.getSearch_keyword().isEmpty()) {
            String keyWord = Utils.replaceString(getAllUserRequest.getSearch_keyword());
            System.out.println("Search Keyword: " + keyWord); // Debugging

            Criteria nameCriteria = Criteria.where("name").regex(keyWord, "i");
            Criteria rollNumberCriteria = Criteria.where("roll_number").regex(keyWord, "i");
            criteriaList.add(nameCriteria);
            criteriaList.add(rollNumberCriteria);
        }

        Query query;
        if (criteriaList.isEmpty()) {
            query = new Query(); // Match all documents
        } else {
            Criteria criteria = new Criteria().orOperator(criteriaList.toArray(new Criteria[0]));
            query = new Query(criteria);
        }

        System.out.println("Query: " + query); // Debugging

        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println("Found Users: " + users); // Debugging

        return users;
    }

    @Override
    public User findWithRollNumber(String rollNumber) {
        Query query=new Query();
        query.addCriteria(Criteria.where("roll_number").is(rollNumber));

        return mongoTemplate.findOne(query,User.class);
    }

    @Override
    public void deleteNotInList(List<String> rollNumberList) {
        if (rollNumberList == null || rollNumberList.isEmpty()) {
            return; // No need to perform deletion if the list is empty
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("roll_number").nin(rollNumberList));

//        System.out.println("Deleting documents with roll numbers not in list: " + rollNumberList);
        mongoTemplate.remove(query, User.class);
    }
}

// Apply filters from the GetAllUserRequest object, e.g., by name or rollNumber
//        if (getAllUserRequest.getName() != null) {
//            query.addCriteria(Criteria.where("name").regex(getAllUserRequest.getName(), "i"));
//        }
//        if (getAllUserRequest.getRollNumber() != null) {
//            query.addCriteria(Criteria.where("rollNumber").is(getAllUserRequest.getRollNumber()));
//        }

// Find all users matching the filters