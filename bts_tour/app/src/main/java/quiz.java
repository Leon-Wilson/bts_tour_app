/**
 * Created by Leon on 5/22/18.
 */

public class quiz {
    question[] questions;

    int max_correct;
    int total_correct;

    quiz(question[] questions_)
    {
        questions = questions_;
        max_correct = questions.length;
        total_correct = 0;
    }

    public void runQuiz()
    {
        for(int i = 0; i < questions.length;i++)
        {
            switch(questions[i].getType())
            {
                case multiple_choice:
                    //SET QUESTION TEXT

                    //SET ANSWER

                    //CHECK ANSWER
                    break;
                case multiple_answer:
                    break;
                case fill_in_blank:
                    break;
            }
        }
    }
}
