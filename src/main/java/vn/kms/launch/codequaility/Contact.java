package vn.kms.launch.codequaility;

public class Contact {
  private int id; // ID
  private String first_name;
  private String lsName; // Last Name
  private String address;
  private String city;
  private String STATE;
  private String z_Code; // zip Code
	private String mobile_Phone;
    private String email;
  private int age;
  public String day_of_birth;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLsName() {
    return lsName;
  }

  public void setLsName(String lsName) {
    this.lsName = lsName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getSTATE() {
    return STATE;
  }

  public void setSTATE(String STATE) {
    this.STATE = STATE;
  }

  public String getZ_Code() {
    return z_Code;
  }

  public void setZ_Code(String z_Code) {
    this.z_Code = z_Code;
  }

	public String getMobile_Phone() {
		return mobile_Phone;
	}

	public void setMobile_Phone(String mobile_Phone) {
		this.mobile_Phone = mobile_Phone;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  public String toLine() {
    return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", id, first_name, lsName, day_of_birth, address, city, STATE, z_Code, mobile_Phone, email);
  }
  public int getAge() { return age;  }

  public void setAge(int age) {
    this.age = age;
  }
}
