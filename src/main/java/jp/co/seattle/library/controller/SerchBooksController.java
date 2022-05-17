package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

@Controller
public class SerchBooksController {
	final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;

    /**
     * 
     * @param model モデル情報
     * @param locale ロケール情報
     * @param serchTitle 検索書籍タイトル
     * @param radiobutton 検索フィルター選択
     * @return 検索書籍一覧
     */
    @RequestMapping(value = "/serchBook", method = RequestMethod.POST) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Model model,
    		Locale locale,
    		@RequestParam("serchTitle") String serchTitle,
    		@RequestParam("radiobutton") int radiobutton) {
    	// デバッグ用ログ
    	logger.info("Welcome SerchBooksController.java! The client locale is {}.", locale);
    	
    	//部分一致=0、完全一致=1
    	if(radiobutton == 0) {
    		if(booksService.serchBookList(serchTitle).isEmpty()) {
    			model.addAttribute("resultMessage","一致する書籍がありません。");
    			return "home";
    		}else {
    			model.addAttribute("bookList",booksService.serchBookList(serchTitle)); 
    		}
    	}else {
    		if(booksService.serchMatchBookList(serchTitle).isEmpty()) {
    			model.addAttribute("resultMessage","一致する書籍がありません。");
    			return "home";
    		}else {
    			model.addAttribute("bookList",booksService.serchMatchBookList(serchTitle)); 
    		}
    	}
    	
        return "home";
    }
    
    
    
}
