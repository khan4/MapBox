package com.example.mapbox.mvvmviewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mapbox.model.Feed;
import com.example.mapbox.networking.UserApi;
import com.example.mapbox.resource.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

    private UserApi userApi;
    private MediatorLiveData<Resource<List<Feed>>> mediatorLiveData;
    private static final String TAG = "UserViewModel";

    @Inject
    public UserViewModel(UserApi userApi){
        this.userApi = userApi;
    }

    public LiveData<Resource<List<Feed>>> observeData(){

        if (mediatorLiveData == null) {

            mediatorLiveData = new MediatorLiveData<>();

            mediatorLiveData.setValue(Resource.loading((List<Feed>) null));

            final LiveData<Resource<List<Feed>>> source = LiveDataReactiveStreams.fromPublisher(
                    userApi.getData()
                            .onErrorReturn(new Function<Throwable, List<Feed>>() {
                                @Override
                                public List<Feed> apply(Throwable throwable) throws Exception {

                                    Feed feed = new Feed();
                                    feed.setCheckId(-1);
                                    ArrayList<Feed> feedList = new ArrayList<>();
                                    feedList.add(feed);
                                    return feedList;
                                }
                            })

                            .map(new Function<List<Feed>, Resource<List<Feed>>>() {
                                @Override
                                public Resource<List<Feed>> apply(List<Feed> feeds) throws Exception {

                                    if (feeds != null && feeds.size() > 0) {
                                        Feed feed = feeds.get(0);
                                        Log.d(TAG, "apply: Feed check Id "+feed.getCheckId());
                                        if (feed.getCheckId() == -1) {
                                            return Resource.error("Some thing Went wrong", null);
                                        }
                                    }
                                    return Resource.success(feeds);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );

            mediatorLiveData.addSource(source, new Observer<Resource<List<Feed>>>() {
                @Override
                public void onChanged(Resource<List<Feed>> listResource) {

                    mediatorLiveData.setValue(listResource);
                    mediatorLiveData.removeSource(source);

                }
            });
        }

        return mediatorLiveData;

    }
}
