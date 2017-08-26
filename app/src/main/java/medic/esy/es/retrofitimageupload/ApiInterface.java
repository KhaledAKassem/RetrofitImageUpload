package medic.esy.es.retrofitimageupload;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by khaled on 26/08/17.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("upload.php")
    Call<imageClass> getImage(@Field("title") String title, @Field("image") String image);

}
