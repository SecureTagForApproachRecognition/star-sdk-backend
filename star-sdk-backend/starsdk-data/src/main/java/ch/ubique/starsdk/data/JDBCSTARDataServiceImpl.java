package ch.ubique.starsdk.data;

import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import ch.ubique.starsdk.model.Exposee;

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
			sql = "insert into t_exposed (key, app_source) values (:key, :app_source)"
					+ " on conflict on constraint key do nothing";
		} else {
			sql = "merge into t_exposed using (values(cast(:key as varchar(10000)), cast(:app_source as varchar(50))))"
					+ " as vals(key, app_source) on t_exposed.key = vals.key"
					+ " when matched then update set t_exposed.key = vals.key, t_exposed.app_source = vals.app_source"
					+ " when not matched then insert (key, app_source) values (vals.key, vals.app_source)";
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("key", exposee.getKey());
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

	@Override
	@Transactional(readOnly = false)
	public boolean validateRedeemCode(String code) {
		Integer count = jt.queryForObject(
				"select count(*) from t_redeem_code where pk_redeem_code = :redeemCode and is_used = False",
				new MapSqlParameterSource("redeemCode", code), Integer.class);
		if (count != null && count == 1) {
			jt.update("update t_redeem_code set is_used = True where pk_redeem_code = :redeemCode",
					new MapSqlParameterSource("redeemCode", code));
			return true;
		} else {
			return false;
		}
	}
}
