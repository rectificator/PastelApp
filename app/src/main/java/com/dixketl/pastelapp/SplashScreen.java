package com.dixketl.pastelapp;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dixketl.pastelapp.fragment.EnCurso;
import com.dixketl.pastelapp.fragment.Pasados;
import com.dixketl.pastelapp.fragment.Proximos;
import com.dixketl.pastelapp.vistas.LinearContainer;

import java.util.ArrayList;
import java.util.List;

import static com.dixketl.pastelapp.Utilitaria.chFragment;
import static com.dixketl.pastelapp.Utilitaria.solicitarPermiso;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    public static int REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private Button ok;
    private Button lnFacebook;
    private Button lnGoogle;
    private Button lnaccess;
    private Button btnElije;
    private Button btnCrea;
    private Button btnPedidos;
    private Button btnPedidosEsp;

    private GridView gridView;
    private GridView pan;
    private GridView relleno;
    private GridView cubierta;

    public LinearLayout base;
    public LinearLayout container;
    public LinearContainer containerS;
    public TabLayout tabs;


    private List<String> panList;
    private List<String> rellenoList;
    private List<String> cubiertaList;

    private ArrayList<Fragment> tabFragment;

    private int panSize;
    private int rellenoSize;
    private int cubiertaSize;
    private int fragmentPos = 0;

    /*private float dX;
    private float dY;
    private float X;
    private float Y;*/

    private android.support.v4.app.FragmentManager manager;
    private android.support.v4.app.FragmentTransaction transaction;

    //Strings de transicion
    String pantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pantalla = getIntent().getStringExtra("pantalla");
        if (pantalla.equals("elije")){
            elijePastel();
        }else if (pantalla.equals("crea")){
            creaPastel();
        }else if (pantalla.equals("pedido_especial")){
            pedidoEspecial();
        }else {
            setContentView(R.layout.a_splash_screen);
            base = findViewById(R.id.splashPrincipal);
        }
        manager = getSupportFragmentManager();
        tabFragment = new ArrayList<>();
        tabFragment.add(new EnCurso());
        tabFragment.add(new Pasados());
        tabFragment.add(new Proximos());
        permiso();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            splash();
        }
    }

    private void splash() {
        new Thread() {
            public void run() {

                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            base.setBackgroundColor(ContextCompat.
                                    getColor(SplashScreen.this, R.color.colorPrimary));
                        }
                    });
                    Thread.sleep(2500);
                    new Thread() {
                        public void run() {

                            try {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        explicacion();
                                    }
                                });
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    private void permiso() {
                /*Solicitando permiso*/
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            splash();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                solicitarPermiso(findViewById(R.id.splashPrincipal),
                        "Este permiso es necesario para acceder a la ubicación del dispositivo",
                        SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v == ok) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                login();

                            }
                        });
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        if (v==lnaccess){
            principal();
        }
        if (v==lnFacebook){
            principal();
        }
        if (v==lnGoogle){
            principal();
        }
        if (v==btnElije){
            Intent mapa = new Intent(SplashScreen.this,MapsActivity.class);
            mapa.putExtra("pantalla","elije");
            startActivity(mapa);

        }
        if (v==btnCrea){
            Intent mapa = new Intent(SplashScreen.this,MapsActivity.class);
            mapa.putExtra("pantalla","crea");
            startActivity(mapa);

        }
        if (v==btnPedidosEsp){
            Intent mapa = new Intent(SplashScreen.this,MapsActivity.class);
            mapa.putExtra("pantalla","pedido_especial");
            startActivity(mapa);
        }
        if (v==btnPedidos){
            misPedidos();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent==gridView){
            Toast.makeText(SplashScreen.this, "Pastel No. " + position,
                    Toast.LENGTH_SHORT).show();

        }
    }

    private void explicacion() {
        setContentView(R.layout.b_explicacion);
        ok = findViewById(R.id.ok);
        ok.setOnClickListener(SplashScreen.this);
    }

    private void login(){
        setContentView(R.layout.c_login);
        lnaccess = findViewById(R.id.loginAccess);
        lnFacebook = findViewById(R.id.btnFacebook);
        lnGoogle = findViewById(R.id.btnGoogle);

        lnaccess.setOnClickListener(this);
        lnFacebook.setOnClickListener(this);
        lnGoogle.setOnClickListener(this);
    }

    private void principal(){
        setContentView(R.layout.d_principal);
        btnElije = findViewById(R.id.elijePastel);
        btnCrea = findViewById(R.id.creaPastel);
        btnPedidos = findViewById(R.id.misPedidos);

        btnElije.setOnClickListener(this);
        btnCrea.setOnClickListener(this);
        btnPedidos.setOnClickListener(this);
    }
    private void elijePastel(){
        setContentView(R.layout.ea_elije_pastel);
        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);
    }
    private void creaPastel(){
        setContentView(R.layout.eb_crear_pastel);
        pan = findViewById(R.id.pan);
        relleno = findViewById(R.id.relleno);
        cubierta = findViewById(R.id.cubierta);

        pan.setAdapter(new ImageAdapter(this));
        relleno.setAdapter(new ImageAdapter(this));
        cubierta.setAdapter(new ImageAdapter(this));

        pan.setOnItemClickListener(this);
        relleno.setOnItemClickListener(this);
        cubierta.setOnItemClickListener(this);

        panList = new ArrayList<>();
        rellenoList = new ArrayList<>();
        cubiertaList = new ArrayList<>();

        /*panSize = panList.size();
        rellenoSize = rellenoList.size();
        cubiertaSize = cubiertaList.size();*/

        panSize = pan.getCount();
        rellenoSize = relleno.getCount();
        cubiertaSize = cubierta.getCount();

        pan.setNumColumns(panSize);
        relleno.setNumColumns(rellenoSize);
        cubierta.setNumColumns(cubiertaSize);

        pan.getLayoutParams().width = pan.getCount() * 520;
        relleno.getLayoutParams().width = relleno.getCount() * 520;
        cubierta.getLayoutParams().width = cubierta.getCount() * 520;

    }

    private void pedidoEspecial(){
        setContentView(R.layout.ed_pedido_especial);
    }

    private void misPedidos(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                            setContentView(R.layout.ec_mis_pedidos);
                            tabs = findViewById(R.id.tabs);
                            container = findViewById(R.id.tabContainer);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            containerS = new LinearContainer(SplashScreen.this);
                            containerS.setLayoutParams(params);
                            containerS.setId(R.id.contenedorS);
                            //containerS.setOnTouchListener(SplashScreen.this);
                            container.addView(containerS);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                    Thread.sleep(100);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            transaction = manager.beginTransaction();
                                            transaction.add(containerS.getId(),tabFragment.get(fragmentPos));
                                            transaction.commitNow();

                                        }
                                    });
                                    }catch (Exception e){e.printStackTrace();}
                                }
                            }).start();
                            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {

                                    if (tab == tabs.getTabAt(0)){
                                        fragmentPos = chFragment(tabFragment,fragmentPos,
                                                containerS,manager,0);

                                    }
                                    else if (tab == tabs.getTabAt(1)){
                                        fragmentPos = chFragment(tabFragment,fragmentPos,
                                                containerS,manager,1);

                                    }else if (tab == tabs.getTabAt(2)){
                                        fragmentPos = chFragment(tabFragment,fragmentPos,
                                                containerS,manager,2);

                                    }
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {

                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }
                            });
                            containerS.removeAllViews();



                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();


    }

    private void changeTab(){
        try {
            if (tabs.getTabAt(0).isSelected()){
                fragmentPos = chFragment(tabFragment,fragmentPos,
                        containerS,manager,0);
            }
            else if (tabs.getTabAt(1).isSelected()){
                fragmentPos = chFragment(tabFragment,fragmentPos,
                        containerS,manager,0);
            }else if (tabs.getTabAt(2).isSelected()){
                fragmentPos = chFragment(tabFragment,fragmentPos,
                        containerS,manager,0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

/*
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean value;
        if (v==containerS){
            v.performClick();
            switch (event.getAction()){
               case MotionEvent.ACTION_DOWN:
                   X = event.getRawX();

                   Log.e("ABAJO", Float.toString(X));
                   value=true;
                   break;
               case MotionEvent.ACTION_MOVE:
                   value=true;
                   dX = X-event.getRawX();
                   if (dX<0){
                       if (containerS.getChildCount()<2){
                           transaction = manager.beginTransaction();
                           transaction.add(containerS.getId(),tabFragment.get(fragmentPos+1));
                           transaction.commitNow();
                       }
                       containerS.setX(event.getX());
                   }else if (dX>0){
                       if (containerS.getChildCount()<2){

                           transaction = manager.beginTransaction();
                           if (fragmentPos<2){
                               transaction.remove(tabFragment.get(fragmentPos+1));
                           }else if (fragmentPos==2){
                               transaction.remove(tabFragment.get(fragmentPos));
                           }
                           transaction.commitNow();
                           transaction = manager.beginTransaction();
                           transaction.add(containerS.getId(),tabFragment.get(fragmentPos-1));
                           transaction.commitNow();
                           */
/*transaction = manager.beginTransaction();
                           transaction.add(containerS.getId(),tabFragment.get(fragmentPos));
                           transaction.commitNow();*//*

                       }
                       Log.e("MOVE", Float.toString(dX));
                       containerS.setX(v.getX());
                   }
                   break;
               case MotionEvent.ACTION_UP:
                   changeTab();
                   Log.e("ARRIBA", Float.toString(v.getX()));
                   value=true;
                   break;
               default:
                   value=false;
                   break;
           }
        }else {
            value = false;
        }
        return value;

    }
*/



}

