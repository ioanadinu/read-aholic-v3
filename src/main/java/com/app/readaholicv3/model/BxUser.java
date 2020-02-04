package com.app.readaholicv3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "bx_users")
public class BxUser {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column
    private String location;

    @Column
    private Integer age;

    public BxUser() {
    }

    public BxUser(Long id, String location, Integer age) {
        this.id = id;
        this.location = location;
        this.age = age;
    }

    public BxUser(User user) {
        this.id = user.getId();
        this.age = user.getAge();
        this.location = user.getLocation();
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Integer getAge() {
        return age;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BxUser bxUser = (BxUser) o;
        return id.equals(bxUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BxUser{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", age=" + age +
                '}';
    }
}
