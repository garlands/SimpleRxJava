package net.jp.garlands.simplerxjava;

public class MediaListData {
    private String title;
    private String image_id;
    private String display_name;

    public MediaListData(String title, String image_id, String display_name) {
        this.title = title;
        this.image_id = image_id;
        this.display_name = display_name;
    }

    public String getTitle() { return title; }

    public String getImage_id() { return image_id; }

    public String getDisplay_name() { return display_name; }
}
