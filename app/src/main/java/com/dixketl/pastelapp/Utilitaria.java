package com.dixketl.pastelapp;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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
    
    
    public static int chFragment(List<Fragment> fragmentList, int fragmentPosition
            , ViewGroup container,FragmentManager manager, int fragmentSubstitute){
        FragmentTransaction transaction;
        if (fragmentPosition != fragmentSubstitute && container.getChildAt(fragmentSubstitute)==null){
            container.removeAllViewsInLayout();
            transaction = manager.beginTransaction();
            transaction.replace(container.getId(),fragmentList.get(fragmentSubstitute))
                    .commitNow();
            return fragmentSubstitute;
        }else if (fragmentPosition != fragmentSubstitute){
            container.removeAllViewsInLayout();
            transaction = manager.beginTransaction();
            transaction.remove(fragmentList.get(fragmentPosition))
                    .commitNow();
            transaction = manager.beginTransaction();
            transaction.replace(container.getId(),fragmentList.get(fragmentSubstitute))
                    .commitNow();
            return fragmentSubstitute;
        }else {
            return fragmentPosition;
        }
    }

}
