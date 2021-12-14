package ec.finalproject.persistance.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "Metric", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Metric implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @ManyToOne
    private Application application;

    private String logLevel;

    private String messageRegex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getMessageRegex() {
        return messageRegex;
    }

    public void setMessageRegex(String messageRegex) {
        this.messageRegex = messageRegex;
    }
}
