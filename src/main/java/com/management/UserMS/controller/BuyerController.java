package com.management.UserMS.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import com.management.UserMS.Validator.BuyerValidator;
import com.management.UserMS.dto.BuyerDTO;
import com.management.UserMS.dto.CartDTO;
import com.management.UserMS.dto.ProductDTO;
import com.management.UserMS.dto.WishlistDTO;
import com.management.UserMS.entity.Buyer;
import com.management.UserMS.service.BuyerService;



@RestController
@CrossOrigin
@RequestMapping(value="/api")
public class BuyerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BuyerService buyerService;
	
	

	@PostMapping(value="buyer/register",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public String registerUser(@RequestBody BuyerDTO buyerDTO) {
		try {
		logger.info("Registration request for buyer {}", buyerDTO);
		buyerDTO.setIsActive(true);
		buyerDTO.setIsPrivileged(false);
		buyerDTO.setRewardPoints(0);
		buyerService.registerBuyer(buyerDTO);
		return "Success";
	}catch(Exception e) {
		return("Already registered");
	}
	}

	@PostMapping(value = "buyer/login",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String loginBuyer(@RequestBody BuyerDTO buyerDTO) throws Exception {
		try {
			buyerService.buyerLogin(buyerDTO);
			return "Login Successfull";
		} catch (Exception e) {
			return " Login unsuccessfull, check your credentials and try again";
		}
	}


	@PostMapping(value = "buyer/deactivate",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deactivateBuyer(@RequestBody BuyerDTO buyerDTO) throws Exception{
			try {
				buyerService.deactivateBuyer(buyerDTO);
			} catch (Exception e) {
				throw new Exception("Invalid Credentials");
			}
			return "Account Deactivated";
	
	}
	
	@GetMapping(value = "rewardPoint/{buyerId}")
	public int getRewardPoints(@PathVariable int buyerId) {
		System.out.println("BuyerID is:"+ buyerId);
		int Points = buyerService.getRewardPoints(buyerId);
		return Points;
	
	}
	
	@PutMapping(value = "rewardPoint/update/{buyerId}/{point}")
	public void updateRewardPoint(@PathVariable int buyerId,@PathVariable int point) {
	
		buyerService.updateRewardPoint(buyerId, point);

	}
	
	@GetMapping(value = "buyer/isPrivilege/{buyerId}")
	public boolean isBuyerPrivileged(@PathVariable int buyerId) {
		System.out.println("inside buyer privilege");
		return buyerService.IsPrivileged(buyerId);
	}
	@GetMapping(value="cart/{buyerId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<CartDTO> buyerCartItems(@PathVariable int buyerId) {
		List<CartDTO> cartDTO= buyerService.getCartItems();
		return cartDTO;
	}
	
	@GetMapping(value="wishlist/{buyerId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WishlistDTO> buyerWishlistItems(@PathVariable int buyerId) {
		List<WishlistDTO> wishlistDTO= buyerService.getWishlistItems();
		return wishlistDTO;
	}
	@PostMapping(value= "/add/wishlist/{prodid}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public  String addProductToWishlist(@PathVariable Integer prodid) {
		try {
		System.out.println("hellooo");
		logger.info("ADD product to wishlist request{}",prodid);
		ProductDTO productDTO = new RestTemplate().getForObject("http://PRODUCTMS"+"/products/productid/"+prodid, ProductDTO.class);
		buyerService.addToWishlist(productDTO);
		return "Added";
		}catch(Exception e) {
			return "not added";
		}
	}
	
	@PostMapping(value= "/add/cart",consumes = MediaType.APPLICATION_JSON_VALUE)
	public  String addProductToCart(@RequestBody CartDTO cartDTO) {
		try {
			System.out.println("hiii");
//			logger.info("ADD product to wishlist request{}",prodid);
//		ProductDTO productDTO = template.getForObject("http://PRODUCTMS"+"/products/productid/"+prodid, ProductDTO.class);
//		CartDTO cartDTO =new CartDTO();
//		cartDTO.setProdid(productDTO.getProdid());
//		cartDTO.setQuantity(quantity);
		buyerService.addToCart(cartDTO);
		return "Added";
		}catch(Exception e) {
			return "not added";
		}
	}
	@DeleteMapping(value="remove/cart/{prodId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public  void removeFromCart(@PathVariable Integer prodId) {
	    buyerService.deleteProductFromCart(prodId);
	  }
	
	@DeleteMapping(value="remove/wishlist{prodId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public  void removeFromWishlist(@PathVariable Integer prodId) {
	    buyerService.deleteProductFromWishlist(prodId);
	  }
}

