public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public abstract String toFileFormat();

    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                ToDo todo = new ToDo(description);
                if (isDone) todo.markDone();
                return todo;
            case "D":
                if (parts.length < 4) return null;
                Deadline deadline = new Deadline(description, parts[3]);
                if (isDone) deadline.markDone();
                return deadline;
            case "E":
                if (parts.length < 5) return null;
                Event event = new Event(description, parts[3], parts[4]);
                if (isDone) event.markDone();
                return event;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        String status = isDone ? "X" : " ";
        return "[" + status + "] " + description;
    }
}
