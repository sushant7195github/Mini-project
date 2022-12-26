package com.quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class StudentInfo {
	private int stud_id;
	private String stud_name;
	private String stud_city;
	private String stud_email;

	public int getStud_id() {
		return stud_id;
	}

	public void setStud_id(int stud_id) {
		this.stud_id = stud_id;
	}

	public String getStud_name() {
		return stud_name;
	}

	public void setStud_name(String stud_name) {
		this.stud_name = stud_name;
	}

	public String getStud_city() {
		return stud_city;
	}

	public void setStud_city(String stud_city) {
		this.stud_city = stud_city;
	}

	public String getStud_email() {
		return stud_email;
	}

	public void setStud_email(String stud_email) {
		this.stud_email = stud_email;
	}

	
	@Override
	public String toString() {
		return "StudentInfo [stud_id=" + stud_id + ", stud_name=" + stud_name + ", stud_city=" + stud_city
				+ ", stud_email=" + stud_email + "]";
	}

	void insertStudentData()

	{

		/*ConnectionTest ct = new ConnectionTest();
		Connection con = ct.getConnectionDetails();
		PreparedStatement ps = null;*/
		Scanner scanner = new Scanner(System.in);
		//StudentInfo studentInfo = new StudentInfo();

		System.out.println("Student Detail");
		System.out.println("Enter student id: ");
		setStud_id(scanner.nextInt());
		System.out.println("Enter student name:");
		setStud_name(scanner.next());
		System.out.println("Enter student city:");
		setStud_city(scanner.next());
		System.out.println("Enter student email id:");
		setStud_email(scanner.next());

		QuestionBank qb1=new QuestionBank(getStud_id(),getStud_name(),getStud_city(),getStud_email());
		qb1.getQuestions();
		
		
		/*
		 * try { ps=con.
		 * prepareStatement("insert into tempc(id,name,city,email) values (?,?,?,?)");
		 * ps.setInt(1, studentInfo.getStud_id()); ps.setString(2,
		 * studentInfo.getStud_name()); ps.setString(3, studentInfo.getStud_city());
		 * ps.setString(4, studentInfo.getStud_email()); ps.executeUpdate();
		 * 
		 * 
		 * 
		 * } catch(Exception e) { System.out.println(e); }
		 */
	}

}
