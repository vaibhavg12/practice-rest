package vb.practice.rest.spring.boot.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import vb.practice.rest.spring.boot.model.Target;
import vb.practice.rest.spring.boot.repository.TargetRepository;
import vb.practice.rest.spring.boot.service.internal.TargetServiceImpl;
import vb.practice.rest.spring.boot.utils.MessageUtil;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class TargetServiceImplTest {

    @Mock
    private TargetRepository mockRepository;
    @Mock
    private Target mockTarget;

    private TargetService targetService;

    @Before
    public void setup() {
        targetService = new TargetServiceImpl(mockRepository);
    }

    /**
     * Test for {@link TargetService#findTarget(Long)}, when id is null.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test(expected = NullPointerException.class)
    public void testFindTargetNullId() {
        try {
            targetService.findTarget(null);
        } catch (NullPointerException npe) {
            Assert.assertEquals(MessageUtil.TARGET_ID_NULL, npe.getMessage());
            throw npe;
        }
    }

    /**
     * Test for {@link TargetService#findTarget(Long)}, when no target is found.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testFindTargetReturnsNull() {
        Assert.assertNull(targetService.findTarget(1L));
    }

    /**
     * Test for {@link TargetService#findTarget(Long)}, when an associated target is found.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testFindTarget() {
        Mockito.when(mockRepository.findById(1L)).thenReturn(java.util.Optional.of(mockTarget));
        Assert.assertEquals(mockTarget, targetService.findTarget(1L));
    }

    /**
     * Test for {@link TargetService#updateTarget(Long, byte[])}, when id is null.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test(expected = NullPointerException.class)
    public void testUpdateTargetNullId() {
        try {
            targetService.updateTarget(null, "hello".getBytes());
        } catch (NullPointerException npe) {
            Assert.assertEquals(MessageUtil.TARGET_ID_NULL, npe.getMessage());
            throw npe;
        }
    }

    /**
     * Test for {@link TargetService#updateTarget(Long, byte[])}, when information is null.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test(expected = NullPointerException.class)
    public void testUpdateTargetNullInformation() {
        try {
            targetService.updateTarget(1L, null);
        } catch (NullPointerException npe) {
            Assert.assertEquals(MessageUtil.INFORMATION_NULL, npe.getMessage());
            throw npe;
        }
    }

    /**
     * Test for {@link TargetService#updateTarget(Long, byte[])}, when target is successfully updated.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testUpdateTarget() throws SQLException {
        byte[] information = "hello".getBytes();
        Blob blob = new SerialBlob(information);
        Mockito.when(mockRepository.findById(1L)).thenReturn(java.util.Optional.of(mockTarget));
        Mockito.when(mockRepository.update(1L, blob)).thenReturn(1);

        int expected = targetService.updateTarget(1L, information);
        Assert.assertEquals(expected, 1);
    }
}
