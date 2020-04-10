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
			sql = "insert into t_exposed (key, onset, app_source) values (:key, to_date(:onset, 'yyyy-MM-dd'), :app_source)"
					+ " on conflict on constraint key do nothing";
		} else {
			sql = "merge into t_exposed using (values(cast(:key as varchar(10000)), cast(:onset as date), cast(:app_source as varchar(50))))"
					+ " as vals(key, onset, app_source) on t_exposed.key = vals.key"
					+ " when not matched then insert (key, onset, app_source) values (vals.key, vals.onset, vals.app_source)";
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("key", exposee.getKey());
		params.addValue("app_source", appSource);
		params.addValue("onset", exposee.getOnset());
		jt.update(sql, params);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Exposee> getExposedForDay(DateTime day) {
		DateTime dayMidnight = day.minusMillis(day.getMillisOfDay());
		String sql = "select pk_exposed_id, key, to_char(onset, 'yyyy-MM-dd') as onset_string from t_exposed where onset >= :dayMidnight and onset < :nextDayMidnight";
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
