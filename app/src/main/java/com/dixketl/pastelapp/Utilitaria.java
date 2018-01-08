package com.dixketl.pastelapp;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by juan on 29/12/17.
 */

public class Utilitaria {

    //Método estático para solicitar el permiso
    public static void solicitarPermiso(View view, String mesage, final Activity activity, final String permission, final int request_permissions){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission)) {

            Snackbar.make(view, mesage, Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(activity, new String[]{permission}, request_permissions);

                        }
                    }).show();
        }else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    request_permissions);
        }

    }


}
