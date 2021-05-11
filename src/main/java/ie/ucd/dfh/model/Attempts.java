package ie.ucd.dfh.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Attempts {

    @Id
    private int id;

    private String username;

    private int attemps;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAttemps() {
        return attemps;
    }

    public void setAttemps(int attemps) {
        this.attemps = attemps;
    }

}
