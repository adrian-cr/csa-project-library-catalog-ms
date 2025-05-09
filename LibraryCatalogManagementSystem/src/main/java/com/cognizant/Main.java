package com.cognizant;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;

public class Main {
  private static Object manageCommand(Library library, HashMap<String, Object> response) {//returns Object if command has a return value and true otherwise
    String command = (String) response.get("command");
    String searchMethod = (String) response.get("searchMethod");
    Object data = response.get("data");
    
    switch (command) {
      case "add":
        library.addBook((Book) data);
        return true;
      case "find":
        HashMap<String, Object> searchParams = (HashMap<String, Object>) data;//format: {searchMethod=String, bookId=String}
        return library.findBook((String) searchParams.get("searchMethod"), (String) searchParams.get("id"));
      case "remove":
          return library.removeBook(searchMethod, (String) data);
      case "update":
        HashMap<String, Object> updateParams = (HashMap<String, Object>) data;//format: {searchMethod=String, bookId=String, dataType=String, data=Object}
          library.updateBook((String) updateParams.get("searchMethod"), (String) updateParams.get("bookId"), (String) updateParams.get("dataType"), updateParams.get("data"));
        return true;
      case "view":
        HashMap<String, Object> viewData = (HashMap<String, Object>) data;//format: {genreFilter:String, sortValue:String}
        return library.viewCatalog((String) viewData.get("genreFilter"), (String) viewData.get("sortValue"));
    }//switch
    return null;
  }//manageCommand()
  
  private static Object validation(String prompt, String dataType, boolean isInputNumber, List inputOptions) {
    System.out.print(prompt);
    Object value = getInput(isInputNumber, inputOptions);
    String errorMessage = validateLibraryData(dataType, value);
    while (errorMessage!=null){
      System.out.println(("\n" + errorMessage + "\n").toUpperCase());
      System.out.print(prompt);
      value = getInput(isInputNumber, inputOptions);
      errorMessage = validateLibraryData(dataType, value);
    }
    return value;
  }
  private static String validateLibraryData(String dataType, Object data) {//returns message string if validation fails
    try {
      switch (dataType) {
        case "author":
          int wordCount = ((String)data).split(" ").length;
          if (wordCount<2 || wordCount>5)
            if (wordCount < 2)
              return "Author name must include a first name and a last name.";
            else
              return "Author name is too long.";
          break;
        case "availability":
          if((int) data < 0)
            return "Value cannot be negative.";
          break;
        case "genres":
          if (((String) data).length() < 3)
            return "Genre list is too short.";
          break;
        case "isbn":
          try {
            if(parseInt((String)data)<0 || ((String)data).length()!=10)
              return "ISBN code must contain 10 digits.";
            break;
          } catch (NumberFormatException e) {
            return "ISBN code must be numeric.";
          }
        case "title":
          if (((String) data).length()<2)
            return "Book " + dataType + " is too short.";
          break;
        default:
          return "Invalid data type. Please try again.";
      }//switch
      return null;
    }//try
    catch (Exception e) {
      return "Invalid data. Please try again.";
    }//catch
  }//validateData()
  private static Object getInput(boolean number, List options) {
    while (true) {
      try {
        Scanner sc = new Scanner(System.in);
        Object input = number? sc.nextInt() : sc.nextLine();
        if(options==null || options.contains(input)) {
          return input;
        }//if
      }//try
      catch (Exception e) {
        System.out.println("Invalid data. Please try again.");
      }//catch
    }//while
  }
  private static HashMap<String, Object> collectData(int option) {
    HashMap<String, Object> response = new HashMap<>();
    String title;
    String author;
    String isbn;
    String[] genres;
    int availableCopies;
    switch (option) {
      case 1://add a new book
        response.put("command", "add");
        response.put("searchMethod", null);
        
        System.out.println("Please provide the following details:");
        title = (String) validation("Book title: ", "title", false, null);
        author = (String) validation("Book author: ", "author", false, null);
        isbn = (String) validation("Book ISBN code: ", "isbn", false, null);
        genres = ((String) validation("Book genres:\n*Enter a list of comma-separated values (e.g. \"fantasy,bl,classic\")\n", "genres", false, null)).split(",");
        availableCopies = (int) validation("Available copies: ", "availability", true, null);
        
        response.put("data", new Book(author, availableCopies, new HashSet<>(asList(genres)), isbn, title));
        break;
      case 2://find a book
        response.put("command", "find");
        System.out.println("How would you like to find your book?");
        System.out.println("1. By title");
        System.out.println("2. By ISBN");
        int choice = (int) getInput(true, asList(1, 2));
        if (choice==1) {
          response.put("searchMethod", "title");
          title = (String) validation("Please enter book title: ", "title", false, null);
          response.put("data", title);
        }
        else {
          response.put("searchMethod", "isbn");
          isbn = (String) validation("Please enter book ISBN: ", "isbn", false, null);
          response.put("data", isbn);
        }
        break;
      case 3:
        response.put("command", "remove");
        break;
      case 4:
        response.put("command", "update");
        break;
      case 5:
        response.put("command", "view");
        break;
      default:
        break;
    }
    return response;
  }
  
  
  public static void main(String[] args) {
    Library library = new Library();
    Scanner sc = new Scanner(System.in);
    System.out.println("\n*****Library Catalog Management System*****\n".toUpperCase());
    System.out.println("Hello. What would you like to do today?\n");
    System.out.println("1. Add book to library");
    System.out.println("2. Find book in library");
    System.out.println("3. Update book in library");
    System.out.println("4. Remove book from library");
    System.out.println("5. View library catalog");
    
    int option = (int) getInput(true, asList(1, 2, 3, 4, 5));
    HashMap<String, Object> response = collectData(option);
    manageCommand(library, response);
    System.out.println(library.toString());
  }
}