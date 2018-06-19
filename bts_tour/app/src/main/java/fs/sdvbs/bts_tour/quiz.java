package fs.sdvbs.bts_tour;

/**
 * Created by Leon on 5/22/18.
 */

//TODO: Make Parcelable
public class quiz {
    question[] questions;

    String building_key;
    String quiz_name;
    int total_questions;
    int total_correct;
    int previous_total;
    int question_num = 0;
    boolean complete = false;
    boolean locked = true;

    public quiz(question[] questions_)
    {
        questions = questions_;
        total_questions = questions.length;
        total_correct = 0;
    }

    public quiz(String name_, question[] questions_)
    {
        quiz_name = name_;
        questions = questions_;
        total_questions = questions.length;
        total_correct = 0;
    }

    public boolean quizComplete()
    {
        //TODO: Should probably make sure these are rounded to integers to avoid fractional comparison
        if(total_correct > (total_questions - (total_questions * 0.25)))
        {
            complete = true;
            return true;
        }
        return false;
    }

    public boolean isComplete()
    {
        return complete;
    }

    public void setComplete(boolean value_)
    {
        complete = value_;
    }
    public void setLock(boolean value_)
    {
        locked = value_;
    }
    public String getName()
    {
        return quiz_name;
    }
}
