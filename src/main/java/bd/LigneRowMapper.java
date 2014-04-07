package bd;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class LigneRowMapper implements RowMapper<Ligne> {

	@Override
	public Ligne mapRow(ResultSet resultSet, int line) throws SQLException {
		LigneExtractor userExtractor = new LigneExtractor();
		return userExtractor.extractData(resultSet);
	}

}
