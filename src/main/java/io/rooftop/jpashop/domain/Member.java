package io.rooftop.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty : 화면용이 entity에 들어오면 안된다.
    private String name;

    @Embedded
    private Address address;

 //   @JsonIgnore  화면용이 entity에 들어오면 안된다.
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();
}
