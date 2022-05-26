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

import jp.co.seattle.library.service.RentService;

@Controller
public class RentHistoryController {
	final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);
	
	
	@Autowired
	private RentService rentService;
	
	@Transactional
	@RequestMapping(value = "/rentHistory", method = RequestMethod.GET) //value＝actionで指定したパラメータ
	public String rentHistory(Locale locale,
			Model model){
		logger.info("Welcome rentHistoryControler.java! The client locale is {}.", locale);
		
		model.addAttribute("rentBookList", rentService.rentBookList());
        return "rentHistory";
	}
}
