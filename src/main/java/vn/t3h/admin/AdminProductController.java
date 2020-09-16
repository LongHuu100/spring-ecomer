package vn.t3h.admin;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.t3h.controller.BaseController;
import vn.t3h.dao.CategoryDao;
import vn.t3h.dao.ProductDao;
import vn.t3h.model.Category;
import vn.t3h.model.Product;
import vn.t3h.services.CategoryService;
import vn.t3h.services.ProductService;

@Controller
@RequestMapping("admin/product")
public class AdminProductController extends BaseController {
	
	@Autowired ProductService productService;
	@Autowired CategoryService categoryService;
	@Autowired ProductDao productDao;
	
	@GetMapping(value = {"", "/"})
	public String getProducts(Model model) {
		model.addAttribute("listProduct", productService.getAllProduct());
		return "admin/product/index";
	}
	
	@GetMapping(value = "/create")
	public String createProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("listCategory", categoryService.categoryWithIndent());
		return "admin/product/form";
	}
	
	@PostMapping(value = "/create")
	public String productForm(@Valid @ModelAttribute(value="product") Product product, 
			BindingResult bindingResult,  Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("product", product);
			model.addAttribute("listCategory", categoryService.categoryWithIndent());
			return "admin/product/form";
		}
		productDao.create(product);
		return "redirect:/admin/product";
	}
	
	@GetMapping(value = "/update")
	public String updateProduct(
			@RequestParam Integer id,
			Model model) {
		var product = productDao.findById(id);
		if(product == null) {
			throw new RuntimeException("Không có record id: " + id);
		}
		model.addAttribute("product", product);
		model.addAttribute("listCategory", categoryService.categoryWithIndent());
		return "admin/product/form";
	}
	
	@PostMapping(value = "/update")
	public String categoryFormUpdate(
			@Valid 
			@ModelAttribute(value="product") Product product, 
			BindingResult bindingResult,
			@RequestParam Integer id,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("product", product);
			model.addAttribute("listCategory", categoryService.categoryWithIndent());
			return "admin/product/form";
		}
		var prodInDb = productDao.findById(id);
		if(prodInDb == null) {
			throw new RuntimeException("Không có record id: " + id);
		}
		
		BeanUtils.copyProperties(product, prodInDb, new String[] {"createTime", "updateTime"});
		productDao.update(prodInDb);
		return "redirect:/admin/product";
	}
}