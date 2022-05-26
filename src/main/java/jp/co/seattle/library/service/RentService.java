package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.RentBookInfo;
import jp.co.seattle.library.rowMapper.RentBookInfoRowMapper;
/**
 * 書籍サービス
 * 
 *rentテーブルに関する処理を実装する
 */
@Service
public class RentService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

	/**
	 * 貸し出した（貸出履歴のない）書籍の詳細情報を登録する
	 * 
	 * @param bookId 書籍ID
	 */

	public void rentBook(int bookId){
		String sql = "INSERT INTO rent (book_id,rent_date) SELECT "+bookId+",now() WHERE NOT EXISTS (SELECT book_id FROM rent WHERE book_id ="+bookId+")";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 貸出履歴のある書籍を貸出する
	 * 
	 * @param bookId 書籍ID
	 */
	
	public void againRentBook(int bookId) {
		String sql = "UPDATE rent SET (rent_date,return_date) = (now(),null) where book_id ="+bookId;
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 書籍IDに紐づく書籍が借りられているかbookIdの有無で判断する
	 * @param bookId　書籍ID
	 * @return 書籍ID
	 */
	public int countValues(int bookId){
		String sql = "SELECT COUNT(book_id) FROM rent WHERE book_id ="+bookId;
		int countValues = jdbcTemplate.queryForObject(sql,int.class);
		return countValues;
	}
	
	/**
	 * 書籍IDに紐づく書籍が借りられていることを貸出日の有無で判断する
	 * @param bookId 書籍ID
	 * @return 書籍ID
	 */
	public int countDate(int bookId) {
		String sql = "SELECT COUNT(rent_date) FROM rent WHERE book_id="+bookId;
		int countDate = jdbcTemplate.queryForObject(sql,int.class);
		return countDate;
	}
	
	/**
	 * 貸し出し書籍を返却する
	 * 
	 * @param bookId 書籍ID
	 */
	public void returnBook(int bookId) {
		String sql = "UPDATE rent SET (rent_date,return_date) = (null,now()) WHERE book_id ="+bookId;
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 書籍情報に紐づいた貸出情報を取得
	 * 
	 * @param bookId
	 * @return
	 */
	public List<RentBookInfo> rentBookList() {
		String sql = "SELECT id, title, rent_date, return_date FROM books LEFT JOIN rent ON books.id = rent.book_id  WHERE EXISTS (SELECT book_id FROM rent WHERE book_id= id)";
		
		List<RentBookInfo> rentBookList = jdbcTemplate.query(sql, new RentBookInfoRowMapper());
		return rentBookList;
	}
}
