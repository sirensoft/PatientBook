package plkhealth.it.app.patientbook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by utehn on 16/6/2559.
 */
public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<MediaModel> data= Collections.emptyList();
    MediaModel current;
    int currentPos=0;



    public MediaAdapter(Context context, List<MediaModel> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.media_list, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        MediaModel current=data.get(position);
        myHolder.title.setText(current.mTitle);
        myHolder.descript.setText(current.mDesc);
        myHolder.read.setText(current.mRead);
        myHolder.date.setText(current.mDate);
        if(current.mType==1) {
            Glide.with(context).load(R.drawable.apps).into(myHolder.list_image);
        }else {
            Glide.with(context).load(R.drawable.rihanna).into(myHolder.list_image);
        }



    }




    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder  {

        TextView title;
        ImageView list_image;
        TextView descript;
        TextView read;
        TextView date;



        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
            list_image= (ImageView) itemView.findViewById(R.id.list_image);
            descript = (TextView) itemView.findViewById(R.id.descript);
            read = (TextView) itemView.findViewById(R.id.read);
            date = (TextView) itemView.findViewById(R.id.date);

        }


    }
}







