package fs.sdvbs.bts_tour;

/**
 * Created by Leon on 5/22/18.
 */

public class quiz {
    question[] questions;

    int total_questions;
    int total_correct;
    int question_num = 0;
    boolean complete = false;
    boolean locked = true;

    public quiz(question[] questions_)
    {
        questions = questions_;
        total_questions = questions.length;
        total_correct = 0;
    }

    public boolean quizComplete()
    {
        if(total_correct > (total_questions - (total_questions * 0.25)))
        {
            return true;
        }
        return false;
    }

    public void setLock(boolean value_)
    {
        locked = value_;
    }
}
