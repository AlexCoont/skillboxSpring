package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    void removeItemsByAuthor(String authorNameToRemove);

    void removeItemsByTitle(String title);

    void removeItemsBySize(Integer size);

    List<T> getAllBySearchQuery(String author, String title, Integer size, Boolean useAnd);

}
