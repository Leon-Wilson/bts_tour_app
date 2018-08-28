package wlsn.programs.com.bts;

/**
 * Created by Leon on 7/31/18.
 */

public interface question {
    public question_types getType();
    public String getQuestionText();
    public boolean isCorrect();
    public void setAlreadyCorrect(boolean bool_);
    public boolean alreadyCorrect();

    //THESE METHODS WILL ACT DIFFERENTLY FOR FILL IN BLANK
    public answer[] getAnswers();
    public answer getAnswer();
}
