package fs.sdvbs.bts_tour;


/**
 * Created by Leon on 5/25/18.
 */

public class multi_select implements question {

    question_types question_type = question_types.multiple_answer;
    String question_text = "";

    answer[] answer_pool;

    int player_choice;
    int correct_answers = 0;
    int[] correct_index_nums;
    int[] selected_index_nums;

    boolean already_answered_correctly = false;

    public multi_select(String question_text_, answer[] answer_pool_)
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

        correct_index_nums = new int[correct_answers];

        int current_index = 0;
        for(int i = 0; i < correct_index_nums.length; i++)
        {
            if(answer_pool[i].isCorrect())
            {
                correct_index_nums[current_index] = i;
                current_index++;
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
        if(selected_index_nums.length == correct_index_nums.length)
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
    public boolean alreadyCorrect() {
        return already_answered_correctly;
    }

    @Override
    public answer[] getAnswers() {
        return new answer[0];
    }

    @Override
    public answer getAnswer() {
        return null;
    }
}

