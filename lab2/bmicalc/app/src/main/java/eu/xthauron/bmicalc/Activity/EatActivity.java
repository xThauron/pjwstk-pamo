package eu.xthauron.bmicalc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import eu.xthauron.bmicalc.Enum.BmiCategory;
import eu.xthauron.bmicalc.R;

public class EatActivity extends AppCompatActivity {
    private ImageView iv_food_propose;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        iv_food_propose = (ImageView) findViewById(R.id.iv_food_propose);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_user_data), Context.MODE_PRIVATE);
        try {
            String bmiCategory = sharedPreferences.getString(getString(R.string.key_bmiCategory), BmiCategory.NORMAL.toString());
            iv_food_propose.setImageBitmap(getProposedFood(bmiCategory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getProposedFood(String bmiCategory) throws IOException {
        String foodCatalog = "eat/" + bmiCategory.toLowerCase();
        Bitmap foodImage = null;

        String[] foodImages = getAssets().list(foodCatalog);
        int foodId = (int)(Math.random() * foodImages.length);
        String foodProposeName = foodCatalog + "/" + foodImages[foodId];
        InputStream stream = getAssets().open(foodProposeName);
        foodImage = BitmapFactory.decodeStream(stream);
        stream.close();

        return foodImage;
    }
}