package com.example.stwo.db;

public class People {

		private Long id;
		private int i;
		private String Name;
		private String Email;
		private int age;
		private String password;
		private String image;
		private String Address;
		
		public People() {
		}

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getPassword(){
			return password;
		}
		
		public void setPassword(String pass){
			this.password=pass;
		}
		
		public int getid() {
			return i;
		}
		public void setId(int id) {
			this.i = id;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			this.Name = name;
		}
		public String getEmail() {
			return Email;
		}
		public void setEmail(String email) {
			this.Email = email;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		
		
		public String getAddress(){
			return Address;
		}
		
		public void setAddress(String Address){
			this.Address=Address;
		}
		
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		
	}


