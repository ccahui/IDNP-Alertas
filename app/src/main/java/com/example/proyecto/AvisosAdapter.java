package com.example.proyecto;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Model.Aviso;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AvisosAdapter extends RecyclerView.Adapter<AvisosAdapter.Avisosviewholder> {
    private Context mContext;
    private List<Aviso> aviso;
    private OnItemClickListener mListener;

    public AvisosAdapter(Context context, List<Aviso> avisos) {
        mContext = context;
        aviso = avisos;
    }

    @NonNull
    @Override
    public Avisosviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_avisos,parent,false);
        return new Avisosviewholder(v);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AvisosAdapter.Avisosviewholder holder, int position) {
        holder.textView_nombre_aviso.setText(aviso.get(position).getNombre());
        holder.textView_apellido_aviso.setText(aviso.get(position).getApellido());
        //holder.textView_descripcion_aviso.setText(aviso.get(position).getDescripcion());
        Glide.with(mContext)
                .load(aviso.get(position).getmImageUrl())
                .centerCrop()
                .into(holder.imageView);
        /*Picasso.get()
                .load(aviso.get(position).getmImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return aviso.size();
    }


    public class Avisosviewholder extends RecyclerView.ViewHolder  implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public ImageView imageView;
        public TextView textView_nombre_aviso;
        public TextView textView_apellido_aviso;
        //public TextView textView_descripcion_aviso;

        public Avisosviewholder(View view) {
            super(view);
            this.textView_nombre_aviso = view.findViewById(R.id.textView_nombre_aviso);
            this.textView_apellido_aviso = view.findViewById(R.id.textView_apellido_aviso);
            //this.textView_descripcion_aviso = view.findViewById(R.id.textView_descripcion_aviso);
            this.imageView = view.findViewById(R.id.imageView_foto);

            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE,1,1,"Do whtaever");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhattEverCLick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        void onWhattEverCLick(int position);

        void onDeleteClick(int position);
    }
    public void setOnItemCliclListener(OnItemClickListener listener){
        mListener = listener;
    }
}
