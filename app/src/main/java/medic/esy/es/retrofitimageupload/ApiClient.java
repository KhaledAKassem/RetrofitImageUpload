package medic.esy.es.retrofitimageupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khaled on 26/08/17.
 */

public class ApiClient {

    private static final String Base_Url="http://10.0.2.2/contacts/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if(retrofit==null){

            retrofit=new Retrofit.Builder().baseUrl(Base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;



    }
}
