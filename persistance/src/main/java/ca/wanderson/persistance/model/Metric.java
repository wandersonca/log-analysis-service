package ca.wanderson.persistance.model;

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

/**
 * Entity bean for the Metric table.
 * @author Will Anderson
 */
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

    /**
     * Getter for id.
     * @return The id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id The id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for name.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name The name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for application.
     * @return The application.
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Setter for application.
     * @param application The application.
     */
    public List<MetricCount> getMetricCounts() {
        return metricCounts;
    }

    /**
     * Setter for application.
     * @param application The application.
     */
    public void setMetricCounts(List<MetricCount> metricCounts) {
        this.metricCounts = metricCounts;
    }

    /**
     * Setter for application.
     * @param application The application.
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Getter for logLevel.
     * @return The logLevel.
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * Setter for logLevel.
     * @param logLevel The logLevel.
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Getter for messageRegex.
     * @return The messageRegex.
     */
    public String getMessageRegex() {
        return messageRegex;
    }

    /**
     * Setter for messageRegex.
     * @param messageRegex The messageRegex.
     */
    public void setMessageRegex(String messageRegex) {
        this.messageRegex = messageRegex;
    }
}
