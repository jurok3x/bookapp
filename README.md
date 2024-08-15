# Library Management System

## Overview

The Library Management System is designed to manage books and members in a library. It provides functionality for borrowing and returning books, retrieving information about books and members, and handling various library operations.

## Features

- **Manage Books**: Add, update, delete, and retrieve book information.
- **Manage Members**: Add, update, delete, and retrieve member information.
- **Borrowing and Returning Books**: Allows members to borrow and return books, with constraints on availability and borrowing limits.
- **Book Queries**: Retrieve books borrowed by a specific member, distinct borrowed book titles, and the count of borrowed copies for each book.

## Setup and Installation

### Prerequisites

- Java 17 or higher
- Maven
- A PostgreSQL database

### Clone the Repository

```bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
```

### Database Configuration
Make sure you added **DB_URL**, **DB_USER** and **DB_PASSWORD** environment variables before run. Use *dev* profile.

## Endpoints
1. **POST** api/v1/books : Creates a new book and saves it to the database if the title and author are valid. Returns the created book with a 201 Created status.

2. **GET** api/v1/books/{id} : Retrieves a specific book by its ID. Returns the book details if found, with a 200 OK status. If the book is not found, returns a 404 Not Found status.

3. **GET** api/v1/books : Retrieves a list of all books in the system. Returns a list of books with a 200 OK status.

4. **PUT** api/v1/books/{id} : Updates the details of an existing book identified by its ID. Returns the updated book with a 200 OK status if successful. If the book is not found or the request is invalid, returns 404 Not Found or 400 Bad Request statuses, respectively.

5. **DELETE** api/v1/books/{id} : Deletes a book identified by its ID. If the book is deleted successfully, returns a confirmation message with a 200 OK status. If the book is not found, returns a 404 Not Found status. If there are issues with the deletion (e.g., the book is currently borrowed), returns a 400 Bad Request status.

6. **POST** api/v1/members : Creates a new member and saves it to the database. Returns the created member with a 201 Created status.

7. **GET** api/v1/members/{id} : Retrieves a specific member by their ID. Returns the member details if found, with a 200 OK status. If the member is not found, returns a 404 Not Found status.

8. **GET** api/v1/members : Retrieves a list of all members in the system. Returns a list of members with a 200 OK status.

9. **PUT** api/v1/members/{id} : Updates the details of an existing member identified by their ID. Returns the updated member with a 200 OK status if successful. If the member is not found or the request is invalid, returns 404 Not Found or 400 Bad Request statuses, respectively.

10. **DELETE** api/v1/members/{id} : Deletes a member identified by their ID. If the member is deleted successfully, returns a confirmation message with a 200 OK status. If the member is not found or has borrowed books, returns a 404 Not Found or 400 Bad Request status, respectively.
11. **POST** api/v1/library/borrow : Allows a member to borrow a book. Requires a request body with BookMemberRequestDTO containing the member's ID and the book's ID. Updates the book and member records. Returns the updated member details with a 200 OK status. Throws a 400 Bad Request if the book is not available or the member has reached their borrowing limit.

12. **POST** api/v1/library/return : Allows a member to return a borrowed book. Requires a request body with BookMemberRequestDTO containing the member's ID and the book's ID. Updates the book and member records. Returns the updated member details with a 200 OK status. Throws a 400 Bad Request if the book was not borrowed by the member.

13. **GET** api/v1/library/borrowed_member : Retrieves all books borrowed by a specific member identified by their name. Requires a name query parameter. Returns a list of borrowed books with a 200 OK status.

14. **GET** api/v1/library/borrowed_titles : Retrieves all distinct book titles that are currently borrowed. Returns a list of distinct book titles with a 200 OK status.

15. **GET** api/v1/library/borrowed_count : Retrieves all distinct book titles that are currently borrowed along with the count of how many copies of each book are borrowed. Returns a list of book titles and counts with a 200 OK status.
