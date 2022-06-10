public class ExampleObject {
    // Данный класс реализован для проверки методов, в которых параметром служит входной объект
    int id;
    String name;
    String description;
    String status;
    int epicId;

    public ExampleObject(String name, String description, int epicId) {
        this.name = name;
        this.description = description;
        this.epicId = epicId;
    }

    public ExampleObject(int id, String name, String description, String status, int epicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicId = epicId;
    }
}
