package io.github.tcaswag.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.tcaswag.domain.MemberOrderItem;
import io.github.tcaswag.repository.MemberOrderItemRepository;
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
 * REST controller for managing MemberOrderItem.
 */
@RestController
@RequestMapping("/api")
public class MemberOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(MemberOrderItemResource.class);

    private static final String ENTITY_NAME = "memberOrderItem";

    private final MemberOrderItemRepository memberOrderItemRepository;

    public MemberOrderItemResource(MemberOrderItemRepository memberOrderItemRepository) {
        this.memberOrderItemRepository = memberOrderItemRepository;
    }

    /**
     * POST  /member-order-items : Create a new memberOrderItem.
     *
     * @param memberOrderItem the memberOrderItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memberOrderItem, or with status 400 (Bad Request) if the memberOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/member-order-items")
    @Timed
    public ResponseEntity<MemberOrderItem> createMemberOrderItem(@Valid @RequestBody MemberOrderItem memberOrderItem) throws URISyntaxException {
        log.debug("REST request to save MemberOrderItem : {}", memberOrderItem);
        if (memberOrderItem.getId() != null) {
            throw new BadRequestAlertException("A new memberOrderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberOrderItem result = memberOrderItemRepository.save(memberOrderItem);
        return ResponseEntity.created(new URI("/api/member-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /member-order-items : Updates an existing memberOrderItem.
     *
     * @param memberOrderItem the memberOrderItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memberOrderItem,
     * or with status 400 (Bad Request) if the memberOrderItem is not valid,
     * or with status 500 (Internal Server Error) if the memberOrderItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/member-order-items")
    @Timed
    public ResponseEntity<MemberOrderItem> updateMemberOrderItem(@Valid @RequestBody MemberOrderItem memberOrderItem) throws URISyntaxException {
        log.debug("REST request to update MemberOrderItem : {}", memberOrderItem);
        if (memberOrderItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MemberOrderItem result = memberOrderItemRepository.save(memberOrderItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memberOrderItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /member-order-items : get all the memberOrderItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of memberOrderItems in body
     */
    @GetMapping("/member-order-items")
    @Timed
    public ResponseEntity<List<MemberOrderItem>> getAllMemberOrderItems(Pageable pageable) {
        log.debug("REST request to get a page of MemberOrderItems");
        Page<MemberOrderItem> page = memberOrderItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/member-order-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /member-order-items/:id : get the "id" memberOrderItem.
     *
     * @param id the id of the memberOrderItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memberOrderItem, or with status 404 (Not Found)
     */
    @GetMapping("/member-order-items/{id}")
    @Timed
    public ResponseEntity<MemberOrderItem> getMemberOrderItem(@PathVariable Long id) {
        log.debug("REST request to get MemberOrderItem : {}", id);
        Optional<MemberOrderItem> memberOrderItem = memberOrderItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(memberOrderItem);
    }

    /**
     * DELETE  /member-order-items/:id : delete the "id" memberOrderItem.
     *
     * @param id the id of the memberOrderItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/member-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteMemberOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete MemberOrderItem : {}", id);

        memberOrderItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
