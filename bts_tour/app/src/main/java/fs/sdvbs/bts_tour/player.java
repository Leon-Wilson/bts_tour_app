package fs.sdvbs.bts_tour;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leon on 5/22/18.
 */

/*
TODO: (DONE) Implement Parceable into player class so that it can be transfered between activities
TODO: MAKE initial json load at splash screen and reuse it everywhere
//*/
public class player implements Parcelable {
    user_json current_user;
    level_system stats;
    //IMAGE? of the current player?
    String player_name;
    //TODO: (DONE) Add list of quizzes to player for storing results in database
    quiz[] quizzes;

    public player()
    {
        player_name = null;
        quizzes = null;
        stats = null;
        current_user = user_json.getInstance();
    }
    public player(String name_) {
        player_name = name_;
        stats = new level_system();
        current_user = user_json.getInstance();
        quizzes = current_user.getQuizzes();
    }

    public player(String name_, level_system stats_) {
        player_name = name_;
        stats = stats_;
    }

    public void setName(String name_)
    {
        player_name = name_;
    }
    public void setStats(level_system stats_)
    {
        stats = stats_;
    }
    public void setCurrentUser(user_json user_)
    {
        current_user = user_;
    }
    public void setQuizzes(quiz[] quizzes_)
    {
        quizzes = quizzes_;
    }

    public String getName()
    {
        return player_name;
    }
    public level_system getStats()
    {
        return stats;
    }
    public user_json getCurrentUser()
    {
        return current_user;
    }
    public quiz[] getQuizzes()
    {
        return quizzes;
    }



    protected player(Parcel in) {
        player_name = in.readString();
        stats = in.readParcelable(level_system.class.getClassLoader());
       // current_user = in.readParcelable(user_json.class.getClassLoader());
//        quizzes = (quiz[]) in.readArray(quiz.class.getClassLoader());
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
        parcel.writeParcelable(stats, i);
       // parcel.writeParcelable(current_user,i);
        //parcel.writeArray(quizzes);*
    }

    public void setQuizList()
    {
        quizzes = current_user.getQuizzes();
    }
}
