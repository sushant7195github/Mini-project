package com.quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin {

	Scanner sc = new Scanner(System.in);
	ConnectionTest ct = new ConnectionTest();
	Connection con = ct.getConnectionDetails();

	private String name;
	private String password;
	private String exname;
	private String expassword;

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

	public String getExname() {
		return exname;
	}

	public void setExname(String exname) {
		this.exname = exname;
	}

	public String getExpassword() {
		return expassword;
	}

	public void setExpassword(String expassword) {
		this.expassword = expassword;
	}

	PreparedStatement ps = null;
	ResultSet resultUsername = null;
	ResultSet resultUserPassword = null;

	public void login() {

		// for 1st time user or not

		System.out.println("Please enter 1 for new user or 2 if already a user - ");
		int login1 = sc.nextInt();
		if (1 == login1) {
			newUser();
		} else if (2 == login1) {
			existingUser();
		} else {
			System.out.println("Please  valid input");
			login();
		}

	}

	private void newUser() {
		int flag = 0;
		UserLogin ul = new UserLogin();

		// New user name and password
		System.out.println("Please enter username - ");
		String tempName = sc.next();
		String query = "SELECT Username from quiz.user_login;";

		try {
			ps = con.prepareStatement(query);
			resultUsername = ps.executeQuery();

			while (resultUsername.next()) {
				String tempResult = resultUsername.getString("Username");

				if (tempResult.equals(tempName)) {
					System.out.println("Username is already exist please enter new username");
					try {
						throw new User_Already_Present_Exception();
					} catch (User_Already_Present_Exception e) {
						flag = 1;
						newUser();
					}

				}
			}

			ul.setName(tempName);
			String queryUserIn = "insert into quiz.user_login (Username) values (?)";

			// Prepared Statement
			PreparedStatement ps = con.prepareStatement(queryUserIn);

			// set values of parameter to Student_input class

			ps.setString(1, ul.getName());

			// Execute
			ps.executeUpdate();

			if (flag == 0) {
				System.out.println("Please enter password for new User - ");
				String tempPassword = sc.next();
				// ul.setPassword(sc.next());

				newUserData(ul.getName(), tempPassword);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void newUserData(String name, String password) {

		// code to insert login info in database

		String query = "update quiz.user_login set password= ? WHERE Username = ?";

		try {
			// Prepared Statement
			PreparedStatement ps = con.prepareStatement(query);

			// set values of parameter to Student_input class

			ps.setString(1, password);
			ps.setString(2, name);

			// Execute
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("welcome to the exam, Best of Luck !!!");

		StudentInfo studentInfo = new StudentInfo();
		studentInfo.insertStudentData();
	}

	private void existingUser() {

		// Existing user name and password

		int flag = 0;

		System.out.println("Please enter username - ");
		setExname(sc.next());

		/*
		 * System.out.println("Please enter Password - "); setExpassword(sc.next());
		 */

		String queryForName = "SELECT * FROM quiz.user_login";

		try {
			ps = con.prepareStatement(queryForName);
			resultUsername = ps.executeQuery();

			while (resultUsername.next()) {
				String tempResultName = resultUsername.getString("Username");

				if (tempResultName.equals(getExname())) {

					System.out.println("Please enter password - ");

					String tempPassword = sc.next();
					flag = 1;
					String queryForPassword = "SELECT * FROM quiz.user_login where Username = ?";

					ps = con.prepareStatement(queryForPassword);
					ps.setString(1, getExname());

					resultUserPassword = ps.executeQuery();
					while (resultUserPassword.next()) {
						String tempPasswordFromDB = resultUserPassword.getString("Password");

						if (tempPasswordFromDB.equals(tempPassword)) {

							System.out.println("For result please enter 1 or to give new exam please enter 2");
							int input = sc.nextInt();

							if (input == 1) {
								ResultSet rs1;
								System.out.println("Enter Student ID : ");
								int id = sc.nextInt();

								PreparedStatement ps = con
										.prepareStatement("select tempc.name,city,email,score from tempc where id=?");
								ps.setInt(1, id);
								rs1 = ps.executeQuery();
								System.out
										.println(" name   " + "|" + " city  " + "|" + " email      " + "|" + " score ");
								System.out.println("--------------------------------------");
								while (rs1.next()) {
									System.out.println(rs1.getString("name") + "\t|" + rs1.getString("city") + "|"
											+ rs1.getString("email") + " |" + rs1.getString("score"));
									break;
								}
							} else if (input == 2) {
								System.out.println("welcome to the exam, Best of Luck !!!");

								StudentInfo studentInfo = new StudentInfo();
								studentInfo.insertStudentData();

								break;
							} else {
								System.out.println("Entered choice is wrong");

								flag = 1;
								existingUser();
							}

						} else {
							System.out.println("Entered password is wrong");
							flag = 1;
							existingUser();
						}

					}
				} else {
					System.out.println("Entered username is wrong");
					flag = 1;
					existingUser();

				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
