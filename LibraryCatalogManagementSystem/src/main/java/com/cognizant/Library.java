package com.cognizant;

import java.util.*;

public class Library {
  private final Set<Book> catalog = new HashSet<>();
  private final Set<String> existingGenres = new HashSet<>();
  
  public Library() {}
  
  /* Getters: */
  
  public Set<String> getExistingGenres() {
    return existingGenres;
  }
  
  /* Custom methods:*/
  public void addBook(Book book) {
    existingGenres.addAll(book.getGenres());
    catalog.add(book);
  }//addBook()
  public Book findBook(String searchMethod, String id) {
    for (Book book : catalog) {
      String bookId = searchMethod.equals("isbn") ? book.getISBN() : book.getTitle();
      if (bookId.equalsIgnoreCase(id)) return book;
    }
    return null;
  }//findBook()
  public Book removeBook(String searchMethod, String id) {
    Book target = findBook(searchMethod, id);
    if (target!=null) {
      Book targetCopy = new Book(target);
      catalog.remove(target);
      return targetCopy;
    }//if
    return null;
  }//removeBook()
  public void updateBook (String searchMethod, String id, String dataType, Object data) {
    Book target = findBook(searchMethod, id);
    if (target!=null) {
      switch (dataType) {
        case "availability":
          target.setAvailableCopies((int) data);
          break;
        case "author":
          target.setAuthor((String) data);
          break;
        case "genre":
          target.addGenres((Set<String>) data);
          existingGenres.addAll((Set<String>) data);
          break;
        case "title":
          target.setTitle((String)data);
          break;
      }//switch
    }//if
  }//updateBook()
  
  public Set<Book> filterCatalog(String genre) {
    Set<Book> filteredCatalog = new HashSet<>();
    if (existingGenres.contains(genre)) {
      for (Book book : catalog) {
        if (book.getGenres().contains(genre))
          filteredCatalog.add(book);
      }//for
    }//if
    return filteredCatalog;
  }//filterCatalog()
  public List<Book> viewCatalog(String genreFilter, String sortValue) {
    Set<Book> catalogSet = catalog;
    if(genreFilter!=null)
      catalogSet = filterCatalog(genreFilter);
    List<Book> sortedList = new ArrayList<>(catalogSet);
    if (sortValue.equalsIgnoreCase("title"))
      sortedList.sort((Book a, Book b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
    else if (sortValue.equalsIgnoreCase("author"))
      sortedList.sort((Book a, Book b) -> a.getAuthor().compareToIgnoreCase(b.getAuthor()));
    return sortedList;
  }//viewCatalog()
  
  @Override
  public String toString() {
    List<Book> sortedCatalog = viewCatalog(null, "title");
    if (!sortedCatalog.isEmpty()) {
      String str= "[";
      
      for (Book book : sortedCatalog)
        str += book.toString() + "\n\n";
      str+="]";
      return str;
    }
    return "[]";
    
  }
}//Library
