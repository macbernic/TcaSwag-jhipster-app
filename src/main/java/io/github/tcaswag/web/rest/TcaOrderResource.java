package io.github.tcaswag.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.tcaswag.domain.TcaOrder;
import io.github.tcaswag.repository.TcaOrderRepository;
import io.github.tcaswag.web.rest.errors.BadRequestAlertException;
import io.github.tcaswag.web.rest.util.HeaderUtil;
import io.github.tcaswag.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TcaOrder.
 */
@RestController
@RequestMapping("/api")
public class TcaOrderResource {

    private final Logger log = LoggerFactory.getLogger(TcaOrderResource.class);

    private static final String ENTITY_NAME = "tcaOrder";

    private final TcaOrderRepository tcaOrderRepository;

    public TcaOrderResource(TcaOrderRepository tcaOrderRepository) {
        this.tcaOrderRepository = tcaOrderRepository;
    }

    /**
     * POST  /tca-orders : Create a new tcaOrder.
     *
     * @param tcaOrder the tcaOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tcaOrder, or with status 400 (Bad Request) if the tcaOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tca-orders")
    @Timed
    public ResponseEntity<TcaOrder> createTcaOrder(@Valid @RequestBody TcaOrder tcaOrder) throws URISyntaxException {
        log.debug("REST request to save TcaOrder : {}", tcaOrder);
        if (tcaOrder.getId() != null) {
            throw new BadRequestAlertException("A new tcaOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcaOrder result = tcaOrderRepository.save(tcaOrder);
        return ResponseEntity.created(new URI("/api/tca-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tca-orders : Updates an existing tcaOrder.
     *
     * @param tcaOrder the tcaOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tcaOrder,
     * or with status 400 (Bad Request) if the tcaOrder is not valid,
     * or with status 500 (Internal Server Error) if the tcaOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tca-orders")
    @Timed
    public ResponseEntity<TcaOrder> updateTcaOrder(@Valid @RequestBody TcaOrder tcaOrder) throws URISyntaxException {
        log.debug("REST request to update TcaOrder : {}", tcaOrder);
        if (tcaOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcaOrder result = tcaOrderRepository.save(tcaOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tcaOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tca-orders : get all the tcaOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tcaOrders in body
     */
    @GetMapping("/tca-orders")
    @Timed
    public ResponseEntity<List<TcaOrder>> getAllTcaOrders(Pageable pageable) {
        log.debug("REST request to get a page of TcaOrders");
        Page<TcaOrder> page = tcaOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tca-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tca-orders/:id : get the "id" tcaOrder.
     *
     * @param id the id of the tcaOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tcaOrder, or with status 404 (Not Found)
     */
    @GetMapping("/tca-orders/{id}")
    @Timed
    public ResponseEntity<TcaOrder> getTcaOrder(@PathVariable Long id) {
        log.debug("REST request to get TcaOrder : {}", id);
        Optional<TcaOrder> tcaOrder = tcaOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tcaOrder);
    }

    /**
     * DELETE  /tca-orders/:id : delete the "id" tcaOrder.
     *
     * @param id the id of the tcaOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tca-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteTcaOrder(@PathVariable Long id) {
        log.debug("REST request to delete TcaOrder : {}", id);

        tcaOrderRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
