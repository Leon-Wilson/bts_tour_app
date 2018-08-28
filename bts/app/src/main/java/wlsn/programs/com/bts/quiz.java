package wlsn.programs.com.bts;

/**
 * Created by Leon on 7/31/18.
 */

public class quiz {
    private question[] questions;

    int building_num;
    String name;
    int total_questions;
    int total_correct;
    int previous_total;
    boolean complete = false;
    boolean locked = true;


    public quiz()
    {
        name = "We couldn't find any quizzes, Please reload";
    }
    public quiz(question[] questions_)
    {
        questions = questions_;
        total_questions = questions.length;
        total_correct = 0;
    }

    public quiz(String name_, question[] questions_)
    {
        name = name_;
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

    public void setBuildingNum(int num_)
    {
        building_num = num_;
    }
    public int getBuildingNum(){return building_num;}
    public boolean isComplete()
    {
        return complete;
    }
    public question[] getQuestions() { return questions;}
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
        return name;
    }
    public int getTotal_questions()
    {
        return total_questions;
    }
    public int getTotal_correct()
    {
        return total_correct;
    }
    public int getPrevious_total()
    {
        return previous_total;
    }
    public boolean getLock()
    {
        return locked;
    }
}
