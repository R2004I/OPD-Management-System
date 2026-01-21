package com.pms.easy_book.repo;

import com.pms.easy_book.entity.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctors, Long> {

    List<Doctors> findBySpecialization(String specialization);
}
