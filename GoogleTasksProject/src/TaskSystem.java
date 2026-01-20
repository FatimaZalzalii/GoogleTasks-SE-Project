import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Google Tasks System - Backend Prototype
 * Based on the Phase 2 Class Diagram
 * Authors: Fatima Zalzali & Zainab Noureldeen
 */

// 1. SubTask Class (Lowest Level)
class SubTask {
    private String subTaskId;
    private String title;
    private boolean isCompleted;

    public SubTask(String subTaskId, String title) {
        this.subTaskId = subTaskId;
        this.title = title;
        this.isCompleted = false;
    }

    public void toggleStatus() {
        this.isCompleted = !this.isCompleted;
    }

    @Override
    public String toString() {
        return "    [SubTask] " + title + (isCompleted ? " (Done)" : "");
    }
}

// 2. Task Class (Contains SubTasks)
class Task {
    private String taskId;
    private String title;
    private String details;
    private Date dueDate;
    private boolean isCompleted;
    private List<SubTask> subTasks; // Composition

    public Task(String taskId, String title, String details, Date dueDate) {
        this.taskId = taskId;
        this.title = title;
        this.details = details;
        this.dueDate = dueDate;
        this.isCompleted = false;
        this.subTasks = new ArrayList<>();
    }

    public void addSubTask(String title) {
        String subId = "S" + (subTasks.size() + 1);
        SubTask newSub = new SubTask(subId, title);
        subTasks.add(newSub);
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public void editDetails(String newDetails) {
        this.details = newDetails;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  [Task] ").append(title).append(isCompleted ? " (Done)" : " (Pending)").append("\n");
        for (SubTask sub : subTasks) {
            sb.append(sub.toString()).append("\n");
        }
        return sb.toString();
    }
}

// 3. TaskList Class (Contains Tasks)
class TaskList {
    private String listId;
    private String title;
    private Date lastUpdated;
    private List<Task> tasks; // Composition

    public TaskList(String listId, String title) {
        this.listId = listId;
        this.title = title;
        this.lastUpdated = new Date();
        this.tasks = new ArrayList<>();
    }

    public void addTask(String taskId, String title, String details) {
        Task newTask = new Task(taskId, title, details, null);
        tasks.add(newTask);
        this.lastUpdated = new Date();
    }

    public void deleteList() {
        tasks.clear();
        System.out.println("List cleared.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [List] ").append(title).append("\n");
        for (Task t : tasks) {
            sb.append(t.toString());
        }
        return sb.toString();
    }
}

// 4. User Class (Manages Lists)
class User {
    private String userId;
    private String name;
    private String email;
    private List<TaskList> taskLists; // Composition

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.taskLists = new ArrayList<>();
    }

    public void login() {
        System.out.println("User " + name + " logged in.");
    }

    public void createTaskList(String listId, String title) {
        TaskList newList = new TaskList(listId, title);
        taskLists.add(newList);
    }

    public TaskList getList(int index) {
        if (index >= 0 && index < taskLists.size()) {
            return taskLists.get(index);
        }
        return null;
    }

    public void showAllData() {
        System.out.println("\n--- Dashboard for " + name + " ---");
        for (TaskList list : taskLists) {
            System.out.println(list.toString());
        }
    }
}

// 5. Main Class to run the System
public class TaskSystem {
    public static void main(String[] args) {
        // Create User
        User myUser = new User("U001", "Student Team", "project@usal.edu.lb");
        myUser.login();

        // Create Lists
        myUser.createTaskList("L01", "Software Project");
        
        // Add Tasks
        TaskList list1 = myUser.getList(0);
        list1.addTask("T01", "Finish Phase 4", "Jira Setup");
        list1.addTask("T02", "Finish Phase 5", "GitHub Push");

        // Display
        myUser.showAllData();
    }
}