package com.pills.pillstracker.repositories;

import com.pills.pillstracker.models.daos.MedicineStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicineStoreRepository extends JpaRepository<MedicineStore, Long> {

}
