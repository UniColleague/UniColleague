package com.swe444.demo.controller;

import com.swe444.demo.entity.Role;
import com.swe444.demo.entity.Task;
import com.swe444.demo.entity.User;
import com.swe444.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@Controller
@RequestMapping("/main")
public class MainController {

    UserService userService;

    RoleService roleService;

    TaskService taskService;


    EmailService emailService;

    String state = null;

    @Autowired
    public MainController(UserService userService, RoleService roleService, TaskService taskService, EmailService emailService){
        this.userService = userService;
        this.roleService = roleService;
        this.taskService = taskService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getName(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    @GetMapping("/showRegisterForm")
    public String showRegisterForm(Model theModel){

        theModel.addAttribute("newUser", new User());

        return "register-form";
    }



    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user){


        User theUser = new User(user.getUsername(),"{noop}"+user.getPassword(),1,user.getEmail());

        theUser.setImage("https://cdn.analyticsvidhya.com/wp-content/uploads/2023/04/ai-generated-gba2dce9e3_1920_xMPNobD.jpg");
        userService.save(theUser);
        Role role = new Role( "ROLE_USER",user.getUsername());
        theUser.addRole(role);
        roleService.save(role);
        userService.save(theUser);

        return "redirect:/showLoginForm";
    }

    @GetMapping("/home")
    public String homePage(Model theModel, HttpServletRequest request) throws ParseException {
        User user = userService.findUserByUsername(getName(request));

        boolean complete = false;
        theModel.addAttribute("complete", complete);
        theModel.addAttribute("newTask", new Task());
        theModel.addAttribute("tasks", user.getTasks());

        LocalDateTime now = LocalDateTime.now();

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        List<Task> tasks = userService.findUserByUsername(getName(request)).getTasks();
        int numberOfTasks = 0;
        String[] tasksName = new String[tasks.size()];
        LocalDateTime date;
        int j = 0;
        for(int i = 0; i < tasks.size(); i++){
             date = LocalDateTime.parse(tasks.get(i).getEndDate()).minusHours(4);
             int ct = date.compareTo(now);

             if (ct < 0 || ct == 0){
                 numberOfTasks++;
                 tasksName[j++] = tasks.get(i).getTitle();

             }
        }

        theModel.addAttribute("taskNumber", numberOfTasks);
        theModel.addAttribute("tasksName", tasksName);


        return "main-page";
    }

    @GetMapping("/showCalendar")
    public String showCalendar(Model theModel, HttpServletRequest request) {
        User user = userService.findUserByUsername(getName(request));
        List<Task> tasks = user.getTasks();
        for (int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getCompleted() == true){
                tasks.remove(tasks.get(i));
            }
        }
        theModel.addAttribute("tasks", tasks);

        return "showCalendar";
    }

    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute("newTask") Task task, HttpServletRequest request){
        User user = userService.findUserByUsername(getName(request));
        task.setUsername(user.getUsername());
        taskService.saveTask(task);

        return "redirect:/main/home";
    }

    @GetMapping("/updateTask")
    public String updateTask(@RequestParam("taskId") int id, Model myModel){
        Task task = taskService.getTaskById(id);

        myModel.addAttribute("task", task);

        return "updateTask-form";
    }

    @PostMapping("/saveTask2")
    public String saveUpdatedTask(@ModelAttribute("task") Task task){
        Task theTask = taskService.getTaskById(task.getId());
        taskService.updateTask(task);

        return "redirect:/main/home";
    }

    @GetMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskId") int id){
        Task task = taskService.getTaskById(id);
        taskService.deleteTask(task);
        return "redirect:/main/home";
    }

    @GetMapping("/taskToComplete")
    public String taskToComplete(@RequestParam("taskId") int id){
        Task task = taskService.getTaskById(id);
        if(task.getCompleted() == false) {
            task.setCompleted(true);
        }
        else if(task.getCompleted() == true) {
            task.setCompleted(false);
        }
        taskService.updateTask(task);

        return "redirect:/main/home";
    }

    @GetMapping("showCompleteTask")
    public String showCompleteTask(Model theModel, HttpServletRequest request) {
        User user = userService.findUserByUsername(getName(request));
        boolean complete = true;
        theModel.addAttribute("complete", complete);
        theModel.addAttribute("newTask", new Task());
        theModel.addAttribute("tasks", user.getTasks());

        return "main-page";
    }


    @GetMapping("/profile")
    public String showProfile(Model theModel, HttpServletRequest request){
        User user = userService.findUserByUsername(getName(request));

        theModel.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/showEditForm")
    public String showEditForm(Model theModel, HttpServletRequest request){
        User theUser = userService.findUserByUsername(getName(request));

        String newPass = "";
        char[] pass = new char[theUser.getPassword().length()];
        int j =0;
        for(int i = 6; i < theUser.getPassword().length(); i++){
            pass[j++] = theUser.getPassword().charAt(i);
            newPass += pass [j-1];
        }
        System.out.println(newPass);
        theUser.setPassword(newPass);
        theModel.addAttribute("currentUser", theUser);
        theModel.addAttribute("state", state);
        state = null;

        return "edit-account";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("currentUser") User user){
        User theUser = userService.findUserByUsername(user.getUsername());
        if(!user.getImage().equals("")) {
            theUser.setImage(user.getImage());
        }
        theUser.setEmail(user.getEmail());
        theUser.setPassword("{noop}" + user.getPassword());


        userService.save(theUser);

        return "redirect:/main/profile";
    }

    @GetMapping("/changeEmail")
    public String changeEmail(@RequestParam("email") String email, Model theModel){
        String em = email;

        theModel.addAttribute("currentEmail", em);
        theModel.addAttribute("user", new User());


        return "changeEmail-form";
    }

    @PostMapping("updateEmail")
    public String updateEmail(@ModelAttribute("user") User user, HttpServletRequest request,Model theModel) throws InterruptedException {
        User theUser = userService.findUserByUsername(getName(request));
        user.setPassword("{noop}" + user.getPassword());

        if(user.getPassword().equals(theUser.getPassword()) && user.getEmail().equals(user.getImage())){
            theUser.setEmail(user.getEmail());
            userService.save(theUser);
            state = "The email has changed successfully ✔";
        }else {
            state = "The email failed to changed ❌❌❌";
//            Thread.currentThread().sleep(2200);
        }



        return "redirect:/main/showEditForm";
    }


    @GetMapping("/changePassword")
    public String changePassword(Model theModel){


        theModel.addAttribute("user", new User());


        return "changePassword-form";
    }

    @PostMapping("updatePassword1")
    public String updatePassword1(@ModelAttribute("user") User user, HttpServletRequest request, Model theModel) throws InterruptedException {
        User theUser = userService.findUserByUsername(getName(request));
        user.setPassword("{noop}" + user.getPassword());

        if(user.getPassword().equals(theUser.getPassword()) && user.getEmail().equals(user.getImage())){
            theUser.setPassword("{noop}" + user.getEmail());
            userService.save(theUser);
            state = "The password has changed successfully ✔";
        }else {
            state = "The password failed to changed ❌❌❌";
//            Thread.currentThread().sleep(2200);
        }



        return "redirect:/main/showEditForm";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model theModel) {
        theModel.addAttribute("emailUser", new User());
        return "forgot-password";
    }

    @PostMapping("/forgot-password-sc")
    public String processForgotPasswordForm(@ModelAttribute("emailUser") User email, Model model) {

        User user = userService.findByEmail(email.getEmail());

        if (user != null) {
            String token = userService.createPasswordResetToken(user);
            emailService.sendPasswordResetEmail(user, token);
        }

        model.addAttribute("message", "Password reset instructions sent to your email.");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        if (userService.isPasswordResetTokenValid(token)) {
            model.addAttribute("token", token);
            model.addAttribute("passwordUser", new User());
            return "reset-password";
        } else {
            model.addAttribute("error", "Invalid or expired token");
            return "forgot-password";
        }
    }

    @PostMapping("/reset-password2")
    public String processResetPasswordForm(@RequestParam String token, @ModelAttribute("passwordUser") User newPassword, Model model) {
        if(newPassword.getPassword().equals(newPassword.getEmail())) {
            if (userService.isPasswordResetTokenValid(token)) {
                userService.resetPassword(token, newPassword.getPassword());
                model.addAttribute("message", true);
                return "login-form";
            } else {
                model.addAttribute("error", "Invalid or expired token");
            }
        }
        else {
            model.addAttribute("error", "The passwords don't match!");
        }

        return "reset-password";
    }

    @GetMapping("/viewStatistics")
    public String viewStatistics(Model theModel,  HttpServletRequest request){

        int finished =0;
        int unfinished = 0;
        List<Task> tasks = userService.findUserByUsername(getName(request)).getTasks();
        for (int i = 0; i< tasks.size(); i++){
            if(tasks.get(i).getCompleted())
                finished++;
            else
                unfinished++;
        }
        theModel.addAttribute("finished", finished);
        theModel.addAttribute("unfinished", unfinished);
        return "viewStatistics";
    }








}
