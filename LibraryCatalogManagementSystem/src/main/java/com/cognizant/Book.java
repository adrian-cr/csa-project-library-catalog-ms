package com.cognizant;

import java.util.*;

public class Book {
  private String author;
  private int availableCopies;
  private final Set<String> genres = new HashSet<>();
  private String isbn; //id field
  private String title;
  
  /* Constructors: */
  public Book(String author, int availableCopies, HashSet<String> genres, String isbn, String title) {
    this.author = author;
    this.availableCopies = availableCopies;
    this.genres.addAll(genres);
    this.isbn = isbn;
    this.title = title;
  }//Book - base constructor
  public Book(Book book) {
    this.author = book.getAuthor();
    this.availableCopies = book.getAvailableCopies();
    this.isbn = book.getISBN();
    this.title = book.getTitle();
  }//Book - cloning constructor
  
  /* Getters: */
  public String getAuthor() {
    return author;
  }//getAuthor()
  public int getAvailableCopies() {
    return availableCopies;
  }//getAvailableCopies()
  public Set<String> getGenres() {
    return genres;
  }//getGenre()
  public String getISBN() {
    return isbn;
  }//getISBN()
  public String getTitle() {
    return title;
  }//getTitle()
  
  /* Setters: */
  public void setAuthor(String author) {
    this.author = author;
  }//setAuthor(
  public void setAvailableCopies(int availableCopies) {
    this.availableCopies = availableCopies;
  }//setAvailableCopies()
  public void setTitle(String title) {
    this.title = title;
  }//setTitle()
  
  /* Custom methods:*/
  public void addGenres(Set<String> genres) {
    genres.addAll(genres);
  }
  
  /* Overridden methods: */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Book book)) return false;
    return isbn.equals(book.isbn);
  }//equals()
  @Override
  public int hashCode() {
    return Objects.hashCode(isbn);
  }//hashCode()
  @Override
  public String toString() {
    String str = "\n  Title: \"" + title +"\"\n" +
            "  Author: " + author + "\n" +
            "  ISBN: " + isbn + "\n" +
            "  Genres: ";
    Iterator<String> genreIter = genres.iterator();
    while (genreIter.hasNext()) str += genreIter.next() + (genreIter.hasNext()? ", " : "");
    str += "\n  Copies available: " + availableCopies + "\n";
    return str;
  }
}
