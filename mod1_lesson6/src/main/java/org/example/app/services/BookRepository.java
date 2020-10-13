package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (bookIdToRemove.equals(book.getId())) {
                logger.info("remove book by id completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public void removeItemsByAuthor(String authorNameToRemove) {
        for (Book book : retreiveAll()){
            if (authorNameToRemove.equals(book.getAuthor())){
                logger.info("remove book by author completed: " + book);
                repo.remove(book);
            }
        }
    }

    @Override
    public void removeItemsByTitle(String title) {
        for (Book book : retreiveAll()){
            if (title.equals(book.getTitle())){
                logger.info("remove book by title completed: " + book);
                repo.remove(book);
            }
        }
    }

    @Override
    public void removeItemsBySize(Integer size) {
        for (Book book : retreiveAll()){
            if (size.equals(book.getSize())){
                logger.info("remove book by size completed: " + book);
                repo.remove(book);
            }
        }
    }

    @Override
    public List<Book> getAllBySearchQuery(String author, String title, Integer size, String AO) {
        List<Book> searchBooks = new ArrayList<>();
        if ("on".equals(AO)){
            for (Book book : retreiveAll()) {
                if (!StringUtils.isEmpty(author) && !StringUtils.isEmpty(title) && null != size) {
                    if (author.equals(book.getAuthor())
                            && title.equals(book.getTitle())
                            && size.equals(book.getSize())) {
                        searchBooks.add(book);
                    }
                }
            }
            return searchBooks;
        }
        for (Book book : retreiveAll()){

            if (!StringUtils.isEmpty(author) && author.equals(book.getAuthor())){
                searchBooks.add(book);
            }
            if (!StringUtils.isEmpty(title) && author.equals(book.getTitle())){
                searchBooks.add(book);
            }
            if (null != size && size.equals(book.getSize())){
                searchBooks.add(book);
            }

        }
        return searchBooks;
    }
}
