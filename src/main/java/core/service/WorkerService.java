package core.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import core.model.Organization;
import core.model.Worker;
import core.service.validation.IValidator;
import core.storage.AuditableCrud;
import core.storage.iml.WorkerDao;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Worker service.
 */
@Service
public class WorkerService  {

    @Autowired
    private final AuditableCrud<Integer,Worker> dao;
    @Autowired
    private final IValidator<Worker> validator;

    /**
     * Instantiates a new Worker service.
     *
     * @param dao the dao
     * @param validator  the validator
     */
    public WorkerService(WorkerDao dao, IValidator<Worker> validator) {
        this.dao = dao;
        this.validator = validator;
    }

    /**
     * Method that get meta-information from repository.
     *
     * @return the string
     */
    public String info() {
        var storage = (PriorityQueue<Worker>) dao.readAll();
        return String.format("Используемый тип для хранения Worker - %s" +
                      "\n Была создана- %tF %tT" +
                      "\n Длина - %d", dao.getCollectionType(), dao.getCreatedDate(), dao.getCreatedDate(), storage.size());
    }

    /**
     * Save workers to file.
     * @return A string describing the success or failure of the operation
     */
    public String save() {
        return dao.dumpToTheFile() ? "Файл успешно сохранен в корне проекта!" : "Произошла ошибка при сохранении";
    }

    /**
     * Add string.
     *
     * @param data the data
     * @return A string describing the success or failure of the operation
     */
    public String add(Worker data) {
        List<String> messages = validator.isValid(data);
        if (messages.size() == 0) {
            dao.add(data);
            return "Данные записаны!";
        }
        return String.join("\n", messages);


    }

    /**
     * Method that calculates avarage of salary.
     *
     * @return avarage of salary
     */
    public double averageOfSalary() {
        OptionalDouble average = dao.readAll()
                .stream()
                .map(Worker::getSalary)
                .mapToDouble(Float::doubleValue)
                .average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }

    /**
     * Method thar .
     *
     * @param worker the worker
     * @return A string describing the success or failure of the operation
     */
    public String update(Worker worker) {
        List<String> messages = validator.isValid(worker);
        return messages.size() == 0 ?
                dao.update(worker) ? "Данные успешно обновлены!" : "id такого worker не существует!"
                : String.join("\n", messages);

    }

    /**
     * A method that includes the logic of deleting the first worker
     *
     * @return A string describing the success or failure of the operation
     */
    public String removeFirst() {
        var storage = (PriorityQueue<Worker>) dao.readAll();
        if (storage.size() == 0) {
            return "Коллекция пуста! Удаления не произошло!";
        }
        storage.poll();
        return "Элемент был удален" ;
    }

    /**
     * Method that deletes workers from collection
     *
     * @return count of deleted workers
     */
    public int clear() {
        var storage = dao.readAll();
        int size = storage.size();
        storage.clear();
        return size;
    }

    /**
     * Remove by id string.
     *
     * @param id the id
     * @return A string describing the success or failure of the operation
     */
    public String removeById(Integer id) {
        return dao.removeById(id)
                ? "Пользователь был удален!"
                : String.format("Пользователя с id=%d не существует.", id);

    }

    /**
     * Add if max salary string.
     *
     * @param w the w
     * @return A string describing the success or failure of the operation
     */
    public String addIfMaxSalary(Worker w) {
        List<String> messages = validator.isValid(w);
        if (messages.size() != 0)
            return "Полученный объект невалидный!";
        var storage = (PriorityQueue<Worker>) dao.readAll();
        Optional<Float> maxSalary = storage.stream()
                .map(Worker::getSalary)
                .max(Float::compareTo);
        if (maxSalary.isPresent()) {
            if (maxSalary.get() < w.getSalary()) {
                dao.add(w);
                return "Добавлен в коллекцию как элемент с максимальной зарплатой";
            }
            return "Воркер отброшен, его зарплата не максимальна";
        }
        dao.add(w);
        return "Первый элемент в коллекции, его зарпалата максимальна";

    }

    /**
     * Add if min salary string.
     *
     * @param worker the worker
     * @return A string describing the success or failure of the operation
     */
    public String addIfMinSalary(Worker worker) {
        List<String> messages = validator.isValid(worker);
        if (messages.size() != 0)
            return "Полученный объект невалидный!";
        var storage = (PriorityQueue<Worker>)dao.readAll();
        Optional<Float> minSalary = storage.stream()
                .map(Worker::getSalary)
                .min(Comparator.naturalOrder());
        if (minSalary.isPresent()) {
            if (minSalary.get() > worker.getSalary()) {
                dao.add(worker);
                return "Добавлен в коллекцию как элемент с минимальной зарплатой";
            }
            return "Воркер отброшен, его зарплата не минимальна";
        }
        dao.add(worker);
        return "Первый элемент в коллекции, его зарпалата минимальна";

    }

    /**
     * show all workers to the client.
     *
     *
     * @return string with information about all workers
     */
    public String show() {
        return Arrays.stream(dao.readAll()
                .toArray(new Worker[0])).sorted(Worker::compareTo)
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Print field descending organization string.
     *
     * @return the string
     */
    public String printFieldDescendingOrganization() {
        return dao.readAll()
                .stream()
                .map(Worker::getOrganization)
                .sorted(Organization::compareTo)
                .map(Organization::toString)
                .collect(Collectors.joining("\n"));


    }

    /**
     * Print field ascending status string.
     *
     * @return the string
     */
    public String printFieldAscendingStatus() {
        return dao.readAll()
                .stream()
                .map(worker -> worker.getStatus()
                        .toString())
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining("\n"));
    }
}