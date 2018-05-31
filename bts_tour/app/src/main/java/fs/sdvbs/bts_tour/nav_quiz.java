package fs.sdvbs.bts_tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class nav_quiz extends AppCompatActivity {

    player current_player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            current_player = extras.getParcelable("current_player");
        }
        else
        {
            //TODO: Error handling for null current player
            current_player = new player("BAD NEWS");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.test_quiz_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nav_quiz.this, quiz_main.class);
                intent.putExtra("current_player", current_player);
                //intent.putExtra("CURRENT_PLAYER", current_player);
                startActivityForResult(intent,1);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                current_player = extras.getParcelable("current_player");
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("current_player", current_player);
        setResult(RESULT_OK, intent);
        finish();
    }
}
