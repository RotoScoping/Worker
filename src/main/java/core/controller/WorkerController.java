package core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import core.model.Worker;
import core.service.WorkerService;


/**
 * SpringBoot RestController that creates all service end-points related with worker service.
 *
 * @author Mark Kriger
 * @since 15/02/2024
 */
@RestController
public class WorkerController {
    private final WorkerService workerService;
    private final ApplicationContext context;

    /**
     * Instantiates a new Worker controller.
     *
     * @param workerService the worker service
     * @param context       the context
     */
    @Autowired
    public WorkerController(WorkerService workerService, ApplicationContext context) {
        this.workerService = workerService;
        this.context = context;

    }

    /**
     * Method that sends a response to the client in the form of a string containing information:
     * <br> - The type of storage used
     * <br> - Creation time
     * <br> - The number of objects in the storage
     * @return A String value with information about the storage
     */
    @GetMapping(value = "/info")
    public String info() {
        return workerService.info();
    }

    /**
     * Method that clears the collection of workers.
     * @return A String value with information about the number of deleted workers
     */
    @GetMapping(value = "/clear")
    public String clear() {
        return String.format("Было удалено %d элементов\n" ,workerService.clear());
    }

    /**
     * Method that adds the worker to the storage.
     * @param worker the worker
     * @return string
     */
    @PostMapping("/add")
    public String add(@RequestBody Worker worker) {
        return workerService.add(worker);
    }

    /**
     * Method that save the workers to the file.
     * @return string information about saving
     */

    @GetMapping(value = "/save")
    public String save() {
        return workerService.save();
    }

    /**
     * Method that updates worker in service.
     *
     * @param worker the worker
     * @return A String value with information about the storage
     */

    @PostMapping( "/update_id")
    public String updateId(@RequestBody Worker worker) {
         return workerService.update(worker);

    }

    /**
     * Method thar removes worker by id in service
     *
     * @param id the id
     * @return the string
     */


    @GetMapping(value = "/remove_by_id")
    public String removeById(@RequestParam("id") String id) {
        return workerService.removeById(Integer.valueOf(id));
    }

    /**
     * Remove first string.
     * @return the string
     */
    @GetMapping(value = "/remove_first")
    public String removeFirst() {
        return workerService.removeFirst();
    }

    /**
     * Add if min string.
     *
     * @param worker the worker
     * @return A string describing the success or failure of the operation
     */
    @PostMapping(value = "/add_if_min")
    public String addIfMin(@RequestBody Worker worker) {
        return workerService.addIfMinSalary(worker);
    }

    /**
     * Add if max string.
     *
     * @param worker the worker
     * @return A string describing the success or failure of the operation
     */


    @PostMapping(value = "/add_if_max")
    public String addIfMax(@RequestBody Worker worker) {
        return workerService.addIfMaxSalary(worker);
    }


    /**
     * Close ApplicationContext. Stopping service.
     */
    @GetMapping(value = "/exit")
    public void exit() {
        int exitCode = SpringApplication.exit(context,  () -> 0);
        System.exit(exitCode);
    }

    /**
     * Sends a response to the client in the form of a string containing information:
     * - The type of collection used
     * -
     *
     * @return A string that contains information about all the workers in the service
     */


    @GetMapping(value = "/show")
    public String show() {
        return workerService.show();
    }

    /**
     * Method that gets average of salary in worker.
     *
     * @return
     */

    @GetMapping(value = "/average_of_salary")
    public String averageOfSalary() {
        return String.format("Средняя зарплата по больнице - %f", workerService.averageOfSalary());
    }

    /**
     * Print field ascending status string.
     *
     * @return string with status ASC sort
     */

    @GetMapping(value = "/print_field_ascending_status")
    public String printFieldAscendingStatus() {
        return workerService.printFieldAscendingStatus();
    }



    /**
     * Print field descending organization string.
     *
     * @return string with organizations DESC sort
     */


    @GetMapping(value = "/print_field_descending_organization")
    public String printFieldDescendingOrganization() {
        return workerService.printFieldDescendingOrganization();
    }

}
