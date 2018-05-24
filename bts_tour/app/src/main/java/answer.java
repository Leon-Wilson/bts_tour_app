/**
 * Created by Leon on 5/22/18.
 */

public class answer {
    String answer_text = "";
    boolean is_correct = false;

    answer(String text_, boolean is_correct_)
    {
        answer_text = text_;
        is_correct = is_correct_;
    }

    public boolean isCorrect()
    {
        return is_correct;
    }
    public void setCorrect(boolean _change)
    {
        is_correct = _change;
    }
}
