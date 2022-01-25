package com.pills.pillstracker.models.daos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime lastUpdate;

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "medicine")
    @Exclude
    private Set<MedicineSchedule> medicineSchedules = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "medicine")
    @Exclude
    private Set<MedicineStore> medicineStores = new HashSet<>();

}
