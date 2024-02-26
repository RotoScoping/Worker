package core.storage.loader.iml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import core.storage.loader.ILoader;
import org.springframework.stereotype.Component;
import core.model.Worker;
import core.model.Workers;

import java.io.*;
import java.util.PriorityQueue;

/**
 * Implementation @see core.storage.loader.ILoader .
 */
@Component
public class FileLoader implements ILoader<PriorityQueue<Worker>> {
    private static final String PATH = System.getProperty("file_path");

    @Override
    public boolean save(PriorityQueue<Worker> collection) {
        ObjectMapper mapper = new XmlMapper().registerModule(new JavaTimeModule());
        Workers workers = new Workers();
        workers.setWorkers(collection);
        try {
            mapper.writeValue(new BufferedOutputStream(new FileOutputStream("save.xml")), workers);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public PriorityQueue<Worker> load()  {
        XmlMapper xmlMapper = new XmlMapper();
        Workers workers = new Workers();
        try {
            workers = xmlMapper.readValue(new InputStreamReader(new FileInputStream(PATH)), Workers.class);
        } catch (IOException e) {
            System.out.println("oops!");
        }
        return workers.getWorkers();

    }
}
