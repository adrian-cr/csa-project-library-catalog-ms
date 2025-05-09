# Project: Library Catalog Management System

This Java project contains the necessary code to run
a library catalog management system which
utilizes the `Collections` framework to create,
read, and update data and allows users to
interact with it through a simple command-line
interface (CLI), which can be run from
the project's `Main` class.

Below are the project's key features:

## Classes
### `Book`
A class for individual books with attributes `title`, `author`, `isbn`, `genre`, and `availableCopies` status.

### `Library`
A class to store and manage `Book` objects.

### `Main`
A class from which the management system is run.
It implements a user-friendly CLI for users to
interact with the `Library` by being able to add,
remove, update, and view `Book` information.

## Data Validation and Integrity
* The system implements validation checks to
ensure the accuracy and adequate formatting of
entered data, and lets users know of any input 
errors by prompting detailed messages.
