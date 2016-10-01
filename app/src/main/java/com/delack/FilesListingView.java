package com.delack;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.delack.Adapter.FilesDataAdapter;
import com.delack.Model.FileModel;
import com.delack.Network.NetworkCalls;
import com.delack.utils.Constants;

import java.util.ArrayList;

public class FilesListingView extends AppCompatActivity {
    String code;
    Context context = this;
    public static android.support.v7.widget.RecyclerView recyclerView;
    public static FilesDataAdapter adapter;
    public static Button delete;
    ArrayList<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_listing_view);
        recyclerView = (RecyclerView) findViewById(R.id.view);
        delete = (Button) findViewById(R.id.delete);
        delete.setVisibility(View.INVISIBLE);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (FileModel model : adapter.retrieveData()) {
                    ids.add(model.getId());
                }

                NetworkCalls.delete(context, Constants.token, ids);

            }
        });


        code = getIntent().getExtras().getString("code");
        Log.e("Code", " " + code);

        NetworkCalls.tokenUrl(context, code);
    }
}
