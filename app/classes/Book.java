package classes;


public class Book extends LibraryItem {

    /*This concrete class is a specialized class of abstract class LibraryItem */

    private int totalPages;
    private String authors[];//array is used to contain authors

    public Book(String sector, int isbn, String title, Reader presentReader) {
        super(sector, isbn, title, presentReader);
    }

    public Book(String sector, int isbn, String title, DateTime lendDate, int totalPages) {
        super(sector, isbn, title, lendDate);
        this.totalPages = totalPages;
    }




    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getTotalPages() {
        return totalPages;
    }



}
