package by.brest.karas.dao;

import by.brest.karas.model.Role;
import by.brest.karas.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByLogin(String login);

    User findById(Integer id);

    List<User> selectUsers(String filter);

    void save (User user, Role role);

    void update(Integer id, User updatedUser);

    void delete(Integer id);
}
