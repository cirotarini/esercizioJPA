package it.plan.eserciziojpa.web.rest;

import it.plan.eserciziojpa.domain.Cliente;
import it.plan.eserciziojpa.repository.ClienteRepository;
import it.plan.eserciziojpa.repository.ConArticolo;
import it.plan.eserciziojpa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.plan.eserciziojpa.domain.Cliente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

    private static final String ENTITY_NAME = "cliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClienteRepository clienteRepository;
    private final ConArticolo myQueryImplementation;

    public ClienteResource(ClienteRepository clienteRepository, ConArticolo myQueryImplementation) {
        this.clienteRepository = clienteRepository;
        this.myQueryImplementation = myQueryImplementation;
    }

    /**
     * {@code POST  /clientes} : Create a new cliente.
     *
     * @param cliente the cliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cliente, or with status {@code 400 (Bad Request)} if the cliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) throws URISyntaxException {
        log.debug("REST request to save Cliente : {}", cliente);
        if (cliente.getId() != null) {
            throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cliente result = clienteRepository.save(cliente);
        return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clientes} : Updates an existing cliente.
     *
     * @param cliente the cliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cliente,
     * or with status {@code 400 (Bad Request)} if the cliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clientes")
    public ResponseEntity<Cliente> updateCliente(@Valid @RequestBody Cliente cliente) throws URISyntaxException {
        log.debug("REST request to update Cliente : {}", cliente);
        if (cliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cliente result = clienteRepository.save(cliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clientes} : get all the clientes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientes in body.
     */
    @GetMapping("/clientes")
    public List<Cliente> getAllClientes() {
        log.debug("REST request to get all Clientes");
        return clienteRepository.findAll();
    }

    @GetMapping("/ClientiCon2Ordini")
    public List<Cliente> ClientiCon2Ordini() {
        log.debug("Tutti i clienti che hanno almeno due ordini");
        return clienteRepository.clientiCon2Ordini();
    }

    @GetMapping("/ClientiCon0Ordini")
    public List<Cliente> ClientiCon0Ordini() {
        log.debug("tutti i clienti che non hanno ordini");
        return clienteRepository.clientiCon0Ordini();
    }

    @GetMapping("/conArticolo/{id}")
    public List<Cliente> getAllClientesByArticolo(@PathVariable Long id) {
        log.debug("tutti i clienti che hanno ordinato uno specifico artico");
        return this.myQueryImplementation.conArticolo(id);
    }

    @GetMapping("/clientiOptOrdini")
    public List<Object[]> clientiOptOrdini() {
        log.debug("tutti i clienti e, per quelli che hanno ordini, anche l’ordine ");
        return clienteRepository.clientiOptOrdini();
    }

    @GetMapping("/prodottiNUmOrdini")
    public List<Object[]> prodottiNUmOrdini() {
        log.debug("tutti i prodotti e, per ogni prodotto, il numero totale di ordini in cui è stato ordinato");
        return clienteRepository.prodottiNUmOrdini();
    }




    @GetMapping("/clienteProdottoNumPezzi")
    public List<Object[]> clienteProdottoNumPezzi() {
        log.debug("per ogni cliente e prodotto, la quantità totale di pezzi ordinati)");
        return clienteRepository.clienteProdottoNumPezzi();
    }


   @GetMapping("/ClienteOrdiniMax3Righe")
    public List<Object[]> ClienteOrdiniMax3Righe() {
        log.debug("per ogni cliente, il numero di ordini con meno di 3 righe (incluse quindi 0)");
        return clienteRepository.clienteOrdiniMax3Righe();
    }




    /**
     * {@code GET  /clientes/:id} : get the "id" cliente.
     *
     * @param id the id of the cliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        log.debug("REST request to get Cliente : {}", id);
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cliente);
    }

    /**
     * {@code DELETE  /clientes/:id} : delete the "id" cliente.
     *
     * @param id the id of the cliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.debug("REST request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
