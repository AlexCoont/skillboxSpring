package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(String bookIdToRemove) {
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
    public List<Book> getAllBySearchQuery(String author, String title, Integer size, Boolean useAnd) {
        if (useAnd){
            return retreiveAll().stream().filter(book -> !StringUtils.isEmpty(author) && author.equals(book.getAuthor()))
                .filter(book -> !StringUtils.isEmpty(title) && title.equals(book.getTitle()))
                .filter(book -> size != null && size.equals(book.getSize()))
                .collect(toList());
        }
        return getAllWithOr(author, title, size);
    }

    private List<Book> getAllWithOr(String author, String title, Integer size){

        return retreiveAll().stream().filter(book -> !StringUtils.isEmpty(author) && author.equals(book.getAuthor()) ||
                !StringUtils.isEmpty(title) && title.equals(book.getTitle()) ||
                null != size && size.equals(book.getSize())).collect(toList());

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
