package fs.sdvbs.bts_tour;

/**
 * Created by Leon on 6/5/18.
 */

public class script {
    int building_num = -1;
    String name = "";
    String description = "";
    String[] degrees;
    String[] importance;

    public script(String name_, String description_, String[] degrees_, String[] importance_)
    {
        name = name_;
        description = description_;
        degrees = degrees_;
        importance = importance_;
    }

    public String getName()
    {
        return name;
    }
    public String getDescription() {return description; }
}
