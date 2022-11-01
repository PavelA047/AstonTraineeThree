package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "angryUsers")
public class AngryUser extends User {

    @Column(name = "nastyLooking")
    private String nastyLooking;

    @Override
    public String toString() {
        return super.toString() + " " + nastyLooking;
    }

    public AngryUser(String firstName,
                     String lastName,
                     String nickName,
                     int age, Set<Role> roles,
                     List<Order> orders,
                     String nastyLooking) {
        super(firstName, lastName, nickName, age, roles, orders);
        this.nastyLooking = nastyLooking;
    }
}
