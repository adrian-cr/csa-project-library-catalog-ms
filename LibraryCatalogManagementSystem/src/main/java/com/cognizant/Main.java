package com.cognizant;

import static java.lang.Integer.parseInt;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public String validateData(String dataType, Object data) {//returns message string if validation fails
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
    
  }
}