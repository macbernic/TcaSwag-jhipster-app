package io.github.tcaswag.repository;

import io.github.tcaswag.domain.TcaMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TcaMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcaMemberRepository extends JpaRepository<TcaMember, Long> {

}
