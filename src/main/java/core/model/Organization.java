package core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * The entity of the organization in which the worker works.
 * Organization has the following fields:
 * <ul>
 *     <li> fullName - name of organization</li>
 *     <li> type -  type of organization.</li>
 *     <li> postalAddress - address </li>
 * </ul>
 * The fields have the following constraints:
 * <ul>
 *     <li> fullName - name should not be longer 762</li>
 * </ul>
 *  @author Mark Kriger
 *  @since 15/02/2024
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor

public class Organization implements Comparable<Organization> {


    @Size(max = 762, message = "organization.fullName.size")
    @JacksonXmlProperty
    @JsonProperty("FullName")
    private String fullName;
    @JacksonXmlProperty
    @JsonProperty("Type")
    private OrganizationType type;
    @JacksonXmlProperty
    @JsonProperty("PostalAddress")
    private Address postalAddress;


    @Override
    public String toString() {
        return
                "\t\tfullName: " + fullName +
                "\n\t\ttype: " + type +
                "\n\t\tpostalAddress:\n " + postalAddress;
    }

    @Override
    public int compareTo(Organization o) {
        return o.fullName.compareTo(this.fullName);
    }
}
