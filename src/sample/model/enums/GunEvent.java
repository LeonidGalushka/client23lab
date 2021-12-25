package sample.model.enums;

public enum GunEvent {

    BUH("BUH\n");

    String title;

    GunEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
