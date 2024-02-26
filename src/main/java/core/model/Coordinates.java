package core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Worker coordinates.
 * Location has the following fields:
 * <ul>
 *     <li> x - the x coordinate</li>
 *     <li> y - the y coordinate</li>
 * </ul>
 * The fields have the following constraints:
 * <ul>
 *     <li> x - not null, It should be greater than -977 </li>
 *     <li> y - not null, It should be less than 886  </li>
 * </ul>
 *  @author Mark Kriger
 *  @since 15/02/2024
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Coordinates {
    @Min(value = -977, message = "coordinates.x.min")
    @NotNull(message = "coordinates.x.null")
    @JacksonXmlProperty
    @JsonProperty("X")
    private Long x;
    @Max(value = 886, message = "coordinates.y.max")
    @NotNull(message = "coordinates.y.null")
    @JacksonXmlProperty
    @JsonProperty("Y")
    private Double y;

    @Override
    public String toString() {
        return String.format("(%d,%.3f)", x, y);
    }
}