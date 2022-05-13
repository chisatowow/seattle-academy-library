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

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentService;

/**
 *詳細表示コントローラー
 */
@Controller
public class ReturnBookController {
	final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

	@Autowired
	private RentService rentService;

	@Autowired
	private BooksService bookdService;
	
	/**
	 * 
	 * @param locale ロケール情報
	 * @param bookId 書籍ID
	 * @param model モデル情報
	 * @return 書籍詳細画面
	 */
	@Transactional
	@RequestMapping(value = "/returnBook", method = RequestMethod.POST) //value＝actionで指定したパラメータ
	//RequestParamでname属性を取得
	public String returnBook(Locale locale,
			@RequestParam("bookId") int bookId,
			Model model) {
		logger.info("Welcome detailsControler.java! The client locale is {}.", locale);
		
		if(rentService.countValues(bookId) > 0) {
			rentService.returnBook(bookId);
		}else {
			model.addAttribute("notRentMessage","貸出しされていません。");
		}
		
		model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));
		return "details";
	}
}
