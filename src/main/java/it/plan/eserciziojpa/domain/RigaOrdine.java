package it.plan.eserciziojpa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RigaOrdine.
 */
@Entity
@Table(name = "riga_ordine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RigaOrdine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantita", nullable = false)
    private Integer quantita;

    @ManyToOne
    @JsonIgnoreProperties("rigaOrdines")
    private Ordine ordine;

    @ManyToOne
    @JsonIgnoreProperties("rigaOrdines")
    private Prodotto prodotto;

    public RigaOrdine() {
    }

    public RigaOrdine(@NotNull Integer quantita, Ordine ordine, Prodotto prodotto) {
        this.quantita = quantita;
        this.ordine = ordine;
        this.prodotto = prodotto;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public RigaOrdine quantita(Integer quantita) {
        this.quantita = quantita;
        return this;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public RigaOrdine ordine(Ordine ordine) {
        this.ordine = ordine;
        return this;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public RigaOrdine prodotto(Prodotto prodotto) {
        this.prodotto = prodotto;
        return this;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RigaOrdine)) {
            return false;
        }
        return id != null && id.equals(((RigaOrdine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RigaOrdine{" +
            "id=" + getId() +
            ", quantita=" + getQuantita() +
            "}";
    }
}
