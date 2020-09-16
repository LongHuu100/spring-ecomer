package vn.t3h.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import vn.t3h.model.Product;

@Repository("productDao")
@Transactional
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Product create(Product model) {
		entityManager.persist(model);
		return model;
	}

	@Override
	public Product update(Product model) {
		entityManager.merge(model);
		return model;
	}

	@Override
	public void delete(Product model) {
		entityManager.remove(model);
	}
	
	@Override
	public List<Product> listProducts() {
		return EntityQuery.create(entityManager, Product.class).list();
	}

	@Override
	public Product findById(int id) {
		try {
			return entityManager.find(Product.class, id);
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
