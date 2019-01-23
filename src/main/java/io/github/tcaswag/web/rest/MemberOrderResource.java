package io.github.tcaswag.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.tcaswag.domain.MemberOrder;
import io.github.tcaswag.repository.MemberOrderRepository;
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
 * REST controller for managing MemberOrder.
 */
@RestController
@RequestMapping("/api")
public class MemberOrderResource {

    private final Logger log = LoggerFactory.getLogger(MemberOrderResource.class);

    private static final String ENTITY_NAME = "memberOrder";

    private final MemberOrderRepository memberOrderRepository;

    public MemberOrderResource(MemberOrderRepository memberOrderRepository) {
        this.memberOrderRepository = memberOrderRepository;
    }

    /**
     * POST  /member-orders : Create a new memberOrder.
     *
     * @param memberOrder the memberOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memberOrder, or with status 400 (Bad Request) if the memberOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/member-orders")
    @Timed
    public ResponseEntity<MemberOrder> createMemberOrder(@Valid @RequestBody MemberOrder memberOrder) throws URISyntaxException {
        log.debug("REST request to save MemberOrder : {}", memberOrder);
        if (memberOrder.getId() != null) {
            throw new BadRequestAlertException("A new memberOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberOrder result = memberOrderRepository.save(memberOrder);
        return ResponseEntity.created(new URI("/api/member-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /member-orders : Updates an existing memberOrder.
     *
     * @param memberOrder the memberOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memberOrder,
     * or with status 400 (Bad Request) if the memberOrder is not valid,
     * or with status 500 (Internal Server Error) if the memberOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/member-orders")
    @Timed
    public ResponseEntity<MemberOrder> updateMemberOrder(@Valid @RequestBody MemberOrder memberOrder) throws URISyntaxException {
        log.debug("REST request to update MemberOrder : {}", memberOrder);
        if (memberOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MemberOrder result = memberOrderRepository.save(memberOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memberOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /member-orders : get all the memberOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of memberOrders in body
     */
    @GetMapping("/member-orders")
    @Timed
    public ResponseEntity<List<MemberOrder>> getAllMemberOrders(Pageable pageable) {
        log.debug("REST request to get a page of MemberOrders");
        Page<MemberOrder> page = memberOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/member-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /member-orders/:id : get the "id" memberOrder.
     *
     * @param id the id of the memberOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memberOrder, or with status 404 (Not Found)
     */
    @GetMapping("/member-orders/{id}")
    @Timed
    public ResponseEntity<MemberOrder> getMemberOrder(@PathVariable Long id) {
        log.debug("REST request to get MemberOrder : {}", id);
        Optional<MemberOrder> memberOrder = memberOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(memberOrder);
    }

    /**
     * DELETE  /member-orders/:id : delete the "id" memberOrder.
     *
     * @param id the id of the memberOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/member-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteMemberOrder(@PathVariable Long id) {
        log.debug("REST request to delete MemberOrder : {}", id);

        memberOrderRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
