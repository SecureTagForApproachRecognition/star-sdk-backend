package ch.ubique.starsdk.data;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import ch.ubique.starsdk.model.Exposee;

import javax.sql.DataSource;
import java.util.List;

public class JDBCSTARDataServiceImpl implements STARDataService {

	private static final Logger logger = LoggerFactory.getLogger(JDBCSTARDataServiceImpl.class);

	private final String dbType;
	private static final String PGSQL = "pgsql";
	private final NamedParameterJdbcTemplate jt;

	public JDBCSTARDataServiceImpl(String dbType, DataSource dataSource) {
		this.dbType = dbType;
		this.jt = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	@Transactional(readOnly = false)
	public void upsertExposee(Exposee exposee, String appSource) {
		String sql = null;
		if (dbType.equals(PGSQL)) {
			sql = "insert into t_exposed (key, action, app_source) values (:key, :action, :app_source)" +
					" on conflict on constraint key_action do nothing";
		} else {
			sql = "merge into t_exposed using (values(cast(:key as varchar(10000)), cast(:action as varchar(20)), cast(:app_source as varchar(50))))" +
					 " as vals(key, app_source, action) on t_exposed.key = vals.key and t_exposed.action = vals.action" +
					 " when matched then update set t_exposed.key = vals.key, t_exposed.app_source = vals.app_source" +
					 " when not matched then insert (key, action, app_source) values (vals.key, vals.action, vals.app_source)";
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("key", exposee.getKey());
		params.addValue("action", exposee.getAction().name());
		params.addValue("app_source", appSource);
		jt.update(sql, params);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Exposee> getExposedForDay(DateTime day) {
		DateTime dayMidnight = day.minusMillis(day.getMillisOfDay());
		String sql = "select * from t_exposed where received_at >= :dayMidnight and received_at < :nextDayMidnight";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("dayMidnight", dayMidnight.toDate());
		params.addValue("nextDayMidnight", dayMidnight.plusDays(1).toDate());
		return jt.query(sql, params, new ExposeeRowMapper());
	}
}
