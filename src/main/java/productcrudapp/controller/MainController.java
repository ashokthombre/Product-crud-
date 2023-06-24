package productcrudapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import productcrudapp.dao.ProductDao;
import productcrudapp.model.Product;

@Controller
public class MainController {
	@Autowired
	private ProductDao productDao;
   
	@RequestMapping("/")
	public String home(Model m)
	{
		System.out.println("Home");
		
		List<Product> p=productDao.getAllProducts();
		m.addAttribute("product",p);
		
		
		return "index";
	}
     	
	@RequestMapping("/add-product")
	public String addProduct(Model m)
	{
		m.addAttribute("title","Add Product");
		return"add_product_form";
	}
	
	//Handle add product form
	@RequestMapping(value = "/handle-product",method = RequestMethod.POST)
	public RedirectView handleProduct(@ModelAttribute Product product,HttpServletRequest request)
	{
		System.out.println(product);
		RedirectView redirectView=new RedirectView();
		redirectView.setUrl(request.getContextPath()+"/");
		
	  productDao.createProduct(product);
	  System.out.println("product Added");
		
		return redirectView;
		
		
	}
	
	//Delete Handler
	@RequestMapping("delete/{productId}")
	public RedirectView deleteProduct(@PathVariable("productId") int productId,HttpServletRequest request)
	{
		System.out.println(productId);
		 productDao.deleteProduct(productId);
		RedirectView redirectView=new RedirectView();
		redirectView.setUrl(request.getContextPath()+"/");
		 
	
	  System.out.println("product Deleted");
		
		return redirectView;
		
	}
	//Updateform
	@RequestMapping("update/{productId}")
	public String update(@PathVariable("productId") int pid,Model model)
	{
		System.out.println(pid);
	Product product=this.productDao.getProduct(pid);
	model.addAttribute("product",product);
		
		return "update_form";
	}
	
	
	
}
