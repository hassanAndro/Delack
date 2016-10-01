package com.delack.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delack.Model.FileModel;
import com.delack.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hassan on 9/22/2016.
 */

public class FilesDataAdapter extends RecyclerView.Adapter<FilesDataAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<FileModel> models = new ArrayList<>();

    public FilesDataAdapter(Context mContext, ArrayList<FileModel> models) {
        this.mContext = mContext;
        this.models = models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_view, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        FileModel model = models.get(position);
        holder.fileName.setText("File Name: " + model.getName());
        holder.fileType.setText("File Type: " + model.getFileType());

        if (model.getFileType().equals("text")) {
            holder.imageView.setBackgroundResource(R.drawable.txt);

        } else if (model.getFileType().equals("apk")) {
            holder.imageView.setBackgroundResource(R.drawable.apk);
        } else {
            holder.imageView.setBackgroundResource(R.drawable.icon);

        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView fileName, fileType;
//        CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.image);
            fileName = (TextView) view.findViewById(R.id.fileName);
            fileType = (TextView) view.findViewById(R.id.fileType);
//            checkBox = (CheckBox) view.findViewById(R.id.check);

        }
    }


    public List<FileModel> retrieveData() {
        return models;
    }
}
