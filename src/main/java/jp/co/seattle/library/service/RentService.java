package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
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
	 * 貸し出した書籍の詳細情報を登録する
	 * 
	 * @param bookId 書籍ID
	 */

	public void rentBook(int bookId){
		String sql = "INSERT INTO rent (book_id) SELECT "+bookId+" WHERE NOT EXISTS (SELECT book_id FROM rent WHERE book_id ="+bookId+")";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 書籍IDに紐づく書籍が借りられていることを取得する
	 * @param bookId　書籍ID
	 * @return 書籍ID
	 */
	public int countValues(int bookId){
		String sql = "SELECT COUNT(book_id) FROM rent WHERE book_id ="+bookId+"";
		int countValues = jdbcTemplate.queryForObject(sql,int.class);
		return countValues;
	}
	
	/**
	 * 貸し出し書籍の情報を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void returnBook(int bookId) {
		String sql = "DELETE FROM rent WHERE book_id ="+bookId+"";
		
		jdbcTemplate.update(sql);
	}
}
