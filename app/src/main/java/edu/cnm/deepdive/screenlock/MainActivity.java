package edu.cnm.deepdive.screenlock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  private Button lock, disable, enable;
  public static final int RESULT_ENABLE = 11;
  private DevicePolicyManager devicePolicyManager;
  private ComponentName compName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
    compName = new ComponentName(this, MyAdmin.class);

    lock = (Button) findViewById(R.id.lock);
    enable = (Button) findViewById(R.id.enable);
    disable = (Button) findViewById(R.id.disable);
    lock.setOnClickListener(this);
    enable.setOnClickListener(this);
    disable.setOnClickListener(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    boolean isActive = devicePolicyManager.isAdminActive(compName);
    disable.setVisibility(isActive ? View.VISIBLE : View.GONE);
    enable.setVisibility(isActive ? View.GONE : View.VISIBLE);
  }

  @Override
  public void onClick(View v) {
    if (v == lock){
      boolean active = devicePolicyManager.isAdminActive(compName);

      if(active){
        devicePolicyManager.lockNow();
      }else {
        Toast.makeText(this, "You need to enable Admin Device Features",
            Toast.LENGTH_LONG).show();
      }
    }else if (v == enable){
      Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
      intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
      intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
          "App needs permission to lock screen");
      startActivityForResult(intent, RESULT_ENABLE);

    }else if (v == disable){
      devicePolicyManager.removeActiveAdmin(compName);
      disable.setVisibility(v.GONE);
      enable.setVisibility(v.VISIBLE);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch(requestCode){
      case RESULT_ENABLE:
        if (resultCode == Activity.RESULT_OK){
          Toast.makeText(MainActivity.this,
              "You have enabled the Admin Device Features",
              Toast.LENGTH_LONG).show();
        }else {
          Toast.makeText(MainActivity.this,
              "Cannot enable Admin Device Features",
              Toast.LENGTH_LONG).show();
        }
        break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}
