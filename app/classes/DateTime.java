package classes;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateTime implements Comparable{

    /*Comparable interface is implemented to get the comparing behaviour,
      to compare with another Date object*/

    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    public DateTime(Timestamp sqlDate) {

        /*Creating a DateTime object from a TimeStamp object*/

        String databaseDateTime[] = sqlDate.toString().split(" ");
        this.year = Integer.parseInt(databaseDateTime[0].split("-")[0]);
        this.month = Integer.parseInt(databaseDateTime[0].split("-")[1]);
        this.day = Integer.parseInt(databaseDateTime[0].split("-")[2]);
        this.hour = Integer.parseInt(databaseDateTime[1].split(":")[0]);
        this.minute = Integer.parseInt(databaseDateTime[1].split(":")[1]);


    }

    public DateTime(int day, int month, int year, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }
    public DateTime(String[] dateValues){

        /*Initiating a DateTime from a String array*/

        this.day = Integer.parseInt(dateValues[2]);
        this.month = Integer.parseInt(dateValues[1]);
        this.year = Integer.parseInt(dateValues[0]);
    }

    public DateTime() {

        /*Initiating a Current DateTime*/

        LocalDate today = new java.util.Date().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        this.month = today.getMonthValue();
        this.year = today.getYear();
        this.day = today.getDayOfMonth();
        this.hour = 10;
        this.minute = 0;
    }
    public DateTime(String jsonDate) {

        /*Initiating a DateTime from a JsonDate */

        String date = jsonDate.split("T")[0];
        String time = jsonDate.split("T")[1];
        String dateValues[] = date.split("-");
        this.day = Integer.parseInt(dateValues[2]);
        this.month = Integer.parseInt(dateValues[1]);
        this.year =Integer.parseInt(dateValues[0]);
        this.hour = Integer.parseInt(time.split(":")[0]);
        this.minute=Integer.parseInt(time.split(":")[1]);

    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public Date getSqlDate(){

        /*Getting the Date in SQL date format*/

        return new Date(this.year-1900,this.month-1,this.day);
    }
    public Timestamp getSqlDateTime(){

        /*Getting the Date in SQL TimeStamp format*/

        return new Timestamp(this.year-1900,this.month-1,this.day,
                this.hour,this.minute,0,0);
    }

    public DateTime addDays(int days){

        /*This is a method which will add days to a Date and will return
         a forward date as required*/

        int monthDays;
        int day = this.day; int month  = this.month; int year = this.year;
        if(month ==2){
            monthDays = 28;//if month is two only 28 days

        }else if(month <= 7){
            //checking whether the month has 30 or 31 days
            if((month % 2) == 0){
                monthDays = 30;
            }else{
                monthDays = 31;
            }
        }else {
            if((month % 2) == 0){
                monthDays = 31;
            }else{
                monthDays = 30;
            }
        }
        day = day + days;
        if(day <= monthDays){
            return new DateTime(day,month,year,this.hour,this.minute);
        }else {
            //if month changes due to adding days
            month = month + 1;
            if (month <= 12){
                return new DateTime(day - monthDays,month,year,this.hour,this.minute);
            }
            else {//if year changes due to adding days
                return new DateTime(day - monthDays,month - 12,year+1,this.hour,this.minute);
            }
        }

    }



    @Override
    public int compareTo(Object o) {


        DateTime input = (DateTime) o;//casting to a DateTime object
        if((this.year - input.year) == 0 && (this.month - input.month) ==0 && (this.day - input.day == 0)
                && (this.hour - input.hour )== 0 && (this.minute - input.minute) == 0){
            //if the comparing date is the same
            return 0;
        }else {
            //checking the difference of days in minutes
            long difference = this.getDifferenceInMinutes(input);
            if(difference > 0){
                //if the date is large comparing to the referred Date
              return  1;
            }else {
                //if the date is small comparing to the referred Date
                return  -1;
            }
            }
        }
      public long getDifferenceInMinutes(DateTime dateTime){
        /*This method will return the difference between two days in minutes*/
         return  (this.year - dateTime.year)* 525600 + (this.month - dateTime.month) * 43200
                  + (this.day - dateTime.day) *1440  + (this.hour - dateTime.hour) * 60 +
                  (this.minute - dateTime.minute);
      }



}
