package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public void removeBooksByAuthor(String authorNameToRemove){
        bookRepo.removeItemsByAuthor(authorNameToRemove);
    }

    public void removeBooksByTitle(String title){
        bookRepo.removeItemsByTitle(title);
    }

    public void removeBooksBySize(Integer size){
        bookRepo.removeItemsBySize(size);
    }

    public List<Book> getBooksBySearchQuery(String author, String title, Integer size, String AO){
        return bookRepo.getAllBySearchQuery(author, title, size, AO);
    }
}
