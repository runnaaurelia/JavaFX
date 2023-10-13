package modelData;

public class Sparepart {
	private String sparepartId;
	private String sparepartName;
	private String brand;
	private int price;
	private int stock;
	
	public Sparepart(String sparepartId, String sparepartName, String brand, int price, int stock) {
		super();
		this.sparepartId = sparepartId;
		this.sparepartName = sparepartName;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
	}

	public String getSparepartId() {
		return sparepartId;
	}

	public void setSparepartId(String sparepartId) {
		this.sparepartId = sparepartId;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
	
	
