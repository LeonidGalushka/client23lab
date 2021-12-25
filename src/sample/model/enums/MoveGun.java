package sample.model.enums;

public enum MoveGun {

    UP("UP\n"),
    DOWN("DOWN\n");
    String title;

    MoveGun(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
