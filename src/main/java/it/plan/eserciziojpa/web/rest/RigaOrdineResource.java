package it.plan.eserciziojpa.web.rest;

import it.plan.eserciziojpa.domain.RigaOrdine;
import it.plan.eserciziojpa.repository.RigaOrdineRepository;
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
 * REST controller for managing {@link it.plan.eserciziojpa.domain.RigaOrdine}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RigaOrdineResource {

    private final Logger log = LoggerFactory.getLogger(RigaOrdineResource.class);

    private static final String ENTITY_NAME = "rigaOrdine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RigaOrdineRepository rigaOrdineRepository;

    public RigaOrdineResource(RigaOrdineRepository rigaOrdineRepository) {
        this.rigaOrdineRepository = rigaOrdineRepository;
    }

    /**
     * {@code POST  /riga-ordines} : Create a new rigaOrdine.
     *
     * @param rigaOrdine the rigaOrdine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rigaOrdine, or with status {@code 400 (Bad Request)} if the rigaOrdine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/riga-ordines")
    public ResponseEntity<RigaOrdine> createRigaOrdine(@Valid @RequestBody RigaOrdine rigaOrdine) throws URISyntaxException {
        log.debug("REST request to save RigaOrdine : {}", rigaOrdine);
        if (rigaOrdine.getId() != null) {
            throw new BadRequestAlertException("A new rigaOrdine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RigaOrdine result = rigaOrdineRepository.save(rigaOrdine);
        return ResponseEntity.created(new URI("/api/riga-ordines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /riga-ordines} : Updates an existing rigaOrdine.
     *
     * @param rigaOrdine the rigaOrdine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rigaOrdine,
     * or with status {@code 400 (Bad Request)} if the rigaOrdine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rigaOrdine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/riga-ordines")
    public ResponseEntity<RigaOrdine> updateRigaOrdine(@Valid @RequestBody RigaOrdine rigaOrdine) throws URISyntaxException {
        log.debug("REST request to update RigaOrdine : {}", rigaOrdine);
        if (rigaOrdine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RigaOrdine result = rigaOrdineRepository.save(rigaOrdine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rigaOrdine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /riga-ordines} : get all the rigaOrdines.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rigaOrdines in body.
     */
    @GetMapping("/riga-ordines")
    public List<RigaOrdine> getAllRigaOrdines() {
        log.debug("REST request to get all RigaOrdines");
        return rigaOrdineRepository.findAll();
    }

    /**
     * {@code GET  /riga-ordines/:id} : get the "id" rigaOrdine.
     *
     * @param id the id of the rigaOrdine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rigaOrdine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/riga-ordines/{id}")
    public ResponseEntity<RigaOrdine> getRigaOrdine(@PathVariable Long id) {
        log.debug("REST request to get RigaOrdine : {}", id);
        Optional<RigaOrdine> rigaOrdine = rigaOrdineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rigaOrdine);
    }

    /**
     * {@code DELETE  /riga-ordines/:id} : delete the "id" rigaOrdine.
     *
     * @param id the id of the rigaOrdine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/riga-ordines/{id}")
    public ResponseEntity<Void> deleteRigaOrdine(@PathVariable Long id) {
        log.debug("REST request to delete RigaOrdine : {}", id);
        rigaOrdineRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
