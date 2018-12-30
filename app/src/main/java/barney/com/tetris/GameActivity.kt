package barney.com.tetris

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import barney.com.tetris.storage.AppPreferences

class GameActivity : AppCompatActivity() {

    lateinit var tvHighScore: TextView
    lateinit var tvCurrentScore: TextView
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        appPreferences = AppPreferences(this)
        tvHighScore = findViewById(R.id.tv_high_score)
        tvCurrentScore = findViewById(R.id.tv_current_score)

        updateHighScore()
        updateCurrentScore()
    }

    private fun updateHighScore() {
        tvHighScore?.text = "${appPreferences.getHighScore()}"
    }

    private fun updateCurrentScore() {
        tvCurrentScore?.text = "0"
    }
}
