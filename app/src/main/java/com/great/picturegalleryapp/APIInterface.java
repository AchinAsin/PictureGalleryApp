package com.great.picturegalleryapp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {


    @GET("?method=flickr.photos.getRecent&per_page=120&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<PojoClass>GetListResources();


}
