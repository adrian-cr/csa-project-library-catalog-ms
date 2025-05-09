package com.cognizant;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class Main {
  public static Object manageCommand(Library library, String command, String searchMethod, Object data) {//returns Object if command has a return value and true otherwise
    switch (command) {
      case "add":
        library.addBook((Book) data);
        return true;
      case "find":
        HashMap<String, Object> searchParams = (HashMap<String, Object>) data;//format: {searchMethod=String, bookId=String}
        return library.find((String) searchParams.get("searchMethod"), (String) searchParams.get("id"));
      case "remove":
          return library.remove(searchMethod, (String) data);
      case "update":
        HashMap<String, Object> updateParams = (HashMap<String, Object>) data;//format: {searchMethod=String, bookId=String, dataType=String, data=Object}
          library.update((String) updateParams.get("searchMethod"), (String) updateParams.get("bookId"), (String) updateParams.get("dataType"), updateParams.get("data"));
        return true;
      case "view":
        HashMap<String, Object> viewData = (HashMap<String, Object>) data;//format: {genreFilter:String, sortValue:String}
        return library.viewCatalog((String) viewData.get("genreFilter"), (String) viewData.get("sortValue"));
    }//switch
    return null;
  }//manageCommand()
  
  public static String validateData(String dataType, Object data) {//returns message string if validation fails
    try {
      switch (dataType) {
        case "author":
          int wordCount = ((String) data).split(" ").length;
          if (wordCount<2 || wordCount>5)
            if (wordCount<2)
              return "Author name must include a first name and a last name.";
            else
              return "Author name is too long.";
          break;
        case "availability":
          if(parseInt((String) data)<0)
            return "Value cannot be negative.";
          break;
        case "genre":
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
  public static void main(String[] args) {
    System.out.println("Hello");
  }
}