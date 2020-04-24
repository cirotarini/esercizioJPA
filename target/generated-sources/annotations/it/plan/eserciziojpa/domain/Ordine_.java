package it.plan.eserciziojpa.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ordine.class)
public abstract class Ordine_ {

	public static volatile SingularAttribute<Ordine, Cliente> cliente;
	public static volatile SingularAttribute<Ordine, Instant> data;
	public static volatile SingularAttribute<Ordine, Long> id;

	public static final String CLIENTE = "cliente";
	public static final String DATA = "data";
	public static final String ID = "id";

}

