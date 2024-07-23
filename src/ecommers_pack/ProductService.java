package ecommers_pack;


import java.util.List;

import ecommers.DAO.ProductDAO;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public void createProduct(Product product) {
        productDAO.createProduct(product);
    }

    public Product getProduct(int id) {
        return productDAO.getProduct(id);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
}
