package classes;

public class Reservation {

    private DateTime collectionDate;
    private Reader resReader;
    private int item_isbn;

    public Reservation(DateTime collectionDate, Reader resReader, int item_isbn) {
        this.collectionDate = collectionDate;
        this.resReader = resReader;
        this.item_isbn = item_isbn;
    }

    public Reservation(Reader resReader, int item_isbn) {
        this.resReader = resReader;
        this.item_isbn = item_isbn;
    }

    public int getItem_isbn() {
        return item_isbn;
    }



    public DateTime getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(DateTime collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Reader getResReader() {
        return resReader;
    }

    public void setResReader(Reader resReader) {
        this.resReader = resReader;
    }


}
