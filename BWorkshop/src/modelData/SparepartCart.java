package modelData;

public class SparepartCart {

	private String sparepartId;
	private Integer userId;
	private String sparepartName;
	private String brand;
	private Integer quantity;
	private Integer price;
	private Integer total;
	
	public SparepartCart(String sparepartId, Integer userId, String sparepartName, String brand, Integer quantity,
			Integer price, Integer total) {
		super();
		this.sparepartId = sparepartId;
		this.userId = userId;
		this.sparepartName = sparepartName;
		this.brand = brand;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
	}
//	public Integer sumTotal(Integer quantity, Integer price) {
//		return quantity * price;
//	}

	public String getSparepartId() {
		return sparepartId;
	}

	public void setSparepartId(String sparepartId) {
		this.sparepartId = sparepartId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSparepartName() {
		return sparepartName;
	}

	public void setSparepartName(String sparepartName) {
		this.sparepartName = sparepartName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
