package ec.finalproject.persistance.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id")
    private Application application;

    @OneToMany(mappedBy = "metric", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<MetricCount> metricCounts;

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

    public List<MetricCount> getMetricCounts() {
        return metricCounts;
    }

    public void setMetricCounts(List<MetricCount> metricCounts) {
        this.metricCounts = metricCounts;
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
