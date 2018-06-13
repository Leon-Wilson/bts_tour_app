package fs.sdvbs.bts_tour;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leon on 5/21/18.
 */

/*
TODO: (IN PROGRESS) Implement Parceable into level system so that it can be written to the player parceable objects when transfering
//*/
public class level_system implements Parcelable{
    int current_level;
    int level_cap;

    int current_points;
    int points_until_levelup;

    //TODO: Create test cases for level up system
    public level_system()
    {
        current_level = 1;
        level_cap = 5;
        current_points = 0;
        points_until_levelup = 10;
    }

    protected level_system(Parcel in) {
        current_level = in.readInt();
        level_cap = in.readInt();
        current_points = in.readInt();
        points_until_levelup = in.readInt();
    }

    public static final Creator<level_system> CREATOR = new Creator<level_system>() {
        @Override
        public level_system createFromParcel(Parcel in) {
            return new level_system(in);
        }

        @Override
        public level_system[] newArray(int size) {
            return new level_system[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(current_level);
        parcel.writeInt(level_cap);
        parcel.writeInt(current_points);
        parcel.writeInt(points_until_levelup);
    }
}
