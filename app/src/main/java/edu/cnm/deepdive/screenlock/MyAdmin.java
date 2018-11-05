package edu.cnm.deepdive.screenlock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyAdmin extends DeviceAdminReceiver {

  @Override
  public void onEnabled(Context context, Intent intent) {
    Toast.makeText(context, "Device Admin : enabled", Toast.LENGTH_LONG).show();
  }

  @Override
  public void onDisabled(Context context, Intent intent) {
    Toast.makeText(context, "Device Admin : disabled", Toast.LENGTH_LONG).show();
  }
}
