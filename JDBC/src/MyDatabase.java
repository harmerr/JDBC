import java.sql.*;

public class MyDatabase {
	
	private Connection con = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public void readDataBase() throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "VerySimplePassword1");
		
		statement = con.createStatement();
		
		resultSet = statement.executeQuery("SELECT * FROM offices");
		
		writeResultSet(resultSet);
		
		preparedStatement = con.prepareStatement("INSERT into offices values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		preparedStatement.setString(1, "TestCode");
		preparedStatement.setString(2, "TestCity");
		preparedStatement.setString(3, "TestPhone");
		preparedStatement.setString(4, "TestAddressLine1");
		preparedStatement.setString(5, "TestAddressLine2");
		preparedStatement.setString(6, "TestState");
		preparedStatement.setString(7, "TestCountry");
		preparedStatement.setString(8, "TestPostalCode");
		preparedStatement.setString(9, "TestTerr");
		
		preparedStatement.executeUpdate();
	
		preparedStatement = con.prepareStatement("SELECT officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory FROM offices");
		resultSet = preparedStatement.executeQuery();
		writeResultSet(resultSet);
		
		preparedStatement = con.prepareStatement("DELETE FROM offices WHERE officeCode= ? ; ");
		preparedStatement.setString(1, "Test");
		preparedStatement.executeUpdate();
		
		resultSet = statement.executeQuery("SELECT * FROM offices");
		writeMetaData(resultSet);
		
		close();
	}
	
	private void writeMetaData(ResultSet resultSet) throws SQLException {
		
		System.out.println("The columns in the table are: ");
		
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		
		for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
		
	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException {
		
		while(resultSet.next()) {
			System.out.println("Office code: " + resultSet.getString("officeCode"));
			System.out.println("City: " + resultSet.getString("city"));
			System.out.println("Phone number: " + resultSet.getString("phone"));
			System.out.println("Address line 1: " + resultSet.getString("addressLine1"));
			System.out.println("Address line 2: " + resultSet.getString("addressLine2"));
			System.out.println("State: " + resultSet.getString("state"));
			System.out.println("Country: " + resultSet.getString("country"));
			System.out.println("Postal code: " + resultSet.getString("postalCode"));
			System.out.println("Territory: " + resultSet.getString("territory"));
		}	
	}
	
	private void close() {
		try {
			if(resultSet != null) {
				resultSet.close();
			}
			
			if(statement != null) {
				statement.close();
			}
			
			if(con != null) {
				con.close();
			}
		} 
		catch(Exception e) {

		}
	}
}
