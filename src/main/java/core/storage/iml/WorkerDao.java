package core.storage.iml;

import core.storage.AuditableCrud;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import core.model.Worker;
import core.storage.loader.ILoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.PriorityQueue;


/**
 * Class managing a collection of workers (dao)
 */
@Component
public class WorkerDao implements AuditableCrud<Integer, Worker> {


    @Getter
    private final PriorityQueue<Worker> storage;

    @Getter
    private final LocalDateTime createdAt;


    private final ILoader<PriorityQueue<Worker>> loader;

    /**
     * Instantiates a new Worker repository.
     * @param loader the loader
     */
    @Autowired
    public WorkerDao(ILoader<PriorityQueue<Worker>> loader) {
        this.loader = loader;
        storage = new PriorityQueue<>((w1,w2) -> w2.getId() - w1.getId());
        createdAt = LocalDateTime.now();
        Init(loader);
    }

    /**
     * A method that initializes a collection of workers using the ILoader<PriorityQueue<Worker>> interface implementation.
     * @param loader the loader
     */
    private void Init(ILoader<PriorityQueue<Worker>> loader)  {
        PriorityQueue<Worker> data = loader.load();
        for (Worker worker : data) {
            add(worker);
        }
    }



    /**
     * Method that add worker to the storage
     * @param worker the worker
     */
    public void add(Worker worker) {
        worker.setId(storage.size()+1);
        worker.setCreationDate(LocalDate.now());
        storage.add(worker);

    }

    /**
     * Method that returns all workers
     * @return collection of workers
     */
    @Override
    public PriorityQueue<Worker> readAll() {
        return storage;
    }


    /**
     * Method that updates worker in collection.
     * <br> That
     *
     * @param worker the worker
     * @return boolean result of operation
     */
    public boolean update(Worker worker) {
        for (Worker el : storage) {
            if (el.getId() == worker.getId()) {
                removeById(worker.getId());
                storage.add(worker);
                return true;
            }
        }
        return false;
    }




    /**
     * Method that removes worker from collection by id.
     * @param id the id
     * @return boolean result of operation
     */
    public boolean removeById(Integer id) {
        if (id < 1 || id > storage.size()) {
            return false;
        }
        boolean res = storage.removeIf(worker -> worker.getId() == id);
        regenerateId(id);
        return res;
    }



    /**
     * Method that regenerates id when worker deletes from collection.
     * <br> Example: we have workers with id 1, 2, 3
     * <br> When we delete worker with id 2, worker with id=3 must be with id=2
     * @param id the id
     * @return boolean result of operation
     */
    private void regenerateId(int id) {


        PriorityQueue<Worker> temp = new PriorityQueue<>();
        while (!storage.isEmpty()) {
            Worker worker = storage.poll();
            int oldId = worker.getId();
            if (oldId > id)
                worker.setId(oldId-1);
            temp.add(worker);
        }

        storage.addAll(temp);
    }


    /**
     * Method that dumps workers to file using implementation the ILoader<PriorityQueue<Worker>> interface implementation
     */
    public boolean dumpToTheFile() {
        return loader.save(storage);
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdAt;
    }

    @Override
    public String getCollectionType() {
        return storage.getClass()
                .getSimpleName();
    }
}
