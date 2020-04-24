package it.plan.eserciziojpa.repository;

import it.plan.eserciziojpa.domain.RigaOrdine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RigaOrdine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RigaOrdineRepository extends JpaRepository<RigaOrdine, Long> {

}
