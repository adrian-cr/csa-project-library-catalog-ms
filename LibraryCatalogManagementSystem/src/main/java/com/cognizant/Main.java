package com.cognizant;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.asList;

public class Main {
  private static Object manageCommand(Library library, HashMap<String, Object> response) {//returns Object if command has a return value and true otherwise
    String command = (String) response.get("command");
    String method = (String) response.get("method");
    Object data = response.get("data");
    
    switch (command) {
      case "add":
        library.addBook((Book) data);
        return true;
      case "find":
        System.out.println();
        return library.findBook(method, (String) data);
      case "remove":
        Book removedBook = library.removeBook(method, (String) data);
        if (removedBook!=null) System.out.println("\n**** BOOK REMOVED *****\n");
        return removedBook;
      case "update":
        HashMap<String, Object> updateParams = (HashMap<String, Object>) data;
        library.updateBook(method, (String) updateParams.get("bookId"), (String) updateParams.get("dataType"), updateParams.get("data"));
        System.out.println("\n**** BOOK UPDATED *****\n");
        return true;
      case "view":
        System.out.println();
        return library.viewCatalog((String) data, method);
      
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
            if(parseLong((String)data)<0 || ((String)data).length()!=13)
              return "ISBN code must contain 10 digits.";
            break;
          } catch (NumberFormatException e) {
            return "ISBN code must be numeric.";
          }
        case "title":
        case "genre":
          if (((String) data).length()<2)
            return "Book " + dataType + " is too short.";
          break;
        default:
          return "Invalid data type. Please try again.";
      }//switch
      return null;
    }//try
    catch (Exception e) {
      return "Invalid input. Please try again.";
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
        else {
          System.out.println("Invalid input. Please try again.");
        }
      }//try
      catch (Exception e) {
        System.out.println("Invalid input. Please try again.");
      }//catch
    }//while
  }
  private static HashMap<String, Object> collectData(Library lib, int option) {
    HashMap<String, Object> response = new HashMap<>();
    Book foundBook = null;
    String title;
    String author;
    String isbn;
    String genre;
    String prompt;
    String[] genres;
    int availableCopies;
    int choice;
    switch (option) {
      case 1://add book
        response.put("command", "add");
        response.put("method", null);
        
        System.out.println("Please provide the following details:");
        title = (String) validation("Book title: ", "title", false, null);
        author = (String) validation("Book author: ", "author", false, null);
        isbn = (String) validation("Book ISBN code: ", "isbn", false, null);
        genres = ((String) validation("Book genres:\n*Enter a list of comma-separated values (e.g. \"fantasy,bl,classic\")\n", "genres", false, null)).split(",");
        availableCopies = (int) validation("Available copies: ", "availability", true, null);
        
        response.put("data", new Book(author, availableCopies, new HashSet<>(asList(genres)), isbn, title));
        break;
      case 2://find book
        response.put("command", "find");
        
        System.out.println("How would you like to find the book?");
        System.out.println("1. By title");
        System.out.println("2. By ISBN");
        choice = (int) getInput(true, asList(1, 2));
        
        if (choice == 1) {
          response.put("method", "title");
          title = (String) validation("Please enter book title: ", "title", false, null);
          response.put("data", title);
        } else {
          response.put("method", "isbn");
          isbn = (String) validation("Please enter book ISBN: ", "isbn", false, null);
          response.put("data", isbn);
        }//if-else
        break;
      case 3://remove book
        response.put("command", "remove");

        System.out.println("How would you like to remove the book?");
        System.out.println("1. By title");
        System.out.println("2. By ISBN");
        choice = (int) getInput(true, asList(1, 2));
        
        if (choice == 1) {
          response.put("method", "title");
          title = (String) validation("Please enter book title: ", "title", false, null);
          response.put("data", title);
        } else {
          response.put("method", "isbn");
          isbn = (String) validation("Please enter book ISBN: ", "isbn", false, null);
          response.put("data", isbn);
        }//if-else
        break;
      case 4://update book
        HashMap<String, Object> updateParams = new HashMap<>();
        response.put("command", "update");
        
        System.out.println("How should I find the book to update?");
        System.out.println("1. By title");
        System.out.println("2. By ISBN");
        choice = (int) getInput(true, asList(1, 2));
        
        if (choice == 1) {
          response.put("method", "title");
          title = (String) validation("Please enter book title: ", "title", false, null);
          updateParams.put("bookId", title);
        } else {
          response.put("method", "isbn");
          isbn = (String) validation("Please enter book ISBN: ", "isbn", false, null);
          updateParams.put("bookId", isbn);
        }//if-else
        
        System.out.println("What would you like to update?");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Genres");
        System.out.println("4. Copies available");
        choice = (int) getInput(true, asList(1, 2, 3, 4));
        
        String dataType = choice==1? "title" : choice==2? "author" : choice==3? "genres" : "availability";
        updateParams.put("dataType", dataType);
        
        prompt = "Please enter new " + dataType + (dataType.equals("genres")? " (must be comma-separated): " : ": ");
        Object data = validation(prompt, dataType, dataType.equals("availability"), null);
        updateParams.put("data", data);
        
        response.put("data", updateParams);
        break;
      case 5://view catalog
        response.put("command", "view");
        
        System.out.println("How should the catalog be displayed?");
        System.out.println("1. Alphabetically ordered by title");
        System.out.println("2. Alphabetically ordered by author");
        choice = (int) getInput(true, asList(1, 2));
        
        response.put("method", choice==1? "title" : "author");
        
        System.out.println("Would you like to filter the catalog by genre?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        choice = (int) getInput(true, asList(1, 2));
        
        if (choice==1) {
          prompt = "Please enter a genre. Available genres: ";
          Iterator<String> genreIter = lib.getExistingGenres().iterator();
          while (genreIter.hasNext()) prompt += genreIter.next() + (genreIter.hasNext()? ", " : ".\n");
          genre = (String) validation(prompt, "genre", false, new ArrayList<>(lib.getExistingGenres()));
          response.put("data", genre);
        }//if
    }//switch
    
    return response;
  }//collectData()
  
  
  
  
  public static void main(String[] args) throws InterruptedException {
    Library library = new Library();
    library.addBook(new Book("Harper Lee", 2, new HashSet<>(asList("novel", "diversity", "classic")), "9780718076375", "To Kill a Mockingbird"));
    library.addBook(new Book("John Katzenbach", 7, new HashSet<>(asList("novel", "thriller", "suspense")), "9780802125583", "The Dead Student"));
    library.addBook(new Book("Claire Douglas", 10, new HashSet<>(asList("novel", "thriller")), "9783328105954", "Still Alive"));
    library.addBook(new Book("Brandon Sanderson", 3, new HashSet<>(asList("novel", "fantasy", "juvenile")), "9781250318541", "Mistborn"));
    library.addBook(new Book("Rick Riordan", 5, new HashSet<>(asList("novel", "fantasy", "juvenile")), "9780718076375", "Percy Jackson and the Olympians"));
    library.addBook(new Book("Antoine de Saint-Exupery", 1, new HashSet<>(asList("short story", "fantasy", "juvenile", "classic")), "9786079593063", "The Little Prince"));
    
    
    System.out.println("\n***** Library Catalog Management System *****\n".toUpperCase());
    System.out.print("Welcome! ");
    while (true) {
      System.out.println("What would you like to do?\n");
      System.out.println("1. Add book to library");
      System.out.println("2. Find book in library");
      System.out.println("3. Remove book from library");
      System.out.println("4. Update book in library");
      System.out.println("5. View library catalog");
      System.out.println("6. Exit");
      int option = (int) getInput(true, asList(1, 2, 3, 4, 5, 6));
      if (option==6) break;
      HashMap<String, Object> response = collectData(library, option);
      Object result = manageCommand(library, response);
      if (result!=null)
        try {
          if ((boolean) result)
            System.out.println("SUCCESS!");
        }//try
        catch (Exception e) {
          System.out.println(result);
        }//catch
      else
        System.out.println("***** NO RESULTS :( *****\n");
      Thread.sleep(2000);
      System.out.println();
    }
    System.out.println("\n\nThank you for using our management system. Goodbye! :)\n");
    Thread.sleep(3000);
    
    
  }
}