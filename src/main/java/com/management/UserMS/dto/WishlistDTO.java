package com.management.UserMS.dto;

import com.management.UserMS.entity.Buyer;
import com.management.UserMS.entity.Wishlist;


public class WishlistDTO {

	Integer buyerid;
	Integer prodid;
	
	public Integer getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(Integer buyerid) {
		this.buyerid = buyerid;
	}
	public Integer getProdid() {
		return prodid;
	}
	public void setProdid(Integer prodid) {
		this.prodid = prodid;
	}
	
	public Wishlist createEntity(){
		// TODO Auto-generated method stub
		Wishlist wishlist = new Wishlist();
		wishlist.getCartId().setBuyerId(this.getBuyerid());
		wishlist.getCartId().setProdId(this.getProdid());
		
		return wishlist;
	}
	
	
	public static WishlistDTO valueOf(Wishlist wishlist) {
		// TODO Auto-generated method stub
		WishlistDTO wishlistDTO=new WishlistDTO();
		wishlistDTO.setBuyerid(wishlist.getCartId().getBuyerId());
		wishlistDTO.setProdid(wishlist.getCartId().getProdId());
		
		return wishlistDTO;
	}
	
	@Override
	public String toString() {
		return "WishlistDTO [BuyerId="+ buyerid + "ProdId=" +prodid+"]";
	}
	
}