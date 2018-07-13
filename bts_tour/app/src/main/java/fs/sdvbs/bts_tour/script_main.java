package fs.sdvbs.bts_tour;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class script_main extends AppCompatActivity {

    player current_player;
    script current_script;

    TextView script_name;
    TextView script_degrees;
    TextView script_description;

    ListView script_important;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        script_name = findViewById(R.id.script_name);
        script_description = findViewById(R.id.script_description);

        current_player = player.getInstance();
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            int selected = extras.getInt("selected_script");
            current_script = current_player.getCurrentUser().getScripts()[selected];
        }

        script_name.setText(current_script.getName());
        script_description.setText(current_script.getDescription());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
