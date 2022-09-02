package carsharing.entity;

public class Customer implements Comparable<Customer> {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Customer o) {
        if(this.id > o.getId()) return 1;
        else if (this.id < o.getId()) return -1;
        else return 0;
    }
}
