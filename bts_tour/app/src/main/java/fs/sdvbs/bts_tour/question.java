package fs.sdvbs.bts_tour;

/**
 * Created by Leon on 5/21/18.
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
