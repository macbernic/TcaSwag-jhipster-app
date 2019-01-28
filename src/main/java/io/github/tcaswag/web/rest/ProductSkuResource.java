package io.github.tcaswag.web.rest;
import io.github.tcaswag.domain.ProductSku;
import io.github.tcaswag.repository.ProductSkuRepository;
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
 * REST controller for managing ProductSku.
 */
@RestController
@RequestMapping("/api")
public class ProductSkuResource {

    private final Logger log = LoggerFactory.getLogger(ProductSkuResource.class);

    private static final String ENTITY_NAME = "productSku";

    private final ProductSkuRepository productSkuRepository;

    public ProductSkuResource(ProductSkuRepository productSkuRepository) {
        this.productSkuRepository = productSkuRepository;
    }

    /**
     * POST  /product-skus : Create a new productSku.
     *
     * @param productSku the productSku to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productSku, or with status 400 (Bad Request) if the productSku has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-skus")
    public ResponseEntity<ProductSku> createProductSku(@Valid @RequestBody ProductSku productSku) throws URISyntaxException {
        log.debug("REST request to save ProductSku : {}", productSku);
        if (productSku.getId() != null) {
            throw new BadRequestAlertException("A new productSku cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSku result = productSkuRepository.save(productSku);
        return ResponseEntity.created(new URI("/api/product-skus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-skus : Updates an existing productSku.
     *
     * @param productSku the productSku to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productSku,
     * or with status 400 (Bad Request) if the productSku is not valid,
     * or with status 500 (Internal Server Error) if the productSku couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-skus")
    public ResponseEntity<ProductSku> updateProductSku(@Valid @RequestBody ProductSku productSku) throws URISyntaxException {
        log.debug("REST request to update ProductSku : {}", productSku);
        if (productSku.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSku result = productSkuRepository.save(productSku);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productSku.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-skus : get all the productSkus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productSkus in body
     */
    @GetMapping("/product-skus")
    public List<ProductSku> getAllProductSkus() {
        log.debug("REST request to get all ProductSkus");
        return productSkuRepository.findAll();
    }

    /**
     * GET  /product-skus/:id : get the "id" productSku.
     *
     * @param id the id of the productSku to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productSku, or with status 404 (Not Found)
     */
    @GetMapping("/product-skus/{id}")
    public ResponseEntity<ProductSku> getProductSku(@PathVariable Long id) {
        log.debug("REST request to get ProductSku : {}", id);
        Optional<ProductSku> productSku = productSkuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productSku);
    }

    /**
     * DELETE  /product-skus/:id : delete the "id" productSku.
     *
     * @param id the id of the productSku to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-skus/{id}")
    public ResponseEntity<Void> deleteProductSku(@PathVariable Long id) {
        log.debug("REST request to delete ProductSku : {}", id);
        productSkuRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
