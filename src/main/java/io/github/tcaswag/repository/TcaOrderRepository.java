package io.github.tcaswag.repository;

import io.github.tcaswag.domain.TcaOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TcaOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcaOrderRepository extends JpaRepository<TcaOrder, Long> {

}
