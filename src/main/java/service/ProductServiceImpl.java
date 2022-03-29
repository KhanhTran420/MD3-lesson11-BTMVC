package service;

import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService {

    private static Map<Integer, Product> products;

    static {
        products = new HashMap<>();
        products.put(1,new Product(1,"Quat Dien",300000,5,true));
        products.put(2,new Product(2,"Den Dien",50000,4,false));
        products.put(3,new Product(3,"Bep Ga",600000,3,true));
        products.put(4,new Product(4,"Tu Lanh",3000000,2,false));
    }

    public List<Product>findAll(){
        return new ArrayList<>(products.values());
    }

    public void save(Product product) {
        products.put(product.getId(), product);
    }

    public Product findById(int id) {
        return products.get(id);
    }

    public void update(int id, Product customer) {
        products.put(id, customer);
    }

    public void remove(int id) {
        products.remove(id);
    }
}
