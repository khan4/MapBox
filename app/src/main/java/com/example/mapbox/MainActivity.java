package com.example.mapbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapbox.database.EntityViewModel;
import com.example.mapbox.database.UserDataBase;
import com.example.mapbox.database.UserEntity;
import com.example.mapbox.factory.ViewModelProviderFactory;
import com.example.mapbox.mapactivity.MyMapActivity;
import com.example.mapbox.model.Feed;
import com.example.mapbox.mvvmviewmodel.UserViewModel;
import com.example.mapbox.recyclerview.FeedRecyclerAdapter;
import com.example.mapbox.resource.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements
        FeedRecyclerAdapter.FeedViewHolder.OnFeedClickListener {

    private static final String TAG = "MainActivity";

    @Inject
    ViewModelProviderFactory providerFactory;

    private UserViewModel viewModel;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FeedRecyclerAdapter adapter;
    private UserDataBase mDb;
    private EntityViewModel entityViewModel;
    private List<UserEntity> entityList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init views
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar.setVisibility(View.INVISIBLE);
        mDb = UserDataBase.getInstance(getApplicationContext());



         //Rx Flowable View Model
        viewModel = ViewModelProviders.of(this,providerFactory).get(UserViewModel.class);
        subscribeObserver();

        //User Db View Model

        entityViewModel = ViewModelProviders.of(this).get(EntityViewModel.class);
        entityViewModel.observeEntries().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                entityList = userEntities;
                initRecyclerView(userEntities);
            }
        });

    }

    public void subscribeObserver(){

        viewModel.observeData().observe(this, new Observer<Resource<List<Feed>>>() {
            @Override
            public void onChanged(Resource<List<Feed>> listResource) {

                if (listResource!=null){

                    switch (listResource.status){

                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;

                        case SUCCESS:
                            progressBar.setVisibility(View.INVISIBLE);
                            putData(listResource.data);
                            break;

                        case ERROR:
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }
            }
        });
    }
    public void putData(List<Feed> feedList){


        if (feedList.size()>0) {
            for (Feed feed : feedList) {
                 UserEntity userEntity = new UserEntity(feed.getUserId(), feed.getUserName(), feed.getUserLatitude(),
                        feed.getUserLongitude(), feed.getUserDescription());
                 entityViewModel.insert(userEntity);

            }
        }

    }

    public void initRecyclerView(List<UserEntity> userEntities){

        recyclerView.setLayoutManager(new LinearLayoutManager(this ));
        adapter = new FeedRecyclerAdapter(userEntities,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnFeedClick(int position) {

     //   Intent intent = new Intent(this, MyMapActivity.class);
      //  startActivity(intent);


        if (entityList.size() > 0){
            UserEntity entity = entityList.get(position);
            Intent intent = new Intent(this, MyMapActivity.class);
            intent.putExtra("ID",entity.getUserId());
            intent.putExtra("NAME",entity.getUserName());
            intent.putExtra("DESCRIPTION",entity.getDescription());
            intent.putExtra("LONGITUDE",entity.getLongitude());
            intent.putExtra("LATITUDE",entity.getLatitude());
            startActivity(intent);

        }

    }
}
