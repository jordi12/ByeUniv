package bd;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class LigneDaoImpl implements LigneDao {

	@Autowired
	DataSource dataSource;

	public List<Ligne> getLigneList() {
		List<Ligne> ligneList = new ArrayList<Ligne>();

		String sql = "select * from ligne";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ligneList = jdbcTemplate.query(sql, new LigneRowMapper());
		return ligneList;
	}

	@Override
	public void updateData(Ligne ligne) {

		String sql = "UPDATE ligne set id = ?,shortName = ?, vote = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql,
				new Object[] { ligne.getId(), ligne.getShortName(),
						ligne.getVote() });

	}

	public void insertData(Ligne ligne) {

		String sql = "INSERT INTO ligne "
				+ "(id,shortName,vote,) VALUES (?, ?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql,
				new Object[] { ligne.getId(), ligne.getShortName(),
						ligne.getVote() });
	}

	@Override
	public void deleteData(String id) {
		String sql = "delete from ligne where id=" + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);

	}

	@Override
	public Ligne getLigne(String id) {
		List<Ligne> ligneList = new ArrayList<Ligne>();
		String sql = "select * from ligne where id= " + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ligneList = jdbcTemplate.query(sql, new LigneRowMapper());
		return ligneList.get(0);
	}

}
