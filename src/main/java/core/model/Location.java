package core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Organization location.
 * Location has the following fields:
 * <ul>
 *     <li> x - the x coordinate</li>
 *     <li> y -  the y coordinate</li>
 *     <li> z - the z coordinate</li>
 *     <li> name - name of location</li>
 * </ul>
 * The fields have the following constraints:
 * <ul>
 *     <li> x - not null </li>
 *     <li> y - not null </li>
 *     <li> z - not null </li>
 *     <li> name - not blank, not null </li>
 * </ul>
 *  @author Mark Kriger
 *  @since 15/02/2024
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Location {
    @NotNull(message = "location.y.null")
    @JacksonXmlProperty
    @JsonProperty("Y")
    private Integer y;

    @NotNull(message = "location.x.null")
    @JacksonXmlProperty
    @JsonProperty("X")
    private Double x;
    @NotNull(message = "location.z.null")
    @JacksonXmlProperty
    @JsonProperty("Z")
    private Double z;
    @NotBlank(message = "location.name.blank")
    @NotNull(message = "location.name.null")
    @JacksonXmlProperty
    @JsonProperty("Name")
    private String name;


    @Override
    public String toString() {
        return "(" +
               " name: " + name +
               ", x: " + x +
               ", y: " + y +
               ", z: " + z +
               " )";
    }
}