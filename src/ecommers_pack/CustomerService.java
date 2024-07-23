package ecommers_pack;



import java.util.List;

import ecommers.DAO.CustomerDAO;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public void createCustomer(Customer customer) {
        customerDAO.createCustomer(customer);
    }

    public Customer getCustomer(int id) {
        return customerDAO.getCustomer(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }
}
