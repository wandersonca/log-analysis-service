package ec.finalproject.persistance.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Application.class)
public abstract class Application_ {

	public static volatile SingularAttribute<Application, String> name;
	public static volatile SingularAttribute<Application, String> description;
	public static volatile SingularAttribute<Application, Long> id;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

