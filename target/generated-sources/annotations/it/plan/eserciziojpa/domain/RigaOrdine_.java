package it.plan.eserciziojpa.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RigaOrdine.class)
public abstract class RigaOrdine_ {

	public static volatile SingularAttribute<RigaOrdine, Ordine> ordine;
	public static volatile SingularAttribute<RigaOrdine, Long> id;
	public static volatile SingularAttribute<RigaOrdine, Integer> quantita;
	public static volatile SingularAttribute<RigaOrdine, Prodotto> prodotto;

	public static final String ORDINE = "ordine";
	public static final String ID = "id";
	public static final String QUANTITA = "quantita";
	public static final String PRODOTTO = "prodotto";

}

