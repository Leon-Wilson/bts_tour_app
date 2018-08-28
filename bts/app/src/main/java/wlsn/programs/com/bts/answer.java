package wlsn.programs.com.bts;

/**
 * Created by Leon on 7/31/18.
 */

public class answer {
    private String answer_text = "";
    private boolean is_correct = false;

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
    public String getAnswerText() { return answer_text; }
}
