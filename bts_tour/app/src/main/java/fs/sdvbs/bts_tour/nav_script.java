package fs.sdvbs.bts_tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class nav_script extends AppCompatActivity {

    player current_player;
    ListView script_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_script);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        current_player = player.getInstance();
        script_list = findViewById(R.id.script_list);
        loadScripts();

        script_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int selected_script = i;
                Intent intent = new Intent(nav_script.this, script_main.class);
                intent.putExtra("selected_script", selected_script);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void loadScripts()
    {
        int script_num = 0;
        String[] script_names;

        for(int i = 0; i < current_player.getCurrentUser().getScripts().length;i++)
        {
            if(current_player.getCurrentUser().getScripts()[i] == null)
            {
                script_num = i;
                break;
            }
        }

        if(script_num == 0)
        {
            script_num = 1;
            script_names = new String[script_num];
            script_names[0] = new String("NO SCRIPTS FOUND");
        }
        else if(script_num > current_player.getCurrentUser().getScripts().length)
        {
            script_names = new String[1];
            script_names[0] = new String("NO SCRIPTS FOUND");
        }
        else
        {
            script_names = new String[script_num];
            for(int j = 0; j < script_num; j++)
            {
                script_names[j] = current_player.getCurrentUser().getScripts()[j].getName();
            }
        }

        ArrayAdapter<String> name_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,script_names);
        script_list.setAdapter(name_list);
    }

}
