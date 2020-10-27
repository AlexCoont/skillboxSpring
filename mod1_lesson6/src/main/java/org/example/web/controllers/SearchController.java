package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {
    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getSearchPage(Model model){
        model.addAttribute("bookList", bookService.getAllBooks());
        return "search_page";
    }

    @PostMapping
    public String searchBooks(@RequestParam(value = "author") String author,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "size") Integer size,
                              @RequestParam(value = "useAnd", required = false) boolean useAnd,
                              Model model){
        logger.info(useAnd);
        if (StringUtils.isEmpty(author) && StringUtils.isEmpty(title) && null == size){
            return "redirect:/search";
        }
        model.addAttribute("bookList", bookService.getBooksBySearchQuery(author, title, size, useAnd));
        return "search_page";
    }
}
