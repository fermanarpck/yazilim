package tr.edu.medipol;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping()
class Controller {
    public static List<Employee> Employees = new ArrayList<>();
    public static Map<Integer, List<String>> Tasks = new HashMap<>();

    static {
        Employees.add(new Employee(1, "Ferman"));
        Employees.add(new Employee(2, "Arpacık"));

        Tasks.put(1, new ArrayList<>());
        Tasks.put(2, new ArrayList<>());
    }

    @GetMapping("/taskList")
    public Map<Integer, List<String>> listAllTasks() {
        return Tasks;
    }

    @PostMapping("/addTask/{employeeId}")
    public ResponseEntity<String> addTask(@PathVariable Integer employeeId, @RequestBody Task eTask) {
        if (!Tasks.containsKey(employeeId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");

        Tasks.get(employeeId).add(eTask.task);

        return ResponseEntity.ok("Task added successfully");
    }
}

