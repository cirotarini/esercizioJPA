package it.plan.eserciziojpa.repository;

import it.plan.eserciziojpa.domain.Cliente;
import it.plan.eserciziojpa.domain.Prodotto;
import it.plan.eserciziojpa.domain.RigaOrdine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;


@Repository
public class ConArticolo {


    @PersistenceContext
    private EntityManager entityManager;


    public List<Cliente> conArticolo(long id) {
        //Estrai tutti i clienti che hanno ordinato uno specifico artico
          TypedQuery<Cliente> query = entityManager.createQuery(
            "select distinct c from Cliente c  " +
                "JOIN Ordine o on o.cliente.id= c.id  " +
                "JOIN RigaOrdine r on r.ordine.id=o.id  " +
                "JOIN Prodotto p on r.prodotto.id=p.id  " +
                "WHERE p.id = :number"
            , Cliente.class);
        return query.setParameter("number", id).getResultList();



    }




}
