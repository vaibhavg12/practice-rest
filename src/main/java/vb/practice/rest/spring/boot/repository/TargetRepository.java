package vb.practice.rest.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vb.practice.rest.spring.boot.model.Target;

import java.sql.Blob;

/**
 * Repository to handle target related database operations.
 */
public interface TargetRepository extends JpaRepository<Target, Long> {

    /**
     * Updates a target with provided target id.
     *
     * @param id          id of the target to be updated, never null
     * @param information information to be updated
     * @return the number of rows updated by query
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Target t SET t.information=:information WHERE t.id=:id")
    Integer update(@Param("id") Long id, @Param("information") Blob information);
}
