package controllers;
import classes.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class HomeController extends Controller {

    public Result sendTableContent(){
        //sending table data in json format
        JsonNode TableViewData = new WetsminsterLibraryManager().displayItemList();
        return ok(TableViewData).as("application/json");
    }

    public Result sendReaderTable(){

        //sending table data in json format

        JsonNode TableViewData = new WetsminsterLibraryManager().displayReaders();
        return ok(TableViewData).as("application/json");
    }
    public Result sendReservationTable(){

        //sending table data in json format

        JsonNode TableViewData = new WetsminsterLibraryManager().displayReservations();
        return ok(TableViewData).as("application/json");
    }

    public Result searchTitle(){
        try {

            JsonNode searchKey = Json.parse(request().body().asText());
            //getting the requested title from user
            JsonNode TableViewData = new WetsminsterLibraryManager()
                    .searchItem(searchKey.get("title").textValue().trim());

            return ok(TableViewData).as("application/json");
            //sending table data in json format

        }catch (NullPointerException ex){
            return ok(Json.toJson("")).as("application/json");
        }

    }




    public Result getBookFormData()  {
        JsonNode bookFormResponse;

        try{
            JsonNode bookFormData = Json.parse(request().body().asText());
            //getting data from frontend and initiating a book object
            Book newBook = new Book(bookFormData.get("sector").textValue(),
                    bookFormData.get("isbn").intValue(),bookFormData.get("title").textValue(),
                    new DateTime(bookFormData.get("date").textValue().split("-")),
                    bookFormData.get("pages").intValue());

            newBook.setAuthors(bookFormData.get("authors").asText().split(","));
            //setting authors of the book
            new WetsminsterLibraryManager().addLibraryItem(newBook);
            //adding the library item
            bookFormResponse = Json.toJson(1);
            //returning 1 as json if action successful
            return ok(bookFormResponse).as("application/json");

        }catch (SQLIntegrityConstraintViolationException error){
            bookFormResponse = Json.toJson(0);
            //returning 0 as json if primary key duplicates

            return ok(bookFormResponse).as("application/json");
        } catch (SQLException e) {
            e.printStackTrace();
            bookFormResponse = Json.toJson(-1);
            //returning -1 as json if database error

            return ok(bookFormResponse).as("application/json");
        } catch (Exception e) {
            bookFormResponse = Json.toJson(-2);

            return ok(bookFormResponse).as("application/json");
        }


    }


    public Result getDvdFormData()  {
        JsonNode dvdFormResponse;

        try{
            JsonNode dvdFormData = Json.parse(request().body().asText());
            //getting data from frontend and initiating a dvd object

            DVD newDVD = new DVD(dvdFormData.get("sector").textValue(),dvdFormData.get("isbn").intValue(),
                    dvdFormData.get("title").textValue(),new DateTime(dvdFormData.get("date").asText().split("-")),
                    dvdFormData.get("producer").textValue(),dvdFormData.get("duration").intValue());

            //setting actors, subtitles, languages taken from frontend
            newDVD.setActors(dvdFormData.get("actors").asText().split(","));
            newDVD.setLanguages(dvdFormData.get("languages").asText().split(","));
            newDVD.setSubtitles(dvdFormData.get("subtitles").asText().split(","));

            new WetsminsterLibraryManager().addLibraryItem(newDVD);

            dvdFormResponse = Json.toJson(1);
            //returning 1 as json if action successful
            return ok(dvdFormResponse).as("application/json");

        }catch (SQLIntegrityConstraintViolationException error){
            dvdFormResponse = Json.toJson(0);
            //returning 0 as json if primary key duplicates
            return ok(dvdFormResponse).as("application/json");

        } catch (SQLException e) {
            e.printStackTrace();
            dvdFormResponse = Json.toJson(-1);
            //returning -1 as json if database error

            return ok(dvdFormResponse).as("application/json");
        } catch (Exception e) {
            dvdFormResponse = Json.toJson(-2);
            return ok(dvdFormResponse).as("application/json");

        }

    }
    public Result lendItem()  {
        JsonNode actionResponse;
        JsonNode lendFormData = Json.parse(request().body().asText());
        //getting data from frontend and initiating a logRecord object
        LogRecord record = new LogRecord(new DateTime(lendFormData.get("date").textValue()),
                lendFormData.get("isbn").intValue(),new Reader(lendFormData.get("readerId").intValue()));
        try {
            //lending the item
            new WetsminsterLibraryManager().lendItem(record);

            actionResponse = Json.toJson(1);
            //if action succeed returning 1 in json format
            return ok(actionResponse).as("application/json");

        }catch (SQLIntegrityConstraintViolationException error){
            if(error.getErrorCode() == 1062){
                //if primary key duplicates send 2 in json format
                actionResponse = Json.toJson(2);
                return ok(actionResponse).as("application/json");
            }else {
                actionResponse = Json.toJson(0);
                //if foreign key error send 0 as json
                return ok(actionResponse).as("application/json");
            }

        } catch (SQLException e) {
            actionResponse = Json.toJson(-1);
            return ok(actionResponse).as("application/json");
        }

    }

    public Result deleteItem()  {
        JsonNode actionResponse;
        //get isbn of the item that should be deleted
        JsonNode itemData = Json.parse(request().body().asText());
        try {
            //deleting item
            new WetsminsterLibraryManager().deleteLibraryItem(itemData.get("isbn").intValue());

            actionResponse = Json.toJson(true);
            //if action succeed returning true in json format
            return ok(actionResponse).as("application/json");
        } catch (SQLException e) {

            //if action succeed returning false in json format

            actionResponse = Json.toJson(false);
            return ok(actionResponse).as("application/json");

        }

    }
    public  Result returnItem(){

        JsonNode actionResponse;
        JsonNode returnFormData = Json.parse(request().body().asText());
         try {
             //getting data from front end and creating and logRecord object

             //calling the return function from westminster library manager
             LogRecord record = new WetsminsterLibraryManager().returnItem(new LogRecord
                     (new DateTime(returnFormData.get("date").textValue()),returnFormData.get("isbn").intValue(),
                             new Reader(returnFormData.get("readerId").intValue())));

             actionResponse = Json.toJson(record);
             //sending response to front-end
             return ok(actionResponse).as("application/json");
         }catch (NullPointerException ex){
             actionResponse = Json.toJson(-1);
             return ok(actionResponse).as("application/json");
         }


    }
    public Result calculateFreeSpaces(){
        //sending free spaces data in json format
        JsonNode slotData = new WetsminsterLibraryManager().getSlots();
        return ok(slotData).as("application/json");
    }

    public Result generateReport(){
        //sending log record data in json format
        JsonNode reportData = new WetsminsterLibraryManager().generateReport();
        return ok(reportData).as("application/json");
    }
    public Result getReaderFormData(){
        JsonNode readerFormResponse;

        try{
            JsonNode readerFormData = Json.parse(request().body().asText());
            //getting data from frontend and initiating a reader object

            Reader libUser = new Reader(readerFormData.get("name").textValue(),readerFormData.get("number").intValue(),
                    readerFormData.get("email").textValue());
            //adding the reader to database
            new WetsminsterLibraryManager().addReader(libUser);

            readerFormResponse = Json.toJson(1);
            //returning 1 as json if action successful

            return ok(readerFormResponse).as("application/json");
        }catch (SQLIntegrityConstraintViolationException error){
            readerFormResponse = Json.toJson(0);
            //returning 0 as json if primary key error

            return ok(readerFormResponse).as("application/json");
        } catch (SQLException e) {
            readerFormResponse = Json.toJson(-1);
            //returning -1 as json if database error
            return ok(readerFormResponse).as("application/json");
        }
    }


    public Result reserveItem(){
         JsonNode resData = Json.parse(request().body().asText());
         //getting data from frontend and creating a reservation object
         Reservation userReservation = new Reservation(new Reader(resData.get("readerId").intValue()),
                 resData.get("isbn").intValue());
         //making the reservation
         return ok(new WetsminsterLibraryManager().makeReservation(userReservation)).as("application/json");
    }

}
