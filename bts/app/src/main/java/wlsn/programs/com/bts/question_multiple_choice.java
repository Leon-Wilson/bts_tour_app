package wlsn.programs.com.bts;

/**
 * Created by Leon on 7/31/18.
 */

public class question_multiple_choice implements question {
    question_types question_type = question_types.multiple_choice;

    String question_text = "";
    answer[] answer_pool;

    //BOTH NEVER ACTUALLY USED
    int player_choice;
    int correct_answer = -1;

    int building_num = -1;
    boolean already_answered_correctly = false;

    question_multiple_choice(String question_ , answer[] answer_pool_)
    {
        question_text = question_;
        answer_pool = answer_pool_;
        //Loop to set correct
        for(int i = 0; i < answer_pool.length; i++)
        {
            if(answer_pool[i].isCorrect())
            {
                correct_answer = i;
                break;
            }

        }

        //Catch answer not set
    }

    public void set_player_choice(int choice)
    {
        player_choice = choice;

    }

    @Override
    public question_types getType()
    {
        return question_type;
    }

    @Override
    public String getQuestionText() { return question_text; }

    //NEVER ACTUALLY USED
    @Override
    public boolean isCorrect()
    {
        if(player_choice == correct_answer)
        {
            return true;
        }

        return false;
    }

    @Override
    public void setAlreadyCorrect(boolean bool_)
    {
        already_answered_correctly = bool_;
    }

    @Override
    public boolean alreadyCorrect()
    {
        return already_answered_correctly;
    }

    @Override
    public answer[] getAnswers()
    {
        return answer_pool;
    }

    @Override
    public answer getAnswer()
    {
        return answer_pool[correct_answer];
    }
}
