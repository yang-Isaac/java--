package cn.tedu.store.entity;

/**
 * 收货地址数据的实体类
 */
public class Address extends BaseEntity {

	private static final long serialVersionUID = 205823991333230728L;

	private Integer aid;
	private Integer uid;
	private String receiver;
	private String province;
	private String city;
	private String area;
	private String district;
	private String zip;
	private String address;
	private String phone;
	private String tel;
	private String tag;
	private Integer isDefault;

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "Address [aid=" + aid + ", uid=" + uid + ", receiver=" + receiver + ", province=" + province + ", city="
				+ city + ", area=" + area + ", district=" + district + ", zip=" + zip + ", address=" + address
				+ ", phone=" + phone + ", tel=" + tel + ", tag=" + tag + ", isDefault=" + isDefault + "]";
	}
}
