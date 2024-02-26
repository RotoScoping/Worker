package core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Organization address.
 * Location has the following fields:
 * <ul>
 *     <li> zipCode - mail code</li>
 *     <li> town - organization location</li>
 * </ul>
 * The fields have the following constraints:
 * <ul>
 *     <li> zipCode - not null</li>
 *     <li> town - not null</li>
 * </ul>
 *  @author Mark Kriger
 *  @since 15/02/2024
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Address {
    @NotNull(message = "address.zipCode.null")
    @JacksonXmlProperty
    @JsonProperty("ZipCode")
    private String zipCode;
    @NotNull(message = "address.town.null" )
    @JacksonXmlProperty
    @JsonProperty("Town")
    private Location town;

    @Override
    public String toString() {
        return "\t\t\t" +
               "zipCode: " + zipCode +
               "\n\t\t\t town: " + town;
    }
}