package springstudy.bookstore.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address implements Serializable {

    private String city;
    private String street;
    private String zipcode;

    @Builder(builderMethodName = "addressBuilder")
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Address Info {" + "city=" + city + ", street=" + street + ", zipcode =" + zipcode +  '}';
    }
}
