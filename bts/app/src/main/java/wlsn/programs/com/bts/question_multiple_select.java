package wlsn.programs.com.bts;

/**
 * Created by Leon on 7/31/18.
 */

public class question_multiple_select implements question {
    question_types question_type = question_types.multiple_answer;
    String question_text = "";

    answer[] answer_pool;

    int player_choice;
    int correct_answers = 0;

    //May not need these two variables if I use a boolean array in quiz_main (can check the boolean array and the isCorrect() of the answer itself
    //int[] correct_index_nums;
    //int[] selected_index_nums;

    int building_num;
    boolean already_answered_correctly = false;

    public question_multiple_select(String question_text_, answer[] answer_pool_)
    {
        answer_pool = answer_pool_;
        question_text = question_text_;

        for(int i = 0; i < answer_pool.length;i++)
        {
            if(answer_pool[i].isCorrect())
            {
                correct_answers += 1;
            }
        }


    }

    @Override
    public question_types getType() {
        return question_type;
    }

    @Override
    public String getQuestionText() {
        return question_text;
    }

    @Override
    public boolean isCorrect()
    {
        return false;
    }

    @Override
    public void setAlreadyCorrect(boolean bool_)
    {
        already_answered_correctly = bool_;
    }

    @Override
    public boolean alreadyCorrect() {
        return already_answered_correctly;
    }

    @Override
    public answer[] getAnswers() {
        return answer_pool;
    }

    @Override
    public answer getAnswer() {
        return null;
    }
}
