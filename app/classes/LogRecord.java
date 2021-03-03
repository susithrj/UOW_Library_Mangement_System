package classes;

import java.math.BigDecimal;
import java.util.Objects;

public class LogRecord implements Comparable{

    /*Comparable interface is implemented to get the comparing behaviour,
      to compare with  another LogRecord object*/

    private DateTime returnDate;
    private int item_isbn;
    private Reader reader;
    private BigDecimal fineFee; //BigDecimal is used to store Fine fee

    public LogRecord(DateTime dateTime, int item_isbn, Reader reader) {
        this.returnDate = dateTime;
        this.item_isbn = item_isbn;
        this.reader = reader;
    }

    public BigDecimal getFineFee() {
        return fineFee;
    }

    public void setFineFee(BigDecimal fineFee) {
        this.fineFee = fineFee;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getItem_isbn() {
        return item_isbn;
    }



    public DateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(DateTime returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {

        /*Equals method is overridden to find the equality of a LogRecord object
          from isbn and reader*/

        if (o == null || getClass() != o.getClass()){
            return false;
            // if class mismatch or null return false
        }
        LogRecord record = (LogRecord) o;//casting to a LOgRecord

        if((this.item_isbn == record.item_isbn) && this.reader.getReaderId() == record.reader.getReaderId()){
            //if item isbn and reader is equal , LogRecord is equal

            return true;
        }else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(item_isbn, reader.getReaderId());
    }



    @Override
    public int compareTo(Object o) {

        /*comparing is done with fineFee,
          if Fine fee is greater the logRecord will
          be taken as greater
         */
        LogRecord log = (LogRecord) o;//casting to a LogRecord object

        if(this.fineFee.doubleValue() > log.fineFee.doubleValue()){
            return 1;
        }else if(this.fineFee.doubleValue() < log.fineFee.doubleValue()) {
            return -1;
        }else {
            return 0;
        }

    }
}

