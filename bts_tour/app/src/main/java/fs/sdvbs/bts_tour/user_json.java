package fs.sdvbs.bts_tour;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Leon on 6/12/18.
 */

/*
The current user's JSON file that will be pulled from the Firebase.
This will be the main way of updating the user on the database and should eliminate
the need for constant pulling and pushing from the database.

We can limit the number of times the data is updated if we
 */
public class user_json implements Parcelable{


    protected user_json(Parcel in) {
    }

    public static final Creator<user_json> CREATOR = new Creator<user_json>() {
        @Override
        public user_json createFromParcel(Parcel in) {
            return new user_json(in);
        }

        @Override
        public user_json[] newArray(int size) {
            return new user_json[size];
        }
    };

    public quiz[] getQuizzes()
    {
        return new quiz[25];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
