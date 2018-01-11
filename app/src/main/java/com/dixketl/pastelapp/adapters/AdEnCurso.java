package com.dixketl.pastelapp.adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dixketl.pastelapp.R;
import com.dixketl.pastelapp.pojos.AuxEnCurso;

import java.util.ArrayList;

/**
 * Created by dgaco on 10/01/18.
 */

public class AdEnCurso extends RecyclerView.Adapter<AdEnCurso.ViewHolder>{

    ArrayList<AuxEnCurso> mListEnCurso;
    ViewHolder holder;
    View v;

    public AdEnCurso(ArrayList<AuxEnCurso> mListEnCurso){
        this.mListEnCurso = mListEnCurso;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public String id;
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;
        public TextView fechaPedido;
        public TextView estatus;
        public ProgressBar estatusBar;


        public ViewHolder(View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.ec_imagen);
            nombre = itemView.findViewById(R.id.ec_nombre);
            descripcion = itemView.findViewById(R.id.ec_descripcion);
            fechaPedido = itemView.findViewById(R.id.ec_fechaPedido);
            estatus = itemView.findViewById(R.id.ec_estatus);
            estatusBar = itemView.findViewById(R.id.ec_progressBar);

        }

        public void bind(AuxEnCurso a){

            nombre.setText(a.getNombre());
            nombre.setText(a.getDescripcion());
            nombre.setText(a.getFechaPago());
            nombre.setText(a.getProgreso());


        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_card_en_curso,parent);


        holder = new ViewHolder(v);
        return holder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mListEnCurso.get(position));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mListEnCurso.size();
    }

    public class progresoActual extends AsyncTask<String,Void,Void>{

        public int progreso;

        @Override
        protected Void doInBackground(String... strings) {

            if (strings[0].equals("En proceso")){
                progreso = 3;
            }
            else if (strings[0].equals("Listo")) {

            }
            else if (strings[0].equals("En ruta")) {

            }
            else if (strings[0].equals("Domicilio visitado")) {

            }
            else if (strings[0].equals("Entregado :D")) {

            }
            else {

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }
    }


}
