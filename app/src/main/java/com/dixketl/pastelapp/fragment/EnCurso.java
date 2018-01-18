package com.dixketl.pastelapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dixketl.pastelapp.R;
import com.dixketl.pastelapp.adapters.AdEnCurso;
import com.dixketl.pastelapp.pojos.AuxEnCurso;

import java.util.ArrayList;

import static com.dixketl.pastelapp.adapters.AdEnCurso.enRuta;


/**
 * Created by juan on 7/01/18.
 */

public class EnCurso extends android.support.v4.app.Fragment {

    private View v;
    private RecyclerView listaEnCurso;
    private LinearLayoutManager lManager;
    private RecyclerView.Adapter adaptador;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mp_en_curso,container,false);



        listaEnCurso = v.findViewById(R.id.lista_en_curso);
        ArrayList<AuxEnCurso> mListaEnCurso = new ArrayList<>();
        AuxEnCurso pastel1 = new AuxEnCurso();
        pastel1.setId("1");
        pastel1.setNombre("Pastel de chocolate oscuro");
        pastel1.setDescripcion("Pan de chocolate, relleno de fresa, cubierto de chocolate oscuro");
        pastel1.setFechaPago("Pedido realizado el 23/10/2023");
        pastel1.setProgreso(enRuta);

        mListaEnCurso.add(pastel1);

        lManager = new LinearLayoutManager(getActivity());
        lManager.removeAllViews();
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        listaEnCurso.setLayoutManager(lManager);
        adaptador = new AdEnCurso(mListaEnCurso);
        listaEnCurso.setAdapter(adaptador);

        return v;

    }
}
