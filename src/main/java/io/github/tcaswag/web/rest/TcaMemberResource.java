package io.github.tcaswag.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.tcaswag.domain.TcaMember;
import io.github.tcaswag.repository.TcaMemberRepository;
import io.github.tcaswag.web.rest.errors.BadRequestAlertException;
import io.github.tcaswag.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TcaMember.
 */
@RestController
@RequestMapping("/api")
public class TcaMemberResource {

    private final Logger log = LoggerFactory.getLogger(TcaMemberResource.class);

    private static final String ENTITY_NAME = "tcaMember";

    private final TcaMemberRepository tcaMemberRepository;

    public TcaMemberResource(TcaMemberRepository tcaMemberRepository) {
        this.tcaMemberRepository = tcaMemberRepository;
    }

    /**
     * POST  /tca-members : Create a new tcaMember.
     *
     * @param tcaMember the tcaMember to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tcaMember, or with status 400 (Bad Request) if the tcaMember has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tca-members")
    @Timed
    public ResponseEntity<TcaMember> createTcaMember(@Valid @RequestBody TcaMember tcaMember) throws URISyntaxException {
        log.debug("REST request to save TcaMember : {}", tcaMember);
        if (tcaMember.getId() != null) {
            throw new BadRequestAlertException("A new tcaMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcaMember result = tcaMemberRepository.save(tcaMember);
        return ResponseEntity.created(new URI("/api/tca-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tca-members : Updates an existing tcaMember.
     *
     * @param tcaMember the tcaMember to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tcaMember,
     * or with status 400 (Bad Request) if the tcaMember is not valid,
     * or with status 500 (Internal Server Error) if the tcaMember couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tca-members")
    @Timed
    public ResponseEntity<TcaMember> updateTcaMember(@Valid @RequestBody TcaMember tcaMember) throws URISyntaxException {
        log.debug("REST request to update TcaMember : {}", tcaMember);
        if (tcaMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcaMember result = tcaMemberRepository.save(tcaMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tcaMember.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tca-members : get all the tcaMembers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tcaMembers in body
     */
    @GetMapping("/tca-members")
    @Timed
    public List<TcaMember> getAllTcaMembers() {
        log.debug("REST request to get all TcaMembers");
        return tcaMemberRepository.findAll();
    }

    /**
     * GET  /tca-members/:id : get the "id" tcaMember.
     *
     * @param id the id of the tcaMember to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tcaMember, or with status 404 (Not Found)
     */
    @GetMapping("/tca-members/{id}")
    @Timed
    public ResponseEntity<TcaMember> getTcaMember(@PathVariable Long id) {
        log.debug("REST request to get TcaMember : {}", id);
        Optional<TcaMember> tcaMember = tcaMemberRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tcaMember);
    }

    /**
     * DELETE  /tca-members/:id : delete the "id" tcaMember.
     *
     * @param id the id of the tcaMember to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tca-members/{id}")
    @Timed
    public ResponseEntity<Void> deleteTcaMember(@PathVariable Long id) {
        log.debug("REST request to delete TcaMember : {}", id);

        tcaMemberRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
