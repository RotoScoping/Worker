package core.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.PriorityQueue;

/**
 * A simple object for mapping a set of workers to/from xml
 */
@Getter
@Setter
@JacksonXmlRootElement(localName = "workers")
@AllArgsConstructor
@NoArgsConstructor
public class Workers {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "worker")
    private PriorityQueue<Worker> workers;

}
