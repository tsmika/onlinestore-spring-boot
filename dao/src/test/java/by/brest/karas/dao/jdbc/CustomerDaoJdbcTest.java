package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"})
public class CustomerDaoJdbcTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void findAllTest() {
        List<Customer> customers = customerDao.findAll();
        Assert.assertNotNull(customers);
        Assert.assertTrue(customers.size() > 0);
    }
}