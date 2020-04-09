package ch.ubique.starsdk.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ch.ubique.starsdk.model.Exposee;

public class ExposeeRowMapper implements RowMapper<Exposee> {
	@Override
	public Exposee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Exposee exposee = new Exposee();
		exposee.setKey(rs.getString("key"));
		exposee.setId(rs.getInt("pk_exposed_id"));
		exposee.setOnset(rs.getString("onset_string"));
		return exposee;
	}
}