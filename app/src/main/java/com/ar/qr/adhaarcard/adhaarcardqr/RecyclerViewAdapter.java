package com.ar.qr.adhaarcard.adhaarcardqr;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhay on 20/6/17.
 */

public  class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
     List<DataAttributes> mFood;
     Context context;
    boolean b1=true;
    DataAttributes dataAttributes,contact;
     DataBAse dataBAse,db2;
    public RecyclerViewAdapter(List<DataAttributes> mFood) {
        this.mFood = mFood;
    }

    public RecyclerViewAdapter() {
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       context = parent.getContext();
         dataBAse=new DataBAse(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public  void  onBindViewHolder(final ViewHolder holder, final int position) {

       /* TextView mMeal = holder.idtxt;
        mMeal.setText(String.valueOf(food.getId()));
        TextView mDesert = holder.nametxt;
        mDesert.setText(food.getName());
        TextView addd = holder.nameadd;
       addd.setText(food.getAdress());*/
       /* final  String curnent_key= String.valueOf(position);
        final Contact infoData = mFood.get(position);*/
        holder.cardno.setText(mFood.get(position).getAADHAAR_DATA_TAG());
         holder.idtxt.setText((mFood.get(position).getAADHAR_UID_ATTR()));
        holder.nametxt.setText(mFood.get(position).getAADHAR_NAME_ATTR());
        holder.gtext.setText(mFood.get(position).getAADHAR_GENDER_ATTR());
        holder.dobtxt.setText(mFood.get(position).getAADHAR_YOB_ATTR());
        holder.nameadd.setText(mFood.get(position).getAADHAR_CO_ATTR());
         final String textdatano=holder.cardno.getText().toString();

       final DataAttributes cardData1=mFood.get(position);
        final String post_name = holder.nametxt.getText().toString();
        final String post_key = holder.cardno.getText().toString();
       holder.linearLayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
             builder1.setMessage("Do YOU WANT TO DELETE AdhaarCard of "+post_name);
             builder1.setCancelable(true);

             builder1.setPositiveButton(
                     "Yes",
                     new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             dataBAse.deleteContact(post_key);
                             removeItem(cardData1);
                             dialog.cancel();
                         }
                     });

             builder1.setNegativeButton(
                     "No",
                     new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             dialog.cancel();
                         }
                     });

             AlertDialog alert11 = builder1.create();
             alert11.show();





            /* Toast.makeText(context,String.valueOf(holder.nameadd.getText().toString()),Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(context, Main2Activity.class);
             intent.putExtra("key_post", post_key);
             intent.putExtra("key_pos", curnent_key);
             context.startActivities(new Intent[]{intent});*/


         }
     });


/*
        holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                removeItem(infoData);
                return true;
            }
        });*/
       /* holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,String.valueOf(post_key),Toast.LENGTH_SHORT).show();

                if(b1){
                   holder.relativeLayout.setVisibility(View.VISIBLE);


                    b1=false;
                }else {
                    relativeLayout.setVisibility(View.GONE);

                    b1=true;
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mFood.size();
    }



    public   class ViewHolder extends RecyclerView.ViewHolder  {
        public  TextView cardno;
        public TextView idtxt;
        public TextView nametxt;
        public TextView nameadd,dobtxt,gtext;

        LinearLayout linearLayout;
View mview;
        public ViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
            cardno=(TextView)itemView.findViewById(R.id.textView60);
            idtxt=(TextView)itemView.findViewById(R.id.textView10);
            nametxt=(TextView)itemView.findViewById(R.id.textView20);
            dobtxt=(TextView)itemView.findViewById(R.id.textView30);
            gtext=(TextView)itemView.findViewById(R.id.textView40);
            nameadd=(TextView)itemView.findViewById(R.id.textView50);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.card_delete);





    }

}
    private void removeItem(DataAttributes infoData) {

        int currPosition = mFood.indexOf(infoData);
        mFood.remove(currPosition);
        notifyItemRemoved(currPosition);

    }

}
