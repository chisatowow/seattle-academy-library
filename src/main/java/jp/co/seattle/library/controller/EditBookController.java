package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

@Controller 
public class EditBookController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);
	
	@Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "text/plain;charset=utf-8") //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String editBook(Locale locale ,Model model ,@RequestParam("bookId")int bookId) {
    	model.addAttribute("bookInfo",booksService.getBookInfo(bookId));
        return "edit";
    }
 
    /**
     * 書籍情報を更新する
     * @param locale ローケル情報
     * @param title 書籍名
     * @param author 著者名
     * @param publisher 出版社
     * @param publish_date 出版日
     * @param file サムネファイル
     * @palam model モデル
     * @param isbn コード
     * @param bio 説明文
     * @param id 書籍ID
     * @return 遷移先画面
     */
    @Transactional
    @RequestMapping(value = "/updBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String updBook(Locale locale,
    		@RequestParam("bookId")int bookId,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publish_date")String publish_date,
            @RequestParam("thumbnail") MultipartFile file,            
            @RequestParam("isbn")String isbn,
            @RequestParam("bio")String bio,             
            Model model) {
        logger.info("Welcome updBooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublishDate(publish_date);
        bookInfo.setIsbn(isbn);
        bookInfo.setBio(bio);
        bookInfo.setBookId(bookId);

        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookInfo", bookInfo);
                return "edit";
            }
        }
        
        boolean detailIsNull = title.isEmpty() || author.isEmpty()|| publisher.isEmpty() || publish_date.isEmpty();
        boolean isbnCheck10 = isbn.matches("^[0-9]{10}$");
        boolean isbnCheck13 = isbn.matches("^[0-9]{13}$");
        boolean pdCheck = publish_date.matches("^[0-9]{8}$");        

        if(detailIsNull) {
        	model.addAttribute("detailIsNull","必須項目を入力してください");        	
        }
        
        if(!(isbnCheck10 || isbnCheck13)){
        	model.addAttribute("isbn","ISBNの桁数または半角数字が正しくありません");        	
        }                
        
        if (!pdCheck) {
        	model.addAttribute("pd","出版日は半角数字のYYYYMMDD形式で入力してください");            	
        } 
        
        if (!(isbnCheck10 || isbnCheck13) || !pdCheck || detailIsNull) {
        	model.addAttribute("bookInfo",bookInfo);
        	return "edit";
        }
                
        // 書籍情報を編集し登録する
        booksService.editBook(bookInfo);
 
        int editId = bookInfo.getBookId();

        // TODO 編集した書籍の詳細情報を表示するように実装        
        
        model.addAttribute("bookDetailsInfo",booksService.getBookInfo(editId));
        
        //  詳細画面に遷移する
        return "details";
        
    }
    
}
