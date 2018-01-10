package com.dixketl.pastelapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by juan on 10/01/18.
 */

public class EnCurso extends RecyclerView.Adapter<EnCurso.ViewHolder> {

    List<EnCurso> enCursoList;

    public EnCurso(List<EnCurso> enCursoList){
        this.enCursoList = enCursoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }

        public void bind(EnCurso enCurso) {

        }

    }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }


}
