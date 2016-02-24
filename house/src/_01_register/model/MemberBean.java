package _01_register.model;

import java.io.*;
import java.sql.*;

public class MemberBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int   pkey;
	String User_account;
	String User_name;
	String User_password;
	String User_address;
	String User_email;
	String User_phone;
	String User_type;
	String User_gender;
	Date User_birth;
	Blob User_photo;
	Clob comment;
	String User_photoName;
	
	
	
	
	public int getPkey() {
		return pkey;
	}

	public void setPkey(int pkey) {
		this.pkey = pkey;
	}

	public Blob getUser_photo() {
		return User_photo;
	}

	public void setUser_photo(Blob user_photo) {
		User_photo = user_photo;
	}
	
	
	public String getUser_account() {
		return User_account;
	}

	public void setUser_account(String user_account) {
		User_account = user_account;
	}

	public String getUser_name() {
		return User_name;
	}

	public void setUser_name(String user_name) {
		User_name = user_name;
	}

	public String getUser_password() {
		return User_password;
	}

	public void setUser_password(String user_password) {
		User_password = user_password;
	}

	public String getUser_address() {
		return User_address;
	}

	public void setUser_address(String user_address) {
		User_address = user_address;
	}

	public String getUser_email() {
		return User_email;
	}

	public void setUser_email(String user_email) {
		User_email = user_email;
	}

	public String getUser_phone() {
		return User_phone;
	}

	public void setUser_phone(String user_phone) {
		User_phone = user_phone;
	}

	public String getUser_type() {
		return User_type;
	}

	public void setUser_type(String user_type) {
		User_type = user_type;
	}

	public String getUser_gender() {
		return User_gender;
	}

	public void setUser_gender(String user_gender) {
		User_gender = user_gender;
	}

	public Date getUser_birth() {
		return User_birth;
	}

	public void setUser_birth(Date user_birth) {
		User_birth = user_birth;
	}



	public String getUser_photoName() {
		return User_photoName;
	}

	public void setUser_photoName(String user_photoName) {
		User_photoName = user_photoName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
	
	
	
	
	public Blob getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(Blob memberImage) {
		this.memberImage = memberImage;
	}

	public Clob getComment() {
		return comment;
	}

	public void setComment(Clob comment) {
		this.comment = comment;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public MemberBean(int pKey, String memberId, String name, String password, String address,  String mail,
			String tel, String userType ,int experience, Timestamp ts, double totalAmt) {
		super();
		this.pkey = pKey;
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.address = address;
		this.email = mail;
		this.tel = tel;
		this.userType = userType;		
		this.experience = experience;
		this.ts = ts;
		this.totalAmt = totalAmt;
	}
	
	public MemberBean(String memberId, String name, String password, String address,  String mail,
			String tel, int expericnce) {
		super();
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.address = address;
		this.email = mail;
		this.tel = tel;
		this.experience = expericnce;
	}
	

	public MemberBean() {
		super();
	}	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		email = mail;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String toString() {
		return "userid=" + memberId + "   password="+password;
	}

	public String getUserType() {
		return userType;
	}

	public Timestamp getTs() {
		return ts;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

}
