package io.github.tcaswag.repository;

import io.github.tcaswag.domain.MemberOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MemberOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberOrderRepository extends JpaRepository<MemberOrder, Long> {

}
