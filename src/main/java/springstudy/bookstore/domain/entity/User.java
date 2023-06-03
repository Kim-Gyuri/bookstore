package springstudy.bookstore.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String email;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @Builder(builderMethodName = "userBuilder")
    public User(String loginId, String password, String name, String email, Address address) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User Info {" + "id=" + id + ", name=" + name + ", loginId=" + loginId + ", password =" + password + ", address =" + address + '}';
    }

}
