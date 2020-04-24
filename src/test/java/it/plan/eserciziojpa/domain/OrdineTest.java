package it.plan.eserciziojpa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.plan.eserciziojpa.web.rest.TestUtil;

public class OrdineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordine.class);
        Ordine ordine1 = new Ordine();
        ordine1.setId(1L);
        Ordine ordine2 = new Ordine();
        ordine2.setId(ordine1.getId());
        assertThat(ordine1).isEqualTo(ordine2);
        ordine2.setId(2L);
        assertThat(ordine1).isNotEqualTo(ordine2);
        ordine1.setId(null);
        assertThat(ordine1).isNotEqualTo(ordine2);
    }
}
