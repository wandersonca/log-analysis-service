package ec.finalproject.persistance.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Metric.class)
public abstract class Metric_ {

	public static volatile SingularAttribute<Metric, Application> application;
	public static volatile SingularAttribute<Metric, String> logLevel;
	public static volatile SingularAttribute<Metric, String> name;
	public static volatile SingularAttribute<Metric, String> messageRegex;
	public static volatile SingularAttribute<Metric, Long> id;

	public static final String APPLICATION = "application";
	public static final String LOG_LEVEL = "logLevel";
	public static final String NAME = "name";
	public static final String MESSAGE_REGEX = "messageRegex";
	public static final String ID = "id";

}

