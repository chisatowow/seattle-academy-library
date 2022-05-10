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
 * 詳細表示コントローラー
 */
@Controller
public class RentBookController {
	final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

	@Autowired
	private RentService rentService;

	@Autowired
	private BooksService bookdService;

	/**
	 * 詳細画面に遷移する
	 * @param locale
	 * @param bookId
	 * @param model
	 * @return
	 */

	@Transactional
	@RequestMapping(value = "/rentBook", method = RequestMethod.POST) //value＝actionで指定したパラメータ
	//RequestParamでname属性を取得
	public String rentBook(Locale locale,
			@RequestParam("bookId") int bookId,
			Model model) {
		//デバッグ用ログ
		logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

		if(rentService.countValues(bookId) == 0) {
			rentService.rentBook(bookId);
			model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));
			return "details";
		}else if(rentService.countValues(bookId) == 1){
			model.addAttribute("rentedMessage","貸出し済みです。");
		}

		model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));
		return "details";
	}

}
