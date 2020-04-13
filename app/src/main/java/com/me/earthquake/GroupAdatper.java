package com.me.earthquake;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupAdatper extends RecyclerView.Adapter<GroupAdatper.ViewHolder> {

    //Adapter Class
    Context context;
    List<RssItem> data;

    public GroupAdatper(Context context, List<RssItem> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from( context );
        View view=inflater.inflate( R.layout.card ,parent,false);
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RssItem model = data.get(position);

        holder.name.setText(model.getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( context,DetailActivity.class);

                intent.putExtra( "name",model.getName() );
                intent.putExtra( "desc",model.getDesc());
                intent.putExtra( "date",model.getDate() );
                intent.putExtra( "cat",model.getCat() );
                context.startActivity( intent );

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        View itemView;

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById( R.id.name );

            this.itemView = itemView;
        }
    }
}
