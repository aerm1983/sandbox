package localhost.test.ConfigBasicOps;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import localhost.ConfigBasicOps.pojo.SimplePojo;


@Service
public class Test00_ConfigBasicOps {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static Logger log = LoggerFactory.getLogger(Test00_ConfigBasicOps.class);

	public void test00_ConfigBasicOps() {

		log.info("Hello from test00_ConfigBasicOps");

		// initialize connection, implicit rowMapper
		List<Integer> intList00 = jdbcTemplate.query("select 9 as myInteger ;", (rs, rowNum) -> { return new Integer( rs.getInt("myInteger") ); } );
		log.info("intList00.size(): {} ; intList00.get(0): {}", intList00.size(), intList00.get(0));


		// initialize connection, explicit rowMapper, one column return
		RowMapper<Integer> rowMapper01 = (rs, rowNum) -> {
			Integer i = new Integer( rs.getInt("myInteger") );
			return i;
		};
		List<Integer> intList01 = jdbcTemplate.query("select 9 as myInteger ;", rowMapper01 );
		log.info("intList01.size(): {} ; intList01.get(0): {}", intList01.size(), intList01.get(0));


		// initialize connection, explicit rowMapper, serveral columns return
		RowMapper<SimplePojo> rowMapper02 = (rs, rowNum) -> {
			SimplePojo simplePojo = new SimplePojo();

			Integer myInt = new Integer( rs.getInt("myInteger") );
			String myString = rs.getString("myString");
			Date myDate = rs.getDate("myDate");

			simplePojo.setMyInt(myInt);
			simplePojo.setMyString(myString);
			simplePojo.setMyDate(myDate);

			return simplePojo;
		};
		List<SimplePojo> intList02 = jdbcTemplate.query("select 9 as myInteger, 'hello' as myString, now() as myDate ;", rowMapper02 );
		log.info("intList02.size(): {} ; intList02.get(0): {}", intList01.size(), intList01.get(0));


		log.info("main01 end");

	}

}
