package miketech.it.carrieredit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import static miketech.it.carrieredit.R.id.aSwitch;
import static miketech.it.carrieredit.R.id.button;
import static miketech.it.carrieredit.R.id.editText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private Switch mSwitch;
    private EditText mEdit;
    private Button mButton;

    private Boolean enabled = false;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().setContext(this);
        enabled = MyApplication.getInstance().isHookEnable();
        initView();
    }


    private void initView(){
        mSwitch = (Switch) findViewById(R.id.aSwitch);
        mEdit = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(button);

        mEdit.setText(MyApplication.getInstance().readCarrierText());


        if (!enabled){
            mEdit.setVisibility(View.INVISIBLE);
            mButton.setVisibility(View.INVISIBLE);
        }else {
            mSwitch.setChecked(true);
            mEdit.setVisibility(View.VISIBLE);
            mButton.setVisibility(View.VISIBLE);
            mButton.setOnClickListener(this);
        }

        mSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        String text = mEdit.getText().toString();
        MyApplication.getInstance().saveCarrierText(text);
        mEdit.clearFocus();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b){
            enabled = true;
            MyApplication.getInstance().setHookEnable(true);
            Log.d(TAG,"Checked");
        }else {
            enabled = false;
            Log.d(TAG,"unChecked");
            MyApplication.getInstance().setHookEnable(false);
        }

        initView();

    }
}
