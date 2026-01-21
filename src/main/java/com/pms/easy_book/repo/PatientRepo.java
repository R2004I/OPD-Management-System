package com.pms.easy_book.repo;

import com.pms.easy_book.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {


    public Patient findByEmailAndPhone(String email, String phone);
}
