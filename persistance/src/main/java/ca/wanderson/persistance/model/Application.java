package ca.wanderson.persistance.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

/**
 * Entity bean for the Application table.
 */
@Entity
@Table(name = "Application", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Metric> metrics;

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
     * Getter for description.
     * @return The description.
     */
    public List<Metric> getMetrics() {
        return metrics;
    }

    /**
     * Setter for description.
     * @param description The description.
     */
    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    /**
     * Getter for description.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description.
     * @param description The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * toString Override.
     */
    @Override
    public String toString() {
        return getName() + "("+ getId() +"): " + getDescription();
    }
}
