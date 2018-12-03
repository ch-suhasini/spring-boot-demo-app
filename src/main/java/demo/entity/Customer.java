package demo.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {

    public Customer() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstname;
    private String lastname;
    private String title;
    private char sex;
    private String martialStatus;
    private int creditRating;
    private boolean isNabCustomer;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> address = new HashSet<Address>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public int getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(int creditRating) {
        this.creditRating = creditRating;
    }

    public boolean isNabCustomer() {
        return isNabCustomer;
    }

    public void setNabCustomer(boolean nabCustomer) {
        isNabCustomer = nabCustomer;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        if (address == null) {
            address = new HashSet<>();
            return;
        }
        for (Address singleAddress : address) {
            addAddress(singleAddress);
        }
          this.address = address;
    }

    public void addAddress(Address singleAddress) {
        if (singleAddress == null) {
            return;
        } else {
            if (address == null) {
                address = new HashSet<>();
            }
            singleAddress.setCustomer(this);
            address.add(singleAddress);
        }
       // this.setAddress(address);
    }


    public void removeAddress(Address singleAddress) {
        singleAddress.setCustomer(null);
        address.remove(singleAddress);

    }
}
