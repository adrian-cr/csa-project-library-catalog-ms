package com.cognizant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Library {
  public final Set<Book> catalog = new HashSet<>();
  public final Set<String> existingGenres = new HashSet<>();
  
  public Library() {
  }
  
  /* Getters: */
  public Set<Book> getCatalog() {
    return catalog;
  }
  
  /* Custom methods:*/
  public void add(Book book) {
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
  public Book findByISBN(String isbn) {
    for (Book book : catalog) {
      if (book.getISBN().equals(isbn))
        return book;
    }//for
    return null;
  }//findByISBN()
  public Book findByTitle(String title) {
    for (Book book : catalog) {
      if (book.getTitle().equals(title))
        return book;
    }//for
    return null;
  }//findByTitle()
  public Set<Book> getCatalogByGenre(String genre) {
    Set<Book> filteredCatalog = new HashSet<>();
    if (existingGenres.contains(genre)) {
      for (Book book : catalog) {
        if (book.getGenres().contains(genre))
          filteredCatalog.add(book);
      }//for
    }//if
    return filteredCatalog;
  }//getCatalogByGenre()
  public void increaseAvailability(Book book) {
    book.setAvailableCopies(book.getAvailableCopies()+1);
  }//increaseAvailability()
  public Book removeByISBN(String isbn) {
    for (Book book : catalog) {
      if (book.getISBN().equals(isbn)) {
        Book target = new Book(book);
        catalog.remove(book);
        return target;
      }//if
    }//for
      return null;
  }//removeByISBN()
  public Book removeByTitle(String title) {
    for (Book book : catalog) {
      if (book.getTitle().equals(title)) {
        Book target = new Book(book);
        catalog.remove(book);
        return target;
      }//if
    }//for
    return null;
  }//removeByTitle()
  public void updateByISBN(String isbn, String dataType, Object data) {
    Book target =findByISBN(isbn);
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
  }//updateByISBN()
  public void updateByTitle(String title, String dataType, Object data) {
    Book target =findByISBN(title);
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
  }//updateByTitle()
  
}
