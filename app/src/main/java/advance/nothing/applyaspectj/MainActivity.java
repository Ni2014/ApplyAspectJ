package advance.nothing.applyaspectj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import advance.nothing.applyaspectj.annotations.BehaviorTrace;
import advance.nothing.applyaspectj.annotations.SingleClick;

public class MainActivity extends AppCompatActivity {

    private final String tag = this.getClass().getSimpleName();

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = findViewById(R.id.btn1);
        mButton2 = findViewById(R.id.btn2);
        mButton3 = findViewById(R.id.btn3);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag,"btn clicked");
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        behaviourTrace();
                    }
                }).start();

            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFastDoubleClick(v);
            }
        });
    }

    @SingleClick(value = 3000)
    private void testFastDoubleClick(View v) {
        toast("btn is clicked");
    }

    @BehaviorTrace(value = "同步用户信息")
    private int behaviourTrace() {
        Log.e(tag,"behaviourTrace()");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1024;
    }


    public void toast(String string){
        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
    }
}
