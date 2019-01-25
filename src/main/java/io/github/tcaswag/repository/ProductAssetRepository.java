package io.github.tcaswag.repository;

import io.github.tcaswag.domain.ProductAsset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductAsset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAssetRepository extends JpaRepository<ProductAsset, Long> {

}
