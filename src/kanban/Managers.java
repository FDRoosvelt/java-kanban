package kanban;

import kanban.managers.HTTPTaskManager;

import java.io.File;


public class Managers {
    public static File file = new File(System.getProperty("user.dir")+"/src/kanban/data/data.csv");
    static String url = "http://localhost:8078";
    public static TaskManager taskManager;


    static public TaskManager getDefault(String key) {
        taskManager = new HTTPTaskManager(url, key);
        return taskManager;
    }
}
