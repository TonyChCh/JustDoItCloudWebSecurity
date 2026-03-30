package teccr.justdoitcloud.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import teccr.justdoitcloud.data.Task;
import teccr.justdoitcloud.data.User;
import teccr.justdoitcloud.service.TaskService;

@Slf4j
@Controller
@RequestMapping("/user/tasks")
public class UserTasksController {

    private final TaskService taskService;

    public UserTasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String showUserTasks(Model model, @AuthenticationPrincipal User user) {
        // Se debe crear un objeto vacio para poder armar en el formulario
        // Es crucial agregarlo al atributo del modelo (newUser & newTask) para poder recibirlo en el POST
        Task task = new Task();
        task.setStatus(Task.Status.INPROGRESS);
        model.addAttribute("newTask", task);
        model.addAttribute("user", user);
        // Retrieve user tasks and add to user object in session
        if (user != null) {
            user.setTasks(taskService.getTasksForUser(user));
        }

        return "usertasks";
    }

    @PostMapping
    public String addTask(@Valid @ModelAttribute(name = "newTask") Task newTask,
                          Errors errors,
                          @AuthenticationPrincipal User user) {
        log.info("Adding task: " + newTask);
        if (errors.hasErrors()) {
            return "usertasks";
        }

        taskService.addTaskToUser(user, newTask);
        return "redirect:/user/tasks";
    }
}
