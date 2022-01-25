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
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "medicineSchedule")
public class MedicineSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private Medicine medicine;

}