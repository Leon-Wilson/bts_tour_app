package fs.sdvbs.bts_tour;

/**
 * Created by Leon on 5/22/18.
 */

public class quiz {
    question[] questions;

    int max_correct;
    int total_correct;
    int question_num = 0;

    public quiz(question[] questions_)
    {
        questions = questions_;
        max_correct = questions.length;
        total_correct = 0;
    }

}
