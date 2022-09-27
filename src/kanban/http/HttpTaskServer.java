package kanban.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import kanban.Status;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kanban.Managers.taskManager;


public class HttpTaskServer {

    private static final int PORT = 8080;
    HttpServer tasksServer;
    private static Gson gson = new Gson();


    public HttpTaskServer() throws IOException {
        tasksServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        tasksServer.createContext("/tasks", new TasksHandler());
        tasksServer.createContext("/tasks/task", new TaskHandler());
        tasksServer.createContext("/tasks/epic", new EpicHandler());
        tasksServer.createContext("/tasks/subtask", new SubtaskHandler());
        tasksServer.createContext("/tasks/history", new HistoryHandler());
        tasksServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        tasksServer.stop(1);
    }
    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = null;
            String method = exchange.getRequestMethod();
            System.out.println("Началась обработка " + method + " /tasks запроса от клиента.");
            switch (method) {
                case "GET":
                    response = gson.toJson(taskManager.getPrioritizedTasks());
                    exchange.sendResponseHeaders(200, 0);
                    break;
            }
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = null;
            String method = exchange.getRequestMethod();
            System.out.println("Началась обработка " + method + " /tasks/task запроса от клиента.");
            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                String[] splitQuery = query.split("=");
                if (splitQuery.length == 2 && !splitQuery[1].isEmpty()) {
                    int id = Integer.parseInt(splitQuery[1]);
                    if (taskManager.getTasks().containsKey(id)) {
                        switch (method) {
                            case "GET":
                                response = gson.toJson(taskManager.getTask(id));
                                exchange.sendResponseHeaders(200, 0);
                                break;
                            case "POST":
                                JsonElement jsonElement = JsonParser.parseString(new String(exchange.getRequestBody().readAllBytes(), UTF_8));
                                if (!jsonElement.isJsonObject()) {
                                    exchange.sendResponseHeaders(404, 0);
                                    return;
                                }
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                taskManager.updateTask(id,
                                        jsonObject.get("name").getAsString(),
                                        jsonObject.get("description").getAsString(),
                                        Status.valueOf(jsonObject.get("status").getAsString()),
                                        jsonObject.get("duration").getAsInt(),
                                        jsonObject.get("startTime").getAsString());
                                exchange.sendResponseHeaders(200, 0);
                                break;
                            case "DELETE":
                                taskManager.deleteTask(id);
                                exchange.sendResponseHeaders(200, 0);
                                break;
                        }
                    }
                }
            } else {
                switch (method) {
                    case "GET":
                        response = gson.toJson(taskManager.getTasks());
                        exchange.sendResponseHeaders(200, 0);
                        break;
                    case "POST":
                        JsonElement jsonElement = JsonParser.parseString(new String(exchange.getRequestBody().readAllBytes(), UTF_8));
                        if (!jsonElement.isJsonObject()) {
                            exchange.sendResponseHeaders(404, 0);
                            return;
                        }
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        taskManager.newTask(jsonObject.get("name").getAsString(),
                                jsonObject.get("description").getAsString(),
                                jsonObject.get("duration").getAsInt(),
                                jsonObject.get("startTime").getAsString());
                        exchange.sendResponseHeaders(200, 0);
                        break;
                    case "DELETE":
                        taskManager.clearTasks();
                        exchange.sendResponseHeaders(200, 0);
                        break;
                }
            }
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class EpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = null;
            String method = exchange.getRequestMethod();
            System.out.println("Началась обработка " + method + " /tasks/epic запроса от клиента.");
            String query = exchange.getRequestURI().getQuery();
            if (!query.isEmpty()) {
                String[] splitQuery = query.split("=");
                if (splitQuery.length == 2 && !splitQuery[1].isEmpty()) {
                    int id = Integer.parseInt(splitQuery[1]);
                    if (taskManager.getEpics().containsKey(id)) {
                        switch (method) {
                            case "GET":
                                response = gson.toJson(taskManager.getEpic(id));
                                exchange.sendResponseHeaders(200, 0);
                                break;
                            case "POST":
                                JsonElement jsonElement = JsonParser.parseString(new String(exchange.getRequestBody().readAllBytes(), UTF_8));
                                if (!jsonElement.isJsonObject()) {
                                    exchange.sendResponseHeaders(404, 0);
                                }
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                taskManager.updateEpic(id,
                                        jsonObject.get("name").getAsString(),
                                        jsonObject.get("description").getAsString());
                                exchange.sendResponseHeaders(200, 0);
                                break;
                            case "DELETE":
                                taskManager.deleteEpic(id);
                                exchange.sendResponseHeaders(200, 0);
                                break;
                        }
                    }
                }
            } else {
                switch (method) {
                    case "GET":
                        response = gson.toJson(taskManager.getEpics());
                        exchange.sendResponseHeaders(200, 0);
                        break;
                    case "POST":
                        JsonElement jsonElement = JsonParser.parseString(new String(exchange.getRequestBody().readAllBytes(), UTF_8));
                        if (!jsonElement.isJsonObject()) {
                            exchange.sendResponseHeaders(404, 0);
                        }
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        taskManager.newEpic(jsonObject.get("name").getAsString(),
                                jsonObject.get("description").getAsString());
                        exchange.sendResponseHeaders(200, 0);
                        break;
                    case "DELETE":
                        taskManager.clearEpics();
                        exchange.sendResponseHeaders(200, 0);
                        break;
                }
            }
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class SubtaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = null;
            String method = exchange.getRequestMethod();
            System.out.println("Началась обработка " + method + " /tasks/epic запроса от клиента.");
            String query = exchange.getRequestURI().getQuery();
            if (!query.isEmpty()) {
                String[] splitQuery = query.split("=");
                if (splitQuery.length == 2 && !splitQuery[1].isEmpty()) {
                    int id = Integer.parseInt(splitQuery[1]);
                    if (taskManager.getSubtasks().containsKey(id)) {
                        switch (method) {
                            case "GET":
                                response = gson.toJson(taskManager.getSubtask(id));
                                exchange.sendResponseHeaders(200, 0);
                                break;
                            case "POST":
                                JsonElement jsonElement = JsonParser.parseString(new String(exchange.getRequestBody().readAllBytes(), UTF_8));
                                if (!jsonElement.isJsonObject()) {
                                    exchange.sendResponseHeaders(404, 0);
                                }
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                taskManager.updateSubtask(id,
                                        jsonObject.get("name").getAsString(),
                                        jsonObject.get("description").getAsString(),
                                        jsonObject.get("epicName").getAsString(),
                                        Status.valueOf(jsonObject.get("status").getAsString()),
                                        jsonObject.get("duration").getAsInt(),
                                        jsonObject.get("startTime").getAsString());
                                exchange.sendResponseHeaders(200, 0);
                                break;
                            case "DELETE":
                                taskManager.deleteSubtask(id);
                                exchange.sendResponseHeaders(200, 0);
                                break;
                        }
                    }
                }
            } else {
                switch (method) {
                    case "GET":
                        response = gson.toJson(taskManager.getSubtasks());
                        exchange.sendResponseHeaders(200, 0);
                        break;
                    case "POST":
                        JsonElement jsonElement = JsonParser.parseString(new String(exchange.getRequestBody().readAllBytes(), UTF_8));
                        if (!jsonElement.isJsonObject()) {
                            exchange.sendResponseHeaders(404, 0);
                        }
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        taskManager.newSubtask(jsonObject.get("name").getAsString(),
                                jsonObject.get("description").getAsString(),
                                jsonObject.get("epicName").getAsString(),
                                jsonObject.get("duration").getAsInt(),
                                jsonObject.get("startTime").getAsString());
                        exchange.sendResponseHeaders(200, 0);
                        break;
                    case "DELETE":
                        taskManager.clearSubtasks();
                        exchange.sendResponseHeaders(200, 0);
                        break;
                }
            }
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = null;
            String method = exchange.getRequestMethod();
            System.out.println("Началась обработка " + method + " /tasks запроса от клиента.");
            String path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET":
                    response = gson.toJson(taskManager.getHistory());
                    exchange.sendResponseHeaders(200, 0);
                    break;
            }
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

}
