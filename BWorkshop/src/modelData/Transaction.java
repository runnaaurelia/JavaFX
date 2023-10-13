package modelData;

public class Transaction {

	private Integer transactionId;
	private Integer userId;
	private String userName;
	private String sparepartName;
	private String brand;
	private Integer quantity;
	private Integer sparepartPrice;
	
	public Transaction(Integer transactionId, Integer userId, String userName, String sparepartName, String brand,
			Integer quantity, Integer sparepartPrice) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.userName = userName;
		this.sparepartName = sparepartName;
		this.brand = brand;
		this.quantity = quantity;
		this.sparepartPrice = sparepartPrice;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getSparepartPrice() {
		return sparepartPrice;
	}

	public void setSparepartPrice(Integer sparepartPrice) {
		this.sparepartPrice = sparepartPrice;
	}
	
}
