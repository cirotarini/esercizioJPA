package it.plan.eserciziojpa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.plan.eserciziojpa.web.rest.TestUtil;

public class RigaOrdineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RigaOrdine.class);
        RigaOrdine rigaOrdine1 = new RigaOrdine();
        rigaOrdine1.setId(1L);
        RigaOrdine rigaOrdine2 = new RigaOrdine();
        rigaOrdine2.setId(rigaOrdine1.getId());
        assertThat(rigaOrdine1).isEqualTo(rigaOrdine2);
        rigaOrdine2.setId(2L);
        assertThat(rigaOrdine1).isNotEqualTo(rigaOrdine2);
        rigaOrdine1.setId(null);
        assertThat(rigaOrdine1).isNotEqualTo(rigaOrdine2);
    }
}
