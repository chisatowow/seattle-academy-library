package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        // TODO 取得したい情報を取得するようにSQLを修正
        String sql = "SELECT id, title, author, publisher, publish_date, thumbnail_url, bio, isbn FROM books ORDER BY title ASC";       
    	List<BookInfo> getedBookList = jdbcTemplate.query(sql, new BookInfoRowMapper());

        return getedBookList;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getBookInfo(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "SELECT * FROM books where id ="
                + bookId;

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

        return bookDetailsInfo;
    }

    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {

        String sql = "INSERT INTO books (title, author, publisher, publish_date, thumbnail_name, thumbnail_url, reg_date, upd_date, bio, isbn) VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"+ bookInfo.getPublishDate() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getThumbnailUrl() + "',"
                + "now(),"
                + "now(),'"
        		+ bookInfo.getBio()+"','"
        		+ bookInfo.getIsbn()+"')";

        jdbcTemplate.update(sql);
        
    }
    
    public void deleteBook(int bookId) {
    	
		String sql = "DELETE FROM books WHERE id ='"+bookId+"'";
    	
		jdbcTemplate.update(sql);
    	
    }
    
     /**
     *書籍IDに紐づく最新登録書籍情報を取得する
     *
     *@param bookId 書籍ID
     *@return 最新書籍情報
     */
    
    public int maxId(){    	
    	String sql ="SELECT Max(id) FROM books";
    	int MaxId = jdbcTemplate.queryForObject(sql,int.class);
    	return MaxId;
    }
    
}
