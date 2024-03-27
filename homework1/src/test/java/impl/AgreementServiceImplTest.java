package impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ru.otus.bank.dao.AgreementDao;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.impl.AgreementServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class AgreementServiceImplTest {

    private AgreementDao dao = mock(AgreementDao.class);

    AgreementServiceImpl agreementServiceImpl;

    @BeforeEach
    public void init() {
        agreementServiceImpl = new AgreementServiceImpl(dao);
    }

    @Test
    public void testFindByName() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        when(dao.findByName(name)).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(name);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(10, agreement.getId());
    }

    @Test
    public void testFindByNameWithCaptor() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(dao.findByName(captor.capture())).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(name);

        Assertions.assertEquals("test", captor.getValue());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(10, agreement.getId());
    }

    @Test
    public void testAddAgreement() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        ArgumentMatcher<Agreement> matcher = new ArgumentMatcher<Agreement>() {

            @Override
            public boolean matches(Agreement argument) {
                return argument != null && "test".equals(argument.getName());
            }
        };

        when(dao.save(argThat(matcher)))
                .thenReturn(agreement);

        Agreement result = agreementServiceImpl.addAgreement(name);
        Assertions.assertEquals(10, agreement.getId());
    }

}
