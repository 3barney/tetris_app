package barney.com.tetris

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import barney.com.tetris.models.AppModel
import barney.com.tetris.storage.AppPreferences
import barney.com.tetris.view.TetrisView

class GameActivity : AppCompatActivity() {

    lateinit var tvHighScore: TextView
    lateinit var tvCurrentScore: TextView
    private lateinit var tetrisView: TetrisView

    lateinit var appPreferences: AppPreferences
    private val appModel: AppModel = AppModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        appPreferences = AppPreferences(this)
        appModel.setPreferences(appPreferences)

        val btnRestart = findViewById<Button>(R.id.btn_restart)
        tvHighScore = findViewById(R.id.tv_high_score)
        tvCurrentScore = findViewById(R.id.tv_current_score)

        tetrisView = findViewById(R.id.view_tetris)
        tetrisView.setActivity(this)
        tetrisView.setModel(appModel)

        tetrisView.setOnTouchListener(this::onTetrisTouchListener)
        btnRestart.setOnClickListener(this::btnRestartListener)

        updateHighScore()
        updateCurrentScore()
    }

    private fun btnRestartListener(view: View) {
        appModel.restartGame()
    }

    private fun onTetrisTouchListener(view: View, event: MotionEvent): Boolean {
        if (appModel.isGameOver() || appModel.isGameAwaitingStart()) {
            appModel.startGame()
            tetrisView.setGameCommandWithDelay(AppModel.Motions.DOWN)
        } else if (appModel.isGameActive()) {
            when (resolveTouchDirection(view, event)) {
                0 -> moveTetromino(AppModel.Motions.LEFT)
                1 -> moveTetromino(AppModel.Motions.ROTATE)
                2 -> moveTetromino(AppModel.Motions.DOWN)
                3 -> moveTetromino(AppModel.Motions.RIGHT)
            }
        }
        return true
    }

    private fun resolveTouchDirection(view: View, event: MotionEvent):Int {
        val x = event.x / view.width
        val y = event.y / view.height
        val direction: Int
        direction = if (y > x) {
            if (x > 1 - y) 2 else 0
        }
        else {
            if (x > 1 - y) 3 else 1
        }
        return direction
    }

    private fun moveTetromino(motions: AppModel.Motions) {
        if (appModel.isGameActive()) {
            tetrisView.setGameCommand(motions)
        }
    }

    private fun updateHighScore() {
        tvHighScore?.text = "${appPreferences.getHighScore()}"
    }

    private fun updateCurrentScore() {
        tvCurrentScore?.text = "0"
    }
}
