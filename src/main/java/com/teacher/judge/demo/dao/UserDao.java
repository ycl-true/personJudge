package com.teacher.judge.demo.dao;

import com.teacher.judge.demo.bo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserDao extends JpaRepository<User, String> {
    List<User> findByUserIdIn(List<String> list);

//    @Query(value = "select u from User u where u.userName = :name and u.password = :password")
//    User findByNameAndPassword(@Param("name") String name,
//                               @Param("password") String password);
    User findByUserNameAndAndPasswordAndValid(String name, String password, String valid);

    User findUserByUserName(String userName);

    List<User> findAllByPersonType(String personType);

    List<User> findAllByValid(String valid);
}
