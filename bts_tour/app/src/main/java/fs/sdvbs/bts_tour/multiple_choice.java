package fs.sdvbs.bts_tour;

/**
 * Created by Leon on 5/21/18.
 */

public class multiple_choice implements question {

    question_types question_type = question_types.multiple_choice;

    String question_text = "";
    answer[] answer_pool;

    int player_choice;
    int correct_answer = -1;

    boolean already_answered_correctly = false;

    multiple_choice(String question_ , answer[] answer_pool_)
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

    public question_types getType()
    {
        return question_type;
    }

    public String getQuestionText() { return question_text; }

    public boolean isCorrect()
    {
        if(player_choice == correct_answer)
        {
            already_answered_correctly = true;
            return true;
        }

        return false;
    }

    public boolean alreadyCorrect()
    {
        return already_answered_correctly;
    }

    public answer[] getAnswers()
    {
        return answer_pool;
    }
    
    public answer getAnswer()
    {
        return answer_pool[correct_answer];
    }
}
