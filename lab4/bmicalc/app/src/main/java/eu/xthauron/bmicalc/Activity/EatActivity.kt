package eu.xthauron.bmicalc.Activity

import androidx.appcompat.app.AppCompatActivity

class EatActivity : AppCompatActivity() {
    private var iv_food_propose: ImageView? = null
    private var sharedPreferences: SharedPreferences? = null
    @Override
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eat)
        iv_food_propose = findViewById(R.id.iv_food_propose) as ImageView?
        sharedPreferences = getSharedPreferences(getString(R.string.preference_user_data), Context.MODE_PRIVATE)
        try {
            val bmiCategory: String = sharedPreferences.getString(getString(R.string.key_bmiCategory), BmiCategory.NORMAL.toString())
            iv_food_propose.setImageBitmap(getProposedFood(bmiCategory))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun getProposedFood(bmiCategory: String): Bitmap? {
        val foodCatalog = "eat/" + bmiCategory.toLowerCase()
        var foodImage: Bitmap? = null
        val foodImages: Array<String> = getAssets().list(foodCatalog)
        val foodId = (Math.random() * foodImages.size) as Int
        val foodProposeName = foodCatalog + "/" + foodImages[foodId]
        val stream: InputStream = getAssets().open(foodProposeName)
        foodImage = BitmapFactory.decodeStream(stream)
        stream.close()
        return foodImage
    }
}