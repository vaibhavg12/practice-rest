package vb.practice.rest.spring.boot.service;

import vb.practice.rest.spring.boot.model.Target;

/**
 * API for business logic related to a {@link Target}.
 */
public interface TargetService {

    /**
     * Updates an information for an existing {@link Target}.
     *
     * @param id          the id of the target, never null
     * @param information the information to be updated, never null
     * @return number of rows that were updated, 0 otherwise
     */
    Integer updateTarget(Long id, byte[] information);

    /**
     * Returns a {@link Target}.
     *
     * @param id the id of the target to be returned, never null
     * @return target associated with given id, null otherwise
     */
    Target findTarget(Long id);
}
