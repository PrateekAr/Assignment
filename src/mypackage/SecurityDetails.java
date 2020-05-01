package mypackage;


public class SecurityDetails {

	private String securityName;
	private String timestamp;
	private String action;
	private String price;
	
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "SecurityDetails [securityName=" + securityName + ", timestamp="
				+ timestamp + ", action=" + action + ", price=" + price + "]";
	}

	
	
}
