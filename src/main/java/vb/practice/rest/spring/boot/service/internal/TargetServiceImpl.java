package vb.practice.rest.spring.boot.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vb.practice.rest.spring.boot.model.Target;
import vb.practice.rest.spring.boot.repository.TargetRepository;
import vb.practice.rest.spring.boot.service.TargetService;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static vb.practice.rest.spring.boot.utils.MessageUtil.INFORMATION_NULL;
import static vb.practice.rest.spring.boot.utils.MessageUtil.TARGET_ID_NULL;

/**
 * Implementation of {@link TargetService}.
 */
@Service
@Transactional
public class TargetServiceImpl implements TargetService {

    @Autowired
    private final TargetRepository targetRepository;

    /**
     * Constructor.
     *
     * @param targetRepository instance of {@link TargetRepository}, never null
     */
    public TargetServiceImpl(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    @Override
    public Integer updateTarget(Long id, byte[] information) {
        if (id == null) {
            throw new NullPointerException(TARGET_ID_NULL);
        }
        if (information == null) {
            throw new NullPointerException(INFORMATION_NULL);
        }
        if (findTarget(id) == null) {
            Logger.getLogger(TargetServiceImpl.class.getName()).log(Level.WARNING, "Target not present");
            return 0;
        }
        try {
            return targetRepository.update(id, new SerialBlob(information));
        } catch (SQLException sqlEx) {
            Logger.getLogger(TargetServiceImpl.class.getName()).log(Level.WARNING, "Exception occurred while parsing byte array to blob");
            return 0;
        }
    }

    @Override
    public Target findTarget(Long id) {
        if (id == null) {
            throw new NullPointerException(TARGET_ID_NULL);
        }
        return targetRepository.findById(id).orElse(null);
    }

}
