package ec.finalproject.persistance.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date interval;

    @ManyToOne
    private Metric metric;

    @NotNull
    private int count;

    public MetricCount(Date date, Metric metric, int count) {
        this.interval = date;
        this.metric = metric;
        this.count = count;
    }

    public void setInterval(Date interval) {
        this.interval = interval;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getInterval() {
        return interval;
    }

    public Metric getMetric() {
        return metric;
    }

    public int getCount() {
        return count;
    }
}
