package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

@Controller
public class BulkBookController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);

	@Autowired
	private BooksService booksService;

	@RequestMapping(value = "/bulkRegist", method = RequestMethod.GET, produces = "text/plain;charset=utf-8") //value＝actionで指定したパラメータ
	//RequestParamでname属性を取得
	public String bulkBook(Locale locale ,Model model) {

		return "bulkBook";
	}
	
	@RequestMapping(value = "/registed", method = RequestMethod.POST , produces = "text/plain;charset=utf-8") //value＝actionで指定したパラメータ
	//RequestParamでname属性を取得
	public String registedBooks(Locale local,Model model,@RequestParam("File")MultipartFile uploadFile) {
		List<String> errorMessage = new ArrayList<String>();
		List<BookDetailsInfo> notError = new ArrayList<BookDetailsInfo>();		
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(uploadFile.getInputStream(), StandardCharsets.UTF_8))){
		      String line;
		      int i = 1;
		      while ((line = br.readLine()) != null) {
		        final String[] split = line.split(",",-1);
		        boolean detailIsNull = split[0].isEmpty() || split[1].isEmpty()|| split[2].isEmpty() || split[3].isEmpty();
		        boolean isbnCheck10 = split[4].matches("^[0-9]{10}$");
		        boolean isbnCheck13 = split[4].matches("^[0-9]{13}$");
		        boolean pdCheck = split[3].matches("^[0-9]{8}$");		        
		        if(detailIsNull || !(isbnCheck10 || isbnCheck13) || !pdCheck) {
		        	errorMessage.add(i+"行目の書籍登録でエラーが起きました。");
		        }
		        if ((isbnCheck10 || isbnCheck13) && pdCheck && !detailIsNull) {
		        	BookDetailsInfo bookInfo = new BookDetailsInfo();
		        	bookInfo.setTitle(split[0]);
		        	bookInfo.setAuthor(split[1]);
		        	bookInfo.setPublisher(split[2]);
		        	bookInfo.setPublishDate(split[3]);
		        	bookInfo.setIsbn(split[4]);
		        	
		        	notError.add(bookInfo);
		        }
		        i ++;
		      }
			} catch (IOException e) {
		      throw new RuntimeException("ファイルが読み込めません", e);
		    }
		
		if(notError == null || notError.size() == 0) {
			model.addAttribute("booksEmpty","csvに書籍情報がありません。");
		}
		
		for(int n = 0;n<errorMessage.size();n++) {
			if(!(errorMessage == null || errorMessage.size() == 0)) {
				model.addAttribute("booksError",errorMessage);
			}
		}
		
		if(notError == null || notError.size() == 0 || !(errorMessage == null || errorMessage.size() == 0)){
			return "bulkBook";
		}
		
		for(int q=0;q<notError.size();q++) {
			booksService.registBook(notError.get(q));
		}
		model.addAttribute("bookList", booksService.getBookList());
		return "redirect:home";
		
	}
}