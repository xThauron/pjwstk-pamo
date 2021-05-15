package eu.xthauron.bmicalc.Activity

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import eu.xthauron.bmicalc.Enum.BmiCategory
import eu.xthauron.bmicalc.R
import java.io.IOException
import java.util.*

class EatActivity : AppCompatActivity() {
    private var iv_food_propose: ImageView? = null
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eat)
        iv_food_propose = findViewById<View>(R.id.iv_food_propose) as ImageView
        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_user_data), MODE_PRIVATE)
        try {
            val bmiCategory = sharedPreferences.getString(
                getString(R.string.key_bmiCategory),
                BmiCategory.NORMAL.toString()
            )
            iv_food_propose!!.setImageBitmap(getProposedFood(bmiCategory))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun getProposedFood(bmiCategory: String?): Bitmap? {
        val foodCatalog = "eat/" + bmiCategory!!.lowercase(Locale.getDefault())
        var foodImage: Bitmap? = null
        val foodImages = assets.list(foodCatalog)
        val foodId = (Math.random() * foodImages!!.size).toInt()
        val foodProposeName = foodCatalog + "/" + foodImages[foodId]
        val stream = assets.open(foodProposeName)
        foodImage = BitmapFactory.decodeStream(stream)
        stream.close()
        return foodImage
    }
}