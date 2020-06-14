//package com.team4.ordermanagement.user.entity;
//
//public class CartId {
//
//}


package com.management.UserMS.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class CartId implements Serializable {
	
	private Integer buyerid;
	
	private Integer prodid;
	

	
	
	public Integer getBuyerId() {
		return buyerid;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerid = buyerId;
	}

	public Integer getProdId() {
		return prodid;
	}

	public void setProdId(Integer prodId) {
		this.prodid = prodId;
	}
	
	
	
	public CartId() {
		// TODO Auto-generated constructor stub
	}
	
	

	public CartId(int buyerid, int prodid) {
		// TODO Auto-generated constructor stub
		this.buyerid=buyerid;
		this.prodid=prodid;
	}
	

	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId that = (CartId) o;
        return buyerid.equals(that.buyerid) && prodid.equals(that.prodid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyerid, prodid);
    }

}