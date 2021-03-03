package classes;


public class DVD extends  LibraryItem {

    /*This concrete class is a specialized class of abstract class LibraryItem */

    private String producer;
    private int duration;
    private String languages[];
    private String subtitles[]; //arrays are used to contain dvd details
    private String  actors[];

    public DVD(String sector, int isbn, String title, Reader presentReader) {
        super(sector, isbn, title, presentReader);
    }

    public DVD(String sector, int isbn, String title, DateTime issueDate, String producer, int duration) {
        super(sector, isbn, title, issueDate);
        this.producer = producer;
        this.duration = duration;
    }





    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String[] subtitles) {
        this.subtitles = subtitles;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
