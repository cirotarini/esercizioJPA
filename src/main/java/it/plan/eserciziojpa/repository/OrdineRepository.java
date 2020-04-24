package it.plan.eserciziojpa.repository;

import it.plan.eserciziojpa.domain.Cliente;
import it.plan.eserciziojpa.domain.Ordine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Ordine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long> {






//Estrai tutti i clienti che hanno ordinato uno specifico articolo
//Estrai tutti i clienti e, per quelli che hanno ordini, anche l’ordine (ovviamente la query potrà restituire lo stesso cliente più volte per ogni ordine)

}
