package core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * The main entity in the business logic of the service.
 * Worker has the following fields:
 * <ul>
 *     <li> id - is the user's ID. The numbering starts from 1 and is generated automatically.</li>
 *     <li> name -  the name of the worker.</li>
 *     <li> coordinates - the location of the worker.</li>
 *     <li> сreationDate - the date the worker was added to the storage, generated automatically.</li>
 *     <li> startDate - the start date of the worker's work in the organization.</li>
 *     <li> position - the vacancy for which the worker was accepted.</li>
 *     <li> status - the status of the worker in the company.</li>
 *     <li> organization - the organization in which worker works.</li>
 * </ul>
 * The fields have the following constraints:
 * <ul>
 *     <li> id - The field value must be greater than 0, unique </li>
 *     <li> name - Not null, not empty </li>
 *     <li> coordinates - Not null </li>
 *     <li> creationDate - Not null </li>
 *     <li> salary - positive number </li>
 *     <li> startDate - Not null </li>
 *     <li> position - Not null </li>
 *     <li> status - Not null </li>
 * </ul>
 * @author Mark Kriger
 * @since 15/02/2024
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Worker implements Comparable<Worker> {

    @JsonProperty("Id")
    private int id;

    @NotNull(message = "worker.name.null")
    @NotEmpty(message = "worker.name.empty")
    @JacksonXmlProperty
    @JsonProperty("Name")
    private String name;

    @NotNull(message = "worker.coordinates.null")
    @JacksonXmlProperty
    @JsonProperty("Coordinates")
    private Coordinates coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @Positive(message = "worker.salary.positive")
    @JacksonXmlProperty
    @JsonProperty("Salary")
    private float salary;

    @NotNull(message = "worker.startDate.null")
    @JacksonXmlProperty
    @JsonProperty("StartDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date startDate;

    @NotNull(message = "worker.position.null")
    @JacksonXmlProperty
    @JsonProperty("Position")
    private Position position;

    @NotNull(message = "worker.status.null")
    @JacksonXmlProperty
    @JsonProperty("Status")
    private Status status;

    @JacksonXmlProperty
    @JsonProperty("Organization")
    private Organization organization;


    /**
     * @return Worker's fields as formatted string value
     */
    @Override
    public String toString() {
        return String.format("""
                Worker (id = %d):
                \tname: %s
                \tcoordinates: %s
                \tcreationDate: %tF
                \tsalary: %f
                \tstartDate: %tF
                \tposition: %s
                \tstatus: %s
                \torganization:
                %s""", id, name, coordinates, creationDate, salary, startDate, position, status, organization);
    }


    /**
     * Default comparison by id value
     * @param o the worker to be compared.
     * @return comparison result
     */
    @Override
    public int compareTo(Worker o) {
        return Integer.compare(this.id, o.id);
    }
}





