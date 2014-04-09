package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

//CTRL + SHIFT + O pour générer les imports
public class Connect {
	/*public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver O.K.");

			String url = "jdbc:postgresql://localhost:5432/byeuniv";
			String user = "postgres";
			String passwd = "blanc12";

			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");
			Statement state = conn.createStatement();

			ResultSet result = state.executeQuery("SELECT * FROM lignebus");
			ResultSetMetaData resultMeta = result.getMetaData();

			System.out.println("- Il y a " + resultMeta.getColumnCount()
					+ " colonnes dans cette table");
			for (int i = 1; i <= resultMeta.getColumnCount(); i++)
				System.out.println("\t *" + resultMeta.getColumnName(i));

			while (result.next()) {
				System.out.print("\t" + result.getInt("id_ligne") + "\t |");
				System.out
						.print("\t" + result.getString("short_name") + "\t |");
				System.out.print("\t" + result.getInt("likes") + "\t |");
				System.out.println("\n---------------------------------");
			}

			result.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public Connection connectionDB() {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver O.K.");

			String url = "jdbc:postgresql://localhost:5432/byeuniv";
			String user = "postgres";
			String passwd = "blanc12";

			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");
			

			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}