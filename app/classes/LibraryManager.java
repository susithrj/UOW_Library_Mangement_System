package classes;
import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.sql.SQLException;

public interface LibraryManager {

    /*Library Manager interface defines all the behaviours of library manager */

    public void addLibraryItem(LibraryItem newItem) throws Exception;
    public void deleteLibraryItem(int isbn) throws SQLException;
    public JsonNode displayItemList();
    public  void lendItem(LogRecord record) throws SQLException;
    public  LogRecord returnItem(LogRecord record);
    public  JsonNode makeReservation(Reservation itemReservation);
    public BigDecimal fineMoneyCalculation(DateTime dueDate, DateTime returnDate);
    public JsonNode searchItem(String searchValue);
    public JsonNode generateReport();
    public void addReader(Reader libraryUser) throws SQLException;
    public JsonNode displayReaders();
    public JsonNode displayReservations();
}
