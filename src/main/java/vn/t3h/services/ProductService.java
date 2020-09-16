package vn.t3h.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.t3h.dao.ProductDao;
import vn.t3h.model.Product;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<Product> getAllProduct() {
		return productDao.listProducts();
	}
}
