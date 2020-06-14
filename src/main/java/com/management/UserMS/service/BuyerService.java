package com.management.UserMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.naming.InvalidNameException;
import javax.validation.Validator;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//import com.management.UserMS.Validator.BuyerValidator;
import com.management.UserMS.dto.BuyerDTO;
import com.management.UserMS.dto.CartDTO;
import com.management.UserMS.dto.ProductDTO;
import com.management.UserMS.dto.WishlistDTO;
import com.management.UserMS.entity.Buyer;
import com.management.UserMS.entity.Cart;
import com.management.UserMS.entity.Wishlist;
import com.management.UserMS.repository.BuyerRepository;
import com.management.UserMS.repository.CartRepository;
import com.management.UserMS.repository.WishlistRepository;


@Service
public class BuyerService {
	private static final Logger logger = LoggerFactory.getLogger(BuyerService.class);
	@Autowired
	BuyerRepository buyerRepo;
	
	@Autowired
	CartRepository cartRepo;
	@Autowired
	WishlistRepository wishlistRepo;

	
	
	public String registerBuyer(BuyerDTO buyerDTO) throws Exception {
		
		try {
		logger.info("Registration request for user {}", buyerDTO);
		validateBuyer(buyerDTO);
		Buyer be=buyerDTO.createEntity();
		buyerRepo.save(be);
		return("new user created");
		}catch(Exception e) {
			throw new Exception("Name is not valid");
		}
	}
	private void validateBuyer(BuyerDTO buyerDTO) throws Exception {

		logger.info("Buyer details are being validated");
		// TODO Auto-generated method stub
		if(!isValidName(buyerDTO.getName()))
			throw new InvalidNameException("BuyerRegistration: Invalid Name");
		if(!isValidEmail(buyerDTO.getEmail()))
			throw new Exception("BuyerRegistration: Invalid Email");
		if(!isValidPhoneNumber(buyerDTO.getPhoneNumber()))
			throw new Exception("BuyerRegistration:Invalid Phone number");
		if(!isvalidPassword(buyerDTO.getPassword()))
			throw new Exception("BuyerRegistration: Invalid Password");
		if(!isAlreadyPhoneNumberExist(buyerDTO.getPhoneNumber()))
			throw new Exception("BuyerRegistration: Phone number already exists");
		if(!isAlreadyEmailIdExist(buyerDTO.getEmail()))
			throw new Exception("BuyerRegistration: Email already exists");
		
		
	}

	private boolean isAlreadyEmailIdExist(String email) {
		// TODO Auto-generated method stub
		Buyer buyer=buyerRepo.findByEmail(email);
		if (buyer!=null)
			return false;
		return true;
	}

	private boolean isAlreadyPhoneNumberExist(String phoneNumber) {
		// TODO Auto-generated method stub
		Buyer buyer=buyerRepo.findByPhoneNumber(phoneNumber);
		if (buyer!=null)
			return false;
		return true;
	}

	private boolean isvalidPassword(String password) {
		return Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{7,20}$",password);
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return Pattern.matches("^\\d{10}$", phoneNumber);
	}

	private boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$",email);
	}

	private boolean isValidName(String name) {
		// TODO Auto-generated method stub
		return Pattern.matches("^[a-zA-Z]+[-a-zA-Z\\s]+([-a-zA-Z]+)$", name);
	}

	
	

	public void buyerLogin(BuyerDTO buyerDTO) throws Exception {

		Buyer buyer = buyerRepo.findByEmail(buyerDTO.getEmail());
		if (buyer != null) {
			if (buyer.getPassword().equals(buyerDTO.getPassword())) {
			;
			} else {
				throw new Exception("BuyerLogin:Invalid Password");
			}
		}else {
			throw new Exception("buyerLogin:InvalidEmail");
		}
		
	}

	public void deactivateBuyer(BuyerDTO buyerDTO) throws Exception {

		Buyer buyer = buyerRepo.findByEmail(buyerDTO.getEmail());
		if (buyer != null) {
			buyer.setIsActive(false);
			buyerRepo.save(buyer);
		} else {
			System.out.println("Invalid Email ID ");
		}

	}
	
	
	public int getRewardPoints(int buyerId) {
		System.out.println("BuyerId"+buyerId);
		Buyer buyer=buyerRepo.findByBuyerId(buyerId);
		return buyer.getRewardPoints();

		
	}
	
	public void updateRewardPoint(int buyerId, int point) {
		Buyer buyer =buyerRepo.findByBuyerId(buyerId);
		if (buyer!=null){
		buyer.setRewardPoints(point);
		buyerRepo.save(buyer);
		}else {
			System.out.println("Invalid BuyerId");
		}
		
	}
	
public boolean IsPrivileged(int buyerId) {
		
		Buyer buyer= buyerRepo.findByBuyerId(buyerId);
		
		if((buyer.getIsPrivileged())==false) {
			
			return false;
		}
		else {
			
			return true;
		}
		
	}

public List<CartDTO> getCartItems() {
	// TODO Auto-generated method stub
	List<Cart> cart = cartRepo.findAll();
	List<CartDTO> cartDTO =new ArrayList<CartDTO>();
	for(Cart c : cart) {
		CartDTO c1 = CartDTO.valueOf(c);
		cartDTO.add(c1);
		
	}
	
	return cartDTO;
}


public List<WishlistDTO> getWishlistItems() {
	// TODO Auto-generated method stub
	List<Wishlist> wishlist = wishlistRepo.findAll();
	List<WishlistDTO> wishlistDTO = new ArrayList<WishlistDTO>();
	for(Wishlist w : wishlist) {
		WishlistDTO w1 = WishlistDTO.valueOf(w);
		wishlistDTO.add(w1);
	}
	return wishlistDTO;
	
}

public void addToWishlist(ProductDTO productDTO) {
	// TODO Auto-generated method stub
	WishlistDTO wishlistDTO= new WishlistDTO();
	wishlistDTO.setBuyerid(1000);
	wishlistDTO.setProdid(productDTO.getProdid());
	Wishlist wishlist= wishlistDTO.createEntity();
	wishlistRepo.save(wishlist);
	
	
}
public  void addToCart(CartDTO cartDTO) {
	// TODO Auto-generated method stub
//	CartDTO cart1= new CartDTO();
//	cart1.setBuyerid(1000);
//	cart1.setProdid(cartDTO.getProdid());
//	cart1.setQuantity(cartDTO.getQuantity());
	Cart cart2= cartDTO.createEntity();
	cartRepo.save(cart2);
	
}
public void deleteProductFromCart(Integer prodId) {
	// TODO Auto-generated method stub
	cartRepo.deleteById(prodId);
}
public void deleteProductFromWishlist(Integer prodId) {
	// TODO Auto-generated method stub
	cartRepo.deleteById(prodId);
	
}


}
