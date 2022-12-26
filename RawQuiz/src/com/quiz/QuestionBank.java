package com.quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.sql.Statement;

public class QuestionBank {
	Statement st = null;
	PreparedStatement ps = null;
	int score1;

	int id;
	String name;
	String city;
	String email;

	public QuestionBank(int id, String name, String city, String email) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.email = email;
	}

	public void getQuestions() {

		System.out.println("\nStart your Exam\n");
		ResultSet rs;
		ResultSet rs1;
		ResultSet rs2;

		ConnectionTest ct = new ConnectionTest();
		try {
			Connection con = ct.getConnectionDetails();
			Statement st = con.createStatement();

			List<String> l = new ArrayList();
			List<String> l1 = new ArrayList<>();

			rs = st.executeQuery(
					"select question,option_a,option_b,option_c,option_d,ans from question_set order by rand()");

			while (rs.next()) {

				String que = rs.getString("question");
				String opt1 = rs.getString("option_a");
				String opt2 = rs.getString("option_b");
				String opt3 = rs.getString("option_c");
				String opt4 = rs.getString("option_d");
				String answer = rs.getString("ans");

				l.add("\n" + que + "\n" + opt1 + "\n" + opt2 + "\n" + opt3 + "\n" + opt4 + "\n");
				l1.add(answer);
			}
			String[] question = new String[l.size()];
			l.toArray(question);

			String[] ans1 = new String[l1.size()];
			l1.toArray(ans1);

			for (int j = 0; j < question.length; j++) {
				System.out.println(question[j]);
				Scanner sc = new Scanner(System.in);

				System.out.println("Enter Answer - ");
				String ans = sc.nextLine();

				if (ans.equalsIgnoreCase("a") || ans.equalsIgnoreCase("b") || ans.equalsIgnoreCase("c")
						|| ans.equalsIgnoreCase("d")) {

					if (ans.equalsIgnoreCase(ans1[j])) {
						score1++;
					}

				} else {
					j--;
					System.out.println("Please Enter valid input");
				}

			}
			System.out.println("\nYour attempt is successfully loaded \n");
			System.out.println("\nYour Score is : " + score1 + " out of 10");

			if (score1 >= 8) {
				System.out.println("Class A");
			} else if (score1 >= 6 && score1 < 8)
				System.out.println("Class B");
			else if (score1 == 5)
				System.out.println("Class C");
			else {
				System.out.println("Class D-Fail");
			}

			try {
				ps = con.prepareStatement("insert into tempc(id,name,city,email,score) values (?,?,?,?,?)");
				ps.setInt(1, id);
				ps.setString(2, name);
				ps.setString(3, city);
				ps.setString(4, email);
				ps.setInt(5, score1);
				ps.executeUpdate();

			} catch (Exception e) {
				System.out.println(e);
			}



			System.out.println("\nStudent Result : \n");

			ps = con.prepareStatement("select * from tempc order by score desc");

			rs2 = ps.executeQuery();
			System.out.println(" ID     " + "|" + " Score " + "|" + " Name " );
			System.out.println("--------------------------------------");
			while (rs2.next()) {
				System.out
						.println(rs2.getString("id") + "\t|" + rs2.getString("score") + "\t|" + rs2.getString("name"));

			}

			System.out.println("--------------------------------------\n");
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Student ID : ");
			int id = sc.nextInt();

			PreparedStatement ps = con.prepareStatement("select tempc.name,city,email,score from tempc where id=?");
			ps.setInt(1, id);
			rs1 = ps.executeQuery();
			System.out.println(" name   " + "|" + " city  " + "|" + " email      " + "|" + " score ");
			System.out.println("--------------------------------------");
			while (rs1.next()) {
				System.out.println(rs1.getString("name") + "\t|" + rs1.getString("city") + "|" + rs1.getString("email")
						+ " |" + rs1.getString("score"));
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
