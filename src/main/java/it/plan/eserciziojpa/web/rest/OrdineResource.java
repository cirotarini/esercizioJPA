package it.plan.eserciziojpa.web.rest;

import it.plan.eserciziojpa.domain.Ordine;
import it.plan.eserciziojpa.repository.OrdineRepository;
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
 * REST controller for managing {@link it.plan.eserciziojpa.domain.Ordine}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrdineResource {

    private final Logger log = LoggerFactory.getLogger(OrdineResource.class);

    private static final String ENTITY_NAME = "ordine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdineRepository ordineRepository;

    public OrdineResource(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    /**
     * {@code POST  /ordines} : Create a new ordine.
     *
     * @param ordine the ordine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordine, or with status {@code 400 (Bad Request)} if the ordine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ordines")
    public ResponseEntity<Ordine> createOrdine(@Valid @RequestBody Ordine ordine) throws URISyntaxException {
        log.debug("REST request to save Ordine : {}", ordine);
        if (ordine.getId() != null) {
            throw new BadRequestAlertException("A new ordine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ordine result = ordineRepository.save(ordine);
        return ResponseEntity.created(new URI("/api/ordines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ordines} : Updates an existing ordine.
     *
     * @param ordine the ordine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordine,
     * or with status {@code 400 (Bad Request)} if the ordine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ordines")
    public ResponseEntity<Ordine> updateOrdine(@Valid @RequestBody Ordine ordine) throws URISyntaxException {
        log.debug("REST request to update Ordine : {}", ordine);
        if (ordine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ordine result = ordineRepository.save(ordine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ordines} : get all the ordines.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordines in body.
     */
    @GetMapping("/ordines")
    public List<Ordine> getAllOrdines() {
        log.debug("REST request to get all Ordines");
        return ordineRepository.findAll();
    }

    /**
     * {@code GET  /ordines/:id} : get the "id" ordine.
     *
     * @param id the id of the ordine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ordines/{id}")
    public ResponseEntity<Ordine> getOrdine(@PathVariable Long id) {
        log.debug("REST request to get Ordine : {}", id);
        Optional<Ordine> ordine = ordineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ordine);
    }

    /**
     * {@code DELETE  /ordines/:id} : delete the "id" ordine.
     *
     * @param id the id of the ordine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ordines/{id}")
    public ResponseEntity<Void> deleteOrdine(@PathVariable Long id) {
        log.debug("REST request to delete Ordine : {}", id);
        ordineRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
