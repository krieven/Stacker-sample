package sample.model;

public class Category {
    private String id;
    private String parent;
    private String title;

    public Category() {
    }

    public Category(String id, String parent, String title){
        this.id = id;
        this.parent = parent;
        this.title = title;
    }

    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getParent() {
        return parent;
    }
}
