package classes;
import java.util.Objects;

public abstract class LibraryItem {

    private DateTime lendDate;
    private DateTime issueDate;
    private  String sector;
    private int isbn;
    private String title;
    private Reader presentReader;




    public LibraryItem(String sector, int isbn, String title, DateTime issueDate) {
        this.sector = sector;
        this.isbn = isbn;
        this.title = title;
        this.issueDate = issueDate;
    }


    public LibraryItem(String sector, int isbn, String title, Reader presentReader) {
        this.sector = sector;
        this.isbn = isbn;
        this.title = title;
        this.presentReader = presentReader;
    }




    public DateTime getIssueDate() {
        return issueDate;
    }


    public DateTime getLendDate() {
        return lendDate;
    }

    public void setLendDate(DateTime lendDate) {
        this.lendDate = lendDate;
    }

    public String getSector() {
        return sector;
    }

    public void setPresentReader(Reader presentReader) {
        this.presentReader = presentReader;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Reader getPresentReader() {
        return presentReader;
    }



    @Override
    public boolean equals(Object o) {

        /*Equals method is overridden to find the equality of a Library item object
          from title, mainly used in the search function*/

        if (o == null || getClass() != o.getClass()){
            // if class mismatch or null return false
            return false;
        }
        LibraryItem item = (LibraryItem) o;
        //equality will be considering from title
        return Objects.equals(title, item.title);
    }

    @Override
    public int hashCode() {
        /* hashcode is overridden to access objects quickly
           title is considered when overriding hashcode
         */
        return Objects.hash(title);
    }
}
