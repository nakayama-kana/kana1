package dbtest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String driverName=("com.mysql.cj.jdbc.Driver");
			Class.forName("driverName");
			
			String url="jdbc:musql://localhost/ex01?serverTimezone=JST";
			String user="root";
			String password="root";
			con=DriverManager.getConnection(url,user,password);
			
			String sql="SELECT date,name,price,quantity,"+
					"price * quantity as amount"+
						"FROM sale,goods"+
							"WHERE sale.code=goods.code"+
								"ORDER BY date,goods.code";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			Date date;
			String name;
			int price,quantity,amount;
			
			while(rs.next()) {
				date = rs.getDate("date");
				name = rs.getString("name");
				price = rs.getInt("price");
				quantity = rs.getInt("quantity");
				amount = rs.getInt("amount");
				System.out.printf("%tF %10s %6d %4d %7d%n",date,name,price,quantity,amount);
			}
			
		}
		catch (ClassNotFoundException e) {
			System.err.println("ドライバが見つかりません");
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
			}
		
		finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(con != null) {
					con.close();
				}
			}
			catch (SQLException e) {
				System.err.println(e.getMessage());
		}
		}
	}

}
