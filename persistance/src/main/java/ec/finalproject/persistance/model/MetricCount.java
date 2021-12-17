package ec.finalproject.persistance.model;

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

    public MetricCount() {
    }

    public MetricCount(String id, Date timeInterval, Metric metric, int count) {
        this.id = id;
        this.timeInterval = timeInterval;
        this.metric = metric;
        this.count = count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTimeInterval(Date timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getTimeInterval() {
        return timeInterval;
    }

    public Metric getMetric() {
        return metric;
    }

    public int getCount() {
        return count;
    }
}
