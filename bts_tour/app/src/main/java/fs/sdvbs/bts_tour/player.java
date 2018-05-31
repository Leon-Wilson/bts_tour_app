package fs.sdvbs.bts_tour;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leon on 5/22/18.
 */

/*
TODO: Implement Parceable into player class so that it can be transfered between activities
//*/
public class player implements Parcelable {
    level_system stats;
    //IMAGE? of the current player?
    String player_name;

    public player(String name_)
    {
        player_name = name_;
        stats = new level_system();
    }

    public player(String name_, level_system stats_)
    {
        player_name = name_;
        stats = stats_;
    }

    protected player(Parcel in) {
        player_name = in.readString();
        stats = in.readParcelable(level_system.class.getClassLoader());
    }

    public static final Creator<player> CREATOR = new Creator<player>() {
        @Override
        public player createFromParcel(Parcel in) {
            return new player(in);
        }

        @Override
        public player[] newArray(int size) {
            return new player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(player_name);
        parcel.writeParcelable(stats,i);
    }
}
