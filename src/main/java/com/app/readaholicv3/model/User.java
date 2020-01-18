package com.app.readaholicv3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@SequenceGenerator(name = "sequence_users", initialValue=300000, allocationSize=20)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_users")
    @Column(name = "user_id")
    private Long id;

    @NaturalId
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    @JsonIgnore
    private String password;

    @Column
    private String location;

    @Column
    private Integer age;

    @Column
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();

    public User() {
        enabled = false;
    }

    public User(@NotBlank @Email String email, @NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 40) String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        enabled = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", age=" + age +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }

    public Boolean isActive() {
        return this.enabled;
    }

    public void setActive(Boolean active) {
        this.enabled = active;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
