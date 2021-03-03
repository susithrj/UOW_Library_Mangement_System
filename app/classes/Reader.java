package classes;

public class Reader {

    private int mobileNumber;
    private String email;
    private int readerId;
    private String name;


    public Reader(int readerId, String name, int mobileNumber, String email) {
        this.readerId = readerId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public Reader(String name, int mobileNumber, String email) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Reader(int readerId) {
        this.readerId = readerId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }



    public String getEmail() {
        return email;
    }


}
