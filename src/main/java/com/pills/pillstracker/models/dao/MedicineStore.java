package com.pills.pillstracker.models.dao;

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
@Table(name = "medicineStore")
public class MedicineStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int currentStatus;

    private int notificationThreshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @Exclude
    private Medicine medicine;

}
