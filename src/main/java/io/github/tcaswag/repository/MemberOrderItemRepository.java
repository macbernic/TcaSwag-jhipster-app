package io.github.tcaswag.repository;

import io.github.tcaswag.domain.MemberOrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MemberOrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberOrderItemRepository extends JpaRepository<MemberOrderItem, Long> {

}
