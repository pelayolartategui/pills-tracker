package com.pills.pillstracker.repositories;

import com.pills.pillstracker.models.daos.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
