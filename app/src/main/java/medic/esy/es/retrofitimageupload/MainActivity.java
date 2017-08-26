package medic.esy.es.retrofitimageupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private EditText editText;
    private Button choose,upload;
    private Bitmap bitmap;
    private static final int image_request=777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=(ImageView) findViewById(R.id.imageView);
        editText=(EditText) findViewById(R.id.edit);
        choose =(Button) findViewById(R.id.choose_image);
        upload =(Button) findViewById(R.id.up);
        choose.setOnClickListener(this);
        upload.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.choose_image:
                selectImage();
                break;
            case R.id.up:
                uploadImage();
                break;

        }

    }
    private void uploadImage(){
        String Image=imageToString();
        String Title=editText.getText().toString();
        ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<imageClass> call=apiInterface.getImage(Title,Image);
        call.enqueue(new Callback<imageClass>() {
            @Override
            public void onResponse(Call<imageClass> call, Response<imageClass> response) {
                imageClass image=response.body();
                Toast.makeText(MainActivity.this,"server responce "+image.getResponse(),Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                choose.setEnabled(false);
                upload.setEnabled(true);
                editText.setText("");
            }

            @Override
            public void onFailure(Call<imageClass> call, Throwable t) {

            }
        });

    }
    private void selectImage(){

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,image_request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==image_request && resultCode==RESULT_OK && data!=null){

            Uri path=data.getData();

            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                choose.setEnabled(false);
                upload.setEnabled(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[]imgbyte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }
}
