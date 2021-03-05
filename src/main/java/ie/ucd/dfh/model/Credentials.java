package ie.ucd.dfh.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Credentials {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name="password")
    private String password;

    public Credentials() {
    }

    public Credentials(@NotBlank String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
