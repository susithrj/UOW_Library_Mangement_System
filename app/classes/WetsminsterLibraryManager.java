package classes;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import services.DatabaseConnector;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class WetsminsterLibraryManager implements LibraryManager {

    @Override
    public void addLibraryItem(LibraryItem newItem) throws Exception {
        //thrown exceptions are handled in Home controller

      if(newItem instanceof Book){
          /*If the item is a  book instance it will add the relevant book  data  to database*/
          ResultSet bookCount  =DatabaseConnector.getData("item  WHERE type = 1","COUNT(isbn)");

          if(bookCount.next() && bookCount.getInt("COUNT(isbn)") <= 100){
              //Item will be only added if there is only free spaces

              String bookInsertStatment = "INSERT into item (isbn,title,sector,pages,issueDate,type) " +
                      "VALUES (?,?,?,?,?,1)";
              String authorInsertStatment = "INSERT into author (id,name)" +
                      "VALUES (?,?)";

              try {
                  //adding book to database
                  Connection dbConn = DatabaseConnector.connectToDatabase();
                  PreparedStatement stm = dbConn.prepareStatement(bookInsertStatment, Statement.RETURN_GENERATED_KEYS);
                  stm.setInt(1,newItem.getIsbn());
                  stm.setString(2,newItem.getTitle());
                  stm.setString(3,newItem.getSector());
                  stm.setInt(4,((Book) newItem).getTotalPages());
                  stm.setDate(5,newItem.getIssueDate().getSqlDate());
                  stm.executeUpdate();

                  //adding authors of the book to database
                  Connection dbCon = DatabaseConnector.connectToDatabase();
                  PreparedStatement stat  = dbCon.prepareStatement(authorInsertStatment, Statement.RETURN_GENERATED_KEYS);
                  for (int i = 0;i < ((Book) newItem).getAuthors().length ;i++){
                      stat.setInt(1,newItem.getIsbn());
                      stat.setString(2,((Book) newItem).getAuthors()[i]);
                      stat.addBatch();
                  }
                  stat.executeBatch();


              }catch (SQLIntegrityConstraintViolationException keyFails){
                  //throwing exception if primary key fails
                  throw keyFails;


              } catch (SQLException ex) {

                  throw ex;

              }
          }else {
              //exception if library storage is full
              throw new Exception("Running Out of Storage");
          }


      }else {
          /*If the item is a  dvd instance it will add the relevant dvd  data  to database*/
          ResultSet dvdCount  =DatabaseConnector.getData("item  WHERE type = 0","COUNT(isbn)");
          if(dvdCount.next() && dvdCount.getInt("COUNT(isbn)") <= 50){
              //Item will be only added if there is only free spaces

              String dvdInsertStatement = "INSERT into item (isbn,title,sector,producer,duration,type) " +
                      "VALUES (?,?,?,?,?,0)";
              String actorInsertStatment = "INSERT into actor (id,name)" +
                      "VALUES (?,?)";
              String languageInsertStatment = "INSERT into language (id,name)" +
                      "VALUES (?,?)";
              String subtitleInsertStatment = "INSERT into subtitle (id,name)" +
                      "VALUES (?,?)";
              try {
                  //adding dvd to database
                  Connection dbConn = DatabaseConnector.connectToDatabase();
                  PreparedStatement stm = dbConn.prepareStatement(dvdInsertStatement, Statement.RETURN_GENERATED_KEYS);
                  stm.setInt(1,newItem.getIsbn());
                  stm.setString(2,newItem.getTitle());
                  stm.setString(3,newItem.getSector());
                  stm.setString(4,((DVD) newItem).getProducer());
                  stm.setInt(5,((DVD) newItem).getDuration());
                  stm.executeUpdate();


                  //adding actors of the dvd to database
                  Connection connect = DatabaseConnector.connectToDatabase();
                  PreparedStatement statActor  = connect.prepareStatement(actorInsertStatment, Statement.RETURN_GENERATED_KEYS);
                  for (int i = 0;i < ((DVD) newItem).getActors().length ;i++){
                      statActor.setInt(1,newItem.getIsbn());
                      statActor.setString(2,((DVD) newItem).getActors()[i]);
                      statActor.addBatch();

                  }
                  statActor.executeBatch();

                  //adding languages of the dvd to database
                  Connection dbCon = DatabaseConnector.connectToDatabase();
                  PreparedStatement stat  = dbCon.prepareStatement(languageInsertStatment, Statement.RETURN_GENERATED_KEYS);
                  for (int i = 0;i < ((DVD) newItem).getLanguages().length ;i++){
                      stat.setInt(1,newItem.getIsbn());
                      stat.setString(2,((DVD) newItem).getLanguages()[i]);
                      stat.addBatch();

                  }
                  stat.executeBatch();

                  //adding subtitles of the dvd to database
                  Connection database = DatabaseConnector.connectToDatabase();
                  PreparedStatement subStm  = database.prepareStatement(subtitleInsertStatment, Statement.RETURN_GENERATED_KEYS);
                  for (int i = 0;i < ((DVD) newItem).getSubtitles().length ;i++){
                      subStm.setInt(1,newItem.getIsbn());
                      subStm.setString(2,((DVD) newItem).getSubtitles()[i]);
                      subStm.addBatch();

                  }
                  subStm.executeBatch();

              }catch (SQLIntegrityConstraintViolationException keyFail){
                  System.out.println("DVD already exist");
                  throw keyFail;
                  //duplicate primary key exception is handled in home controller

              } catch (SQLException ex) {

                  throw ex;

              }
          }else {
              //exception if library storage is full
              throw new Exception("Running Out of Storage");
          }

      }
    }

    @Override
    public void deleteLibraryItem(int isbn) throws SQLException {

        //sql code to delete Item
        String itemDeleteQuary = "DELETE FROM item WHERE isbn= ?;";
        try {
            //deleting Item
            Connection dbConn = DatabaseConnector.connectToDatabase();
            PreparedStatement stm = dbConn.prepareStatement(itemDeleteQuary, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1,isbn);
            if(stm.executeUpdate() == 0){
                //if process fails throwing an exception
                throw new SQLException();
            }

        } catch (SQLException e) {
            //throwing exception will be handled in home controller
            throw e;

        }

    }

    @Override
    public JsonNode displayItemList() {

        //returning itemDetails in jsonFormat to the Controller
        return Json.toJson(getItemDetails());



    }


    @Override
    public void lendItem(LogRecord record) throws SQLException {

                try {
                    //getting the requested item type from database
                     ResultSet typeResult = DatabaseConnector.getData("item WHERE isbn =" +
                             String.valueOf(record.getItem_isbn()) ,"type" );

                     typeResult.next();
                     int type = typeResult.getInt("type");

                     String sql = "INSERT into logrecord (isbn,return_date,readerID,lend_date) " +
                            "VALUES (?,?,?,?)";

                     //making a record about the lend
                    Connection con = DatabaseConnector.connectToDatabase();
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1,record.getItem_isbn());
                    statement.setTimestamp(4,record.getReturnDate().getSqlDateTime());
                    if(type == 0){
                        //if its a dvd return date is from three days
                        statement.setTimestamp(2,record.getReturnDate().addDays(3).getSqlDateTime());
                    }else {
                        //if its a book return date is from 7 days
                        statement.setTimestamp(2,record.getReturnDate().addDays(7).getSqlDateTime());
                    }

                    statement.setInt(3,record.getReader().getReaderId());
                    statement.executeUpdate();

                     //updating that this item is given to the particular reader
                    String setReaderStm = " UPDATE item  SET taken = ? WHERE isbn = ?";
                    Connection dbConn = DatabaseConnector.connectToDatabase();
                    PreparedStatement setReader = dbConn.prepareStatement(setReaderStm, Statement.RETURN_GENERATED_KEYS);
                    setReader.setInt(1,record.getReader().getReaderId());
                    setReader.setInt(2,record.getItem_isbn());
                    setReader.executeUpdate();

                }catch (SQLIntegrityConstraintViolationException fail){
                    throw fail;
                    //throwing an exception if primary key fails(handled in home controller )
                } catch (SQLException ex) {

                    throw  ex;

                }

    }

    @Override
    public LogRecord returnItem(LogRecord record) {

        try {
            //getting data for relevant ISBN
            ResultSet logData = DatabaseConnector.getData("logrecord WHERE isbn =" +
                    String.valueOf(record.getItem_isbn()),"*");
             if(logData.next()) {

                 DateTime lendDate = new DateTime(logData.getTimestamp("lend_date"));
                 LogRecord existingRecord = new LogRecord(new DateTime(logData.getTimestamp("return_date")),
                         logData.getInt("isbn"), new Reader(logData.getInt("readerID")));
                 //checking the request with existing records
                 System.out.println(existingRecord.equals(record));
                 if (existingRecord.equals(record)) {

                     //compare the return date and calculate fine if not given to the deadline
                     if (existingRecord.getReturnDate().compareTo(record.getReturnDate()) < 1) {
                         existingRecord.setFineFee(fineMoneyCalculation(existingRecord.getReturnDate(), record.getReturnDate()));

                     }

                  //delete log record from database
                 DatabaseConnector.deleteData("logrecord","isbn",String.valueOf(existingRecord.getItem_isbn()));

                 String addHistoryStm = "INSERT INTO history (isbn,days) VALUES (?,?);";

                 //Recording history to make reservations
                 Connection dbConn = DatabaseConnector.connectToDatabase();
                 PreparedStatement stm = dbConn.prepareStatement(addHistoryStm, Statement.RETURN_GENERATED_KEYS);
                 stm.setInt(1,existingRecord.getItem_isbn());
                 stm.setInt(2,((int)(record.getReturnDate().getDifferenceInMinutes(lendDate)/1440)));
                 stm.executeUpdate();

                 //updating that the book is free
                 String setReaderStm = " UPDATE item  SET taken = ? WHERE isbn = ?";
                 Connection logDatabase = DatabaseConnector.connectToDatabase();
                 PreparedStatement setReader = dbConn.prepareStatement(setReaderStm, Statement.RETURN_GENERATED_KEYS);
                 setReader.setInt(1,0);
                 setReader.setInt(2,record.getItem_isbn());
                 setReader.executeUpdate();



                 return existingRecord;
                 }else {
                     throw new NullPointerException();
                     //thrown exception is handled in home controller
                 }

             }else {
                 throw new NullPointerException();
                 //thrown exception is handled in home controller
             }




        } catch (SQLException e) {


            return null;
        }
    }

    @Override
    public JsonNode makeReservation(Reservation itemReservation) {
        //sql code to add reservation
        String resInsertStatment = "INSERT into reservation (readerID,isbn,res_date,collection_date) " +
                "VALUES (?,?,?,?)";
        //sql code to find the item is lended or not
         ResultSet logData = DatabaseConnector.getData("logrecord WHERE isbn ="+
                 String.valueOf(itemReservation.getItem_isbn()),"COUNT(isbn)");
         //sql code to get number of reservations that has done
         ResultSet reserveData = DatabaseConnector.getData("reservation WHERE isbn ="+
                 String.valueOf(itemReservation.getItem_isbn()),"COUNT(isbn)");
         try{
             //checking if the book is lend or not
             if(logData.next() &&  logData.getInt("COUNT(isbn)") != 0 && reserveData.next()){
                 ResultSet logRecord = DatabaseConnector.getData("logrecord","lend_date,return_date");
                 logRecord.next();
                 DateTime expectedReturn = new DateTime(logRecord.getTimestamp("return_date"));
                 DateTime lendDate = new DateTime(logRecord.getTimestamp("lend_date"));


                 ResultSet history = DatabaseConnector.getData("history WHERE isbn=" +
                         String.valueOf(itemReservation.getItem_isbn()),"AVG(days)");
                 history.next();
                 int days = history.getInt("AVG(days)");//getting average days of borrowers
                 //getting number of reservations for a particular item
                 int resCount =   reserveData.getInt("COUNT(isbn)");
                  if(resCount == 0){
                      //if zero reservations
                      DateTime predictedDate = lendDate.addDays(days);
                      if(!(new DateTime().compareTo(expectedReturn) > 0)){
                          //if the item is not over due
                          if(predictedDate.compareTo(new DateTime()) > 0){
                              /*if the predicted date is larger than current date
                              predicted date will be collection date
                               */
                              itemReservation.setCollectionDate(predictedDate);

                          }else{
                              /*if predicted date is smaller
                              return date will be collection date
                               */
                              itemReservation.setCollectionDate(expectedReturn);

                          }
                          //record reservation in database
                          Connection logToDatabase = DatabaseConnector.connectToDatabase();
                          PreparedStatement stm = logToDatabase.prepareStatement(resInsertStatment, Statement.RETURN_GENERATED_KEYS);
                          stm.setInt(1,itemReservation.getResReader().getReaderId());
                          stm.setInt(2,itemReservation.getItem_isbn());
                          stm.setDate(3,new DateTime().getSqlDate());
                          stm.setDate(4,itemReservation.getCollectionDate().getSqlDate());
                          stm.executeUpdate();
                          return Json.toJson(itemReservation);
                      }else {
                          //-1 is returned as json if item is overdue

                          return Json.toJson(-1);
                      }

                  }else {
                      //if more than 0 reservations done , summing up the date for the next reader
                      itemReservation.setCollectionDate(lendDate.addDays(days * (resCount + 1)));

                      //saving reservation in database
                      Connection logToDatabase = DatabaseConnector.connectToDatabase();
                      PreparedStatement stm = logToDatabase.prepareStatement(resInsertStatment, Statement.RETURN_GENERATED_KEYS);
                      stm.setInt(1,itemReservation.getResReader().getReaderId());
                      stm.setInt(2,itemReservation.getItem_isbn());
                      stm.setDate(3,new DateTime().getSqlDate());
                      stm.setDate(4,itemReservation.getCollectionDate().getSqlDate());
                      stm.executeUpdate();

                      return Json.toJson(itemReservation);
                  }
             }else {

                 return Json.toJson(0);
                 //0 if the item is not taken by any reader
             }

         } catch (SQLException e) {
             //-2 for database error

             return Json.toJson(-2);
         }


    }

    @Override
    public BigDecimal fineMoneyCalculation(DateTime dueDate, DateTime returnDate) {
        long totalMinutes = returnDate.getDifferenceInMinutes(dueDate);
        //getting date difference in minutes
        if(totalMinutes < 0){
            //if no need to calculate fine fee return 0 as big decimal
            return new BigDecimal(0,MathContext.DECIMAL32);
        }else {
            if(dueDate.addDays(3).compareTo(returnDate)>= 0){
                //if return within 3 days after return date 0.2 per hour
                return new BigDecimal(((totalMinutes/60) * 0.2),MathContext.DECIMAL32);
            }else{
                //else after 3 days 0.3 per hour
                return new BigDecimal(((totalMinutes/60 - 36) * 0.3 + 14.4), MathContext.DECIMAL32) ;
            }
        }



    }

    @Override
    public JsonNode searchItem(String searchValue) {
        ArrayList resultList = new ArrayList<LibraryItem>();
        ArrayList<LibraryItem> searchList =  getItemDetails();
        for(int i = 0;i < searchList.size();i++){
            //getting the title in parts
            String title[] = searchList.get(i).getTitle().split(" ");
            for (int j = 0;j < title.length ;j++){
                /*if any match is there for a part in lower or upper
                case the item is added to result array
                 */
                if(title[j].toLowerCase().equals(searchValue.toLowerCase())){
                    resultList.add(searchList.get(i));
                    break;
                }
            }
        }
        return Json.toJson(resultList);
    }

    @Override
    public JsonNode generateReport() {
        ArrayList<LogRecord> records = new ArrayList<LogRecord>();
        ArrayList<LogRecord> recordSorted = new ArrayList<LogRecord>();
        //getting data from database
        ResultSet logData = DatabaseConnector.getData("logrecord","return_date,isbn,readerID");
        try {
            for(int i = 0;logData.next();i++) {//if data is not empty adding to list
                records.add(i,new LogRecord(new DateTime(logData.getTimestamp("return_date")),logData.getInt("isbn"),
                        new Reader(logData.getInt("readerID"))));
            }
            for(int j = 0 ;j < records.size() ;j++ ){
                //calculating fine money for all records
                records.get(j).setFineFee(fineMoneyCalculation(records.get(j).getReturnDate(),new DateTime()));
            }
            //putting the list in descending order(comparedto is implemented)

            Collections.sort(records);
            for (int j = 0 ;j < records.size();j++){
                recordSorted.add(j,records.get(records.size()-1-j));
            }

            return Json.toJson(recordSorted);
            //sending the list in json format
        } catch (SQLException e) {
            return Json.toJson(-1);
            //-1 if database error
        }

    }

    @Override
    public void addReader(Reader libraryUser) throws SQLException {
        String readerInsertStatment = "INSERT INTO reader (name,mobileNumber,email) " +
                "VALUES (?,?,?)";
        Connection dbConn = DatabaseConnector.connectToDatabase();
        PreparedStatement stm = dbConn.prepareStatement(readerInsertStatment, Statement.RETURN_GENERATED_KEYS);

        stm.setString(1,libraryUser.getName());
        stm.setInt(2,libraryUser.getMobileNumber());
        stm.setString(3,libraryUser.getEmail());
        //adding user to  system
        stm.executeUpdate();
        //thrown exceptions are handled in home controller

    }

    @Override
    public JsonNode displayReaders() {
        ArrayList readerList = new ArrayList<Reader>();
        try {
            ResultSet readerData = DatabaseConnector.getData("reader", "*");
            for (int i = 0; readerData.next(); i++) {
                //adding data to list if data is available
                readerList.add(i, new Reader(readerData.getInt("readerID"),
                        readerData.getString("name"), readerData.getInt("mobileNumber"),
                        readerData.getString("email")));

            }
            //sending list json format
            return Json.toJson(readerList);
        } catch (SQLException e) {
            return null;

        }

    }

    @Override
    public JsonNode displayReservations() {
        ArrayList reservationList = new ArrayList<Reservation>();
        try {
            ResultSet resData = DatabaseConnector.getData("reservation", "*");
            for (int i = 0; resData.next(); i++) {

                 //adding data to list if data is available
                reservationList.add(i, new Reservation(new DateTime(resData.getTimestamp("collection_date")),
                        new Reader(resData.getInt("readerID")),resData.getInt("isbn")));

            }
            //sending list json format
            return Json.toJson(reservationList);
        } catch (SQLException e) {
            return null;

        }
    }

    private ArrayList<LibraryItem> getItemDetails(){
        ArrayList itemList = new ArrayList<LibraryItem>();
        ResultSet libraryData = DatabaseConnector.getData("item","sector,isbn,title,type,taken");


        try{
            if(libraryData != null){

                for(int i = 0;libraryData.next();i++) {
                    if(libraryData.getInt("type") == 1){
                        //if its a book creating a book object
                        itemList.add(i,(LibraryItem)new Book(libraryData.getString("sector"),
                                libraryData.getInt("isbn"), libraryData.getString("title"),
                                new Reader(libraryData.getInt("taken"))));
                    }else {
                        //if its a dvd creating a dvd object

                        itemList.add(i,(LibraryItem)new DVD(libraryData.getString("sector"),
                                libraryData.getInt("isbn"), libraryData.getString("title"),
                                new Reader(libraryData.getInt("taken"))));
                    }


                }
                return itemList;
               //returning the list
            }else {
                return null;
            }
        }catch (SQLException e){
            System.out.println("Database Error");
            return null;
        }

    }
    public JsonNode getSlots(){
        int slotValues[] = new int[2];
        //statements to find count in items
        ResultSet dvdCount  =DatabaseConnector.getData("item  WHERE type = 0","COUNT(isbn)");
        ResultSet bookCount  =DatabaseConnector.getData("item  WHERE type = 1","COUNT(isbn)");
        try {
            if (dvdCount.next()){
                //getting number of dvd
                slotValues[0] = dvdCount.getInt("COUNT(isbn)");
            }
            if (bookCount.next()){
                //getting number of  books
                slotValues[1] = bookCount.getInt("COUNT(isbn)");
            }
            return Json.toJson(slotValues);
            //returning vales as json
        } catch (SQLException e) {
            //-1 for database error
            return Json.toJson(-1);
        }

    }
}
