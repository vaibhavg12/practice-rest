package vb.practice.rest.spring.boot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vb.practice.rest.spring.boot.model.Target;
import vb.practice.rest.spring.boot.rest.exception.NoTargetFoundException;
import vb.practice.rest.spring.boot.rest.exception.PayloadSizeException;
import vb.practice.rest.spring.boot.rest.exception.PreConditionException;
import vb.practice.rest.spring.boot.service.TargetService;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * This class defines the REST interfaces for {@link Target}.
 */
@Controller
@RestController
@RequestMapping(value = "/target")
public class TargetController {

    private final static long MAX_SIZE = 4 * 1024 * 1024; // 4 MB
    private static final String INVALID_TARGET_ID = "Id of target cannot less than 1";
    @Autowired
    private final TargetService targetService;

    /**
     * constructor
     *
     * @param targetService autowired instance of {@link TargetService}.
     */
    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    /**
     * Retrieves {@link Target} associated with given target id.
     *
     * @param id the id of the target to be retrieved, never <code>null</code>
     * @return the target if exists, null otherwise
     * @throws PreConditionException  if path variable is <code>null</code> or less than 1
     * @throws NoTargetFoundException if no target is found
     */
    @GetMapping(value = "/retrieve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Target retrieveTarget(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new PreConditionException(INVALID_TARGET_ID);
        }

        Target target = targetService.findTarget(id);
        if (target == null) {
            String message = String.format("No target found with %d", id);
            throw new NoTargetFoundException(message);
        } else {
            return target;
        }
    }

    /**
     * Updates {@link Target} with given information
     *
     * @param id            the id of the target to be updated, never <code>null</code>
     * @param requestParams map of body parameters, can be empty
     * @return number of rows that were updated in the table
     * @throws PreConditionException if path variable is <code>null</code> or less than 1
     * @throws PayloadSizeException  if payload is larger than 4 MB
     */
    @PostMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Integer updateTarget(@PathVariable("id") Long id, @RequestBody Map<String, String> requestParams) {
        if (id < 1) {
            throw new PreConditionException(INVALID_TARGET_ID);
        }
        if (requestParams.get("information") == null) {
            throw new PreConditionException("Request body does not contain any information");
        }

        // possible future enhancement :-
        // implement paging using spring data pageable and use that to create a blob and store it eventually.
        byte[] information = requestParams.get("information").getBytes(StandardCharsets.UTF_8);
        if (information.length > MAX_SIZE) {
            throw new PayloadSizeException("Payload cannot be larger than 4 MB");
        } else {
            return targetService.updateTarget(id, information);
        }
    }

}
