package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.UserDao;
import by.brest.karas.model.Role;
import by.brest.karas.model.User;

import java.util.List;

public class UserDaoJdbc implements UserDao {

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public List<User> selectUsers(String filter) {
        return null;
    }

    @Override
    public void save(User user, Role role) {

    }

    @Override
    public void update(Integer id, User updatedUser) {

    }

    @Override
    public void delete(Integer id) {

    }
}
