package com.pills.pillstracker.models.daos;

import com.pills.pillstracker.validators.tags.ContactNumberConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "\"User\"",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "contactNumber"),
        @UniqueConstraint(columnNames = "email")
    })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @ContactNumberConstraint
    private String contactNumber;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Exclude
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Exclude
    private Set<Person> persons = new HashSet<>();

    public void addPerson(Person person) {

        persons.add(person);
        person.setUser(this);
    }

    public void removePerson(Person person) {

        persons.add(person);
        person.setUser(null);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }

}
