package carsharing.entity;

public class Company implements Comparable<Company> {
    private String name;
    private int id;

    public Company(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Company o) {
        if(this.id > o.getId()) return 1;
        else if (this.id < o.getId()) return -1;
        else return 0;
    }
}
