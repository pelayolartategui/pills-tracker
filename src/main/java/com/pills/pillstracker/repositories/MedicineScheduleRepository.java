package com.pills.pillstracker.repositories;

import com.pills.pillstracker.models.daos.MedicineSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicineScheduleRepository extends JpaRepository<MedicineSchedule, Long> {

}
