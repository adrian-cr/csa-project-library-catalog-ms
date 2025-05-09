package com.cognizant;

import java.util.*;

public class Library {
  private final Set<Book> catalog = new HashSet<>();
  private final Set<String> existingGenres = new HashSet<>();
  
  public Library() {
  
  }
  
  
  /* Getters: */
  public Set<Book> getCatalog() {
    return catalog;
  }
  
  /* Custom methods:*/
  public void addBook(Book book) {
    int prevSize = catalog.size();
    catalog.add(book);
    int newSize = catalog.size();
    if (prevSize==newSize) {
      increaseAvailability(book);
    }
  }//add()
  public void decreaseAvailability(Book book) {
    book.setAvailableCopies(book.getAvailableCopies()+1);
  }//decreaseAvailability()
  public Book find(String searchMethod, String id) {
    for (Book book : catalog) {
      String bookId = searchMethod.equals("isbn") ? book.getISBN() : book.getTitle();
      if (bookId.equals(id)) return book;
    }
    return null;
  }//find()
  public Set<Book> filterCatalogByGenre(String genre) {
    Set<Book> filteredCatalog = new HashSet<>();
    if (existingGenres.contains(genre)) {
      for (Book book : catalog) {
        if (book.getGenres().contains(genre))
          filteredCatalog.add(book);
      }//for
    }//if
    return filteredCatalog;
  }//filterCatalogByGenre()
  public void increaseAvailability(Book book) {
    book.setAvailableCopies(book.getAvailableCopies()+1);
  }//increaseAvailability()
  public Book remove(String searchMethod, String id) {
    Book target = find(searchMethod, id);
    if (target!=null) {
      Book targetCopy = new Book(target);
      catalog.remove(target);
      return targetCopy;
    }
//    if (searchMethod.equals("isbn"))
//      for (Book book : catalog) {
//        if (book.getISBN().equals(id)) {
//          Book target = new Book(book);
//          catalog.remove(book);
//          return target;
//        }//if
//      }//for
//    else if (searchMethod.equals("title"))
//      for (Book book : catalog)
//        if (book.getTitle().equals(id)) {
//          Book target = new Book(book);
//          catalog.remove(book);
//          return target;
//        }//if
    return null;
  }//remove()
  public List<Book> viewCatalog(String genreFilter, String sortValue) {
    Set<Book> catalogSet = catalog;
    if(genreFilter!=null)
      catalogSet = filterCatalogByGenre(genreFilter);
    List<Book> sortedList = new ArrayList<>(catalogSet);
    if (sortValue.equalsIgnoreCase("title"))
      sortedList.sort((Book a, Book b) -> {return a.getTitle().compareToIgnoreCase(b.getTitle());});
    else if (sortValue.equalsIgnoreCase("author"))
      sortedList.sort((Book a, Book b) -> {return a.getAuthor().compareToIgnoreCase(b.getAuthor());});
    return sortedList;
  }//sortCatalogBy
  public void update(String searchMethod, String id, String dataType, Object data) {
    Book target = find(searchMethod, id);
    if (target!=null) {
      switch (dataType) {
        case "availability":
          target.setAvailableCopies((int) data);
          break;
        case "author":
          target.setAuthor((String) data);
          break;
        case "genre":
          target.addGenre((String) data);
          existingGenres.add((String) data);
          break;
        case "title":
          target.setTitle((String)data);
          break;
      }//switch
    }//if
  }//update()
  
  
}//Library
