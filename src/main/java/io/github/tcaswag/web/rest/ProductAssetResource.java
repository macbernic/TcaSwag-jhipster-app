package io.github.tcaswag.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.tcaswag.domain.ProductAsset;
import io.github.tcaswag.repository.ProductAssetRepository;
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
 * REST controller for managing ProductAsset.
 */
@RestController
@RequestMapping("/api")
public class ProductAssetResource {

    private final Logger log = LoggerFactory.getLogger(ProductAssetResource.class);

    private static final String ENTITY_NAME = "productAsset";

    private final ProductAssetRepository productAssetRepository;

    public ProductAssetResource(ProductAssetRepository productAssetRepository) {
        this.productAssetRepository = productAssetRepository;
    }

    /**
     * POST  /product-assets : Create a new productAsset.
     *
     * @param productAsset the productAsset to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productAsset, or with status 400 (Bad Request) if the productAsset has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-assets")
    @Timed
    public ResponseEntity<ProductAsset> createProductAsset(@Valid @RequestBody ProductAsset productAsset) throws URISyntaxException {
        log.debug("REST request to save ProductAsset : {}", productAsset);
        if (productAsset.getId() != null) {
            throw new BadRequestAlertException("A new productAsset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAsset result = productAssetRepository.save(productAsset);
        return ResponseEntity.created(new URI("/api/product-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-assets : Updates an existing productAsset.
     *
     * @param productAsset the productAsset to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productAsset,
     * or with status 400 (Bad Request) if the productAsset is not valid,
     * or with status 500 (Internal Server Error) if the productAsset couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-assets")
    @Timed
    public ResponseEntity<ProductAsset> updateProductAsset(@Valid @RequestBody ProductAsset productAsset) throws URISyntaxException {
        log.debug("REST request to update ProductAsset : {}", productAsset);
        if (productAsset.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAsset result = productAssetRepository.save(productAsset);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productAsset.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-assets : get all the productAssets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productAssets in body
     */
    @GetMapping("/product-assets")
    @Timed
    public ResponseEntity<List<ProductAsset>> getAllProductAssets(Pageable pageable) {
        log.debug("REST request to get a page of ProductAssets");
        Page<ProductAsset> page = productAssetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-assets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /product-assets/:id : get the "id" productAsset.
     *
     * @param id the id of the productAsset to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productAsset, or with status 404 (Not Found)
     */
    @GetMapping("/product-assets/{id}")
    @Timed
    public ResponseEntity<ProductAsset> getProductAsset(@PathVariable Long id) {
        log.debug("REST request to get ProductAsset : {}", id);
        Optional<ProductAsset> productAsset = productAssetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productAsset);
    }

    /**
     * DELETE  /product-assets/:id : delete the "id" productAsset.
     *
     * @param id the id of the productAsset to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductAsset(@PathVariable Long id) {
        log.debug("REST request to delete ProductAsset : {}", id);

        productAssetRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
