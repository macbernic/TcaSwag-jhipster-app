package io.github.tcaswag.repository;

import io.github.tcaswag.domain.ProductSku;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductSku entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

}
