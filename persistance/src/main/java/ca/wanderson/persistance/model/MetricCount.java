package ca.wanderson.persistance.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Entity bean for the MetricCount table.
 * @author Will Anderson
 */
@Entity
@Table(name = "MetricCount")
public class MetricCount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeInterval;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "metric_id")
    @NotNull
    private Metric metric;

    @NotNull
    private int count;

    /**
     * Default constructor.
     */
    public MetricCount() {
    }

    /**
     * Constructor used within application.
     * @param id The id.
     * @param timeInterval The time interval.
     * @param metric The metric.
     * @param count The count.
     */
    public MetricCount(String id, Date timeInterval, Metric metric, int count) {
        this.id = id;
        this.timeInterval = timeInterval;
        this.metric = metric;
        this.count = count;
    }

    /**
     * Getter for id.
     * @return The id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for id.
     * @return The id.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for timeInterval.
     * @return The time interval.
     */
    public void setTimeInterval(Date timeInterval) {
        this.timeInterval = timeInterval;
    }

    /**
     * Getter for timeInterval.
     * @return The time interval.
     */
    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    /**
     * Getter for timeInterval.
     * @return The time interval.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Getter for timeInterval.
     * @return The time interval.
     */
    public Date getTimeInterval() {
        return timeInterval;
    }

    /**
     * Getter for metric.
     * @return The metric.
     */
    public Metric getMetric() {
        return metric;
    }

    /**
     * Getter for count.
     * @return The count.
     */
    public int getCount() {
        return count;
    }
}
