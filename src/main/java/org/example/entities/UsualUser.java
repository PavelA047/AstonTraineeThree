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
@Table(name = "usualUsers")
public class UsualUser extends User {

    @Column(name = "greeting")
    private String greeting;

    @Override
    public String toString() {
        return super.toString() + " " + greeting;
    }

    public UsualUser(String firstName,
                     String lastName,
                     String nickName,
                     int age, Set<Role> roles,
                     List<Order> orders,
                     String greeting) {
        super(firstName, lastName, nickName, age, roles, orders);
        this.greeting = greeting;
    }
}
