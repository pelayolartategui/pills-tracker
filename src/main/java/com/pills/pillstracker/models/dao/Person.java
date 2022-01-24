package com.pills.pillstracker.models.dao;

import com.pills.pillstracker.validators.tags.ContactNumberConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "person",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "contactNumber")
    })
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String firstname;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @ContactNumberConstraint
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private User user;

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @Exclude
    private Set<MedicineSchedule> medicineSchedules = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    @Exclude
    private Set<MedicineStore> medicineStores = new HashSet<>();

}
