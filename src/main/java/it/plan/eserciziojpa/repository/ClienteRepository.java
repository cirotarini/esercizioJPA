package it.plan.eserciziojpa.repository;

import it.plan.eserciziojpa.domain.Cliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    //Estrai tutti i clienti che hanno almeno due ordini
    @Query("select o.cliente from Ordine o GROUP BY o.cliente.id HAVING COUNT(o) >= 2")
    public List<Cliente> clientiCon2Ordini();

    //Estrai tutti i clienti che non hanno ordini
    @Query("select c from Cliente c LEFT JOIN Ordine o on o.cliente.id= c.id where o is null")
    public List<Cliente> clientiCon0Ordini();

    //Estrai tutti i clienti e, per quelli che hanno ordini, anche l’ordine (ovviamente la query potrà restituire lo stesso cliente più volte per ogni ordine)
    @Query("select c,o from Cliente c LEFT JOIN Ordine o on o.cliente.id= c.id  order by c.id, o.id "
        )
    public List<Object[]> clientiOptOrdini();

    //Estrai tutti i prodotti e, per ogni prodotto, il numero totale di ordini in cui è stato ordinato
    @Query("select p, Count (o) from Prodotto p " +
        "left JOIN RigaOrdine r on r.prodotto.id=p.id " +
        "left Join Ordine o on r.ordine.id= o.id " +
        "GROUP By p.id " +
        "order by p.id"
    )
    public List<Object[]> prodottiNUmOrdini();

    //Estrai, per ogni cliente e prodotto, la quantità totale di pezzi ordinati
    @Query("select c, p,  Sum (r.quantita) from Prodotto p " +
        "JOIN RigaOrdine r on r.prodotto.id=p.id " +
        "Join Ordine o on r.ordine.id= o.id " +
        "Join Cliente c  on o.cliente= c.id " +
        "GROUP By c.id, p.id  " +
        "order by c.id, p.id"
    )
    public List<Object[]> clienteProdottoNumPezzi();


    //Estrai, per ogni cliente, il numero di ordini con meno di 3 righe (incluse quindi 0)
    @Query("select count(o),c   from Cliente c  " +
        "left Join Ordine o on c.id= o.cliente " +
        "left JOIN RigaOrdine r on r.ordine=o.id " +
        "GROUP By c.id, o.id "+
        "HAVING count (r.id) < 3 " +
        "order by c.id"
    )
    public List<Object[]> clienteOrdiniMax3Righe();




}
