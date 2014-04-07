package bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import bd.Ligne;

public class LigneExtractor implements ResultSetExtractor<Ligne> {

	public Ligne extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		
		Ligne ligne = new Ligne();
		
		ligne.setId(resultSet.getString(1));
		ligne.setShortName(resultSet.getString(2));
		ligne.setVote(resultSet.getInt(3));

		return ligne;
	}

}
