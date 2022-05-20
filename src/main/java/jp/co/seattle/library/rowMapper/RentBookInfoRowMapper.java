package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.RentBookInfo;

@Configuration
public class RentBookInfoRowMapper implements RowMapper<RentBookInfo>{

	@Override
    public RentBookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	RentBookInfo rentBookInfo = new RentBookInfo();
	
	rentBookInfo.setBookId(rs.getInt("id"));
	rentBookInfo.setTitle(rs.getString("title"));
	rentBookInfo.setRentDate(rs.getString("rent_date"));
    rentBookInfo.setReturnDate(rs.getString("return_date"));
    return rentBookInfo;
	}
}
