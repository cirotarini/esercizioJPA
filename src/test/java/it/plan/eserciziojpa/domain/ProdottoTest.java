package it.plan.eserciziojpa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.plan.eserciziojpa.web.rest.TestUtil;

public class ProdottoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prodotto.class);
        Prodotto prodotto1 = new Prodotto();
        prodotto1.setId(1L);
        Prodotto prodotto2 = new Prodotto();
        prodotto2.setId(prodotto1.getId());
        assertThat(prodotto1).isEqualTo(prodotto2);
        prodotto2.setId(2L);
        assertThat(prodotto1).isNotEqualTo(prodotto2);
        prodotto1.setId(null);
        assertThat(prodotto1).isNotEqualTo(prodotto2);
    }
}
