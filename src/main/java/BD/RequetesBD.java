package BD;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import demo.ItineraireResultat;

public class RequetesBD {

	Connect connection = new Connect();
	Connection conn = connection.connectionDB();
	HashMap<String, String> lignebd = new HashMap<String, String>();

	public void InsertionBD() throws SQLException {
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ItineraireResultat itres = new ItineraireResultat();
		lignebd = itres.getLignesDepart(lignebd);

		ResultSet resQuery = state
				.executeQuery("SELECT short_name FROM lignebus");
		
		boolean insertAFaire=false;
		String sql = "INSERT INTO lignebus (short_name, likes, id_ligne) VALUES";
		for (String mapKeyligne : lignebd.keySet()) {
			boolean pasPresent = true;
			resQuery.first();
			do {
				
				if(resQuery.getString("short_name").equals(lignebd.get(mapKeyligne)))
				{
					pasPresent=false;
				}
			}while (resQuery.next());
			if(pasPresent){
				insertAFaire=true;
				sql = sql + "('" + lignebd.get(mapKeyligne) + "','" + 0 + "','"
					+ mapKeyligne + "'),";
			}
			
		}
		sql = sql.substring(0, sql.length() - 1) + ";";

		if(insertAFaire) {state.executeUpdate(sql);}

		state.close();
	}
	
	public int getLikes(String shortName) throws SQLException {
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet resQuery = state
				.executeQuery("SELECT likes FROM lignebus WHERE CAST(short_name AS CHAR)=" + "CAST("+shortName+"AS CHAR)");
		int nbLikes = 0;
		
		if(resQuery.first())
			{nbLikes = resQuery.getInt("likes");}
		
		resQuery.close();
		
		state.close();
		return nbLikes;
	}
	
}
