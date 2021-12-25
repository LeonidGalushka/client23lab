package sample.model.enums;

public enum User {
    USER_1("1"),
    USER_2("2");

    String title;

    User(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
