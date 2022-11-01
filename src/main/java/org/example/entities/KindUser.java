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
@Table(name = "kindUsers")
public class KindUser extends User {

    @Column(name = "kindUsers")
    private String kindGreeting;

    @Override
    public String toString() {
        return super.toString() + " " + kindGreeting;
    }

    public KindUser(String firstName,
                    String lastName,
                    String nickName,
                    int age, Set<Role> roles,
                    List<Order> orders,
                    String kindGreeting) {
        super(firstName, lastName, nickName, age, roles, orders);
        this.kindGreeting = kindGreeting;
    }
}
