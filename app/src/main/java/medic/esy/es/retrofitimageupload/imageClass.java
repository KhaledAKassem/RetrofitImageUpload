package medic.esy.es.retrofitimageupload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled on 26/08/17.
 */

public class imageClass {


    @SerializedName("title")
    private String Title;

    @SerializedName("image")
    private String Image;
    @SerializedName("response")

    private String Response;



    public String getResponse() {
        return Response;
    }




}
