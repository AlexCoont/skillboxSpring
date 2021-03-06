package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";

    }

    @GetMapping("goSearch")
    public String goToSearch(){
        logger.info("go to search books");
        return "redirect:/search";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            if (StringUtils.isEmpty(book.getAuthor()) && StringUtils.isEmpty(book.getTitle()) && null == book.getSize()) {
                return "redirect:/books/shelf";
            }
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("removeAll")
    public String removeAllBooks(@RequestParam(value = "authorNameToRemove") String authorNameToRemove,
                                 @RequestParam(value = "titleToRemove") String titleToRemove,
                                 @RequestParam(value = "sizeToRemove") Integer sizeToRemove){
        if (!StringUtils.isEmpty(authorNameToRemove)){
            bookService.removeBooksByAuthor(authorNameToRemove);
        }
        if (!StringUtils.isEmpty(titleToRemove)){
            bookService.removeBooksByTitle(titleToRemove);
        }
        if (null != sizeToRemove){
            bookService.removeBooksBySize(sizeToRemove);
        }
        return "redirect:/books/shelf";
    }
}
