package com.eastail.yellina

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.eastail.yellina.Database.DbHelper
import com.eastail.yellina.Database.Helper
import com.eastail.yellina.Database.Location
import com.eastail.yellina.Database.Store
import com.eastail.yellina.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var dbHelper: DbHelper
    private var numX: Int? = null
    private lateinit var navController: NavController
    private lateinit var coins: TextView
    private lateinit var helper: Helper
    private lateinit var looseVisaSound: MediaPlayer
    var curAvatar: Int? = null
    var mMediaPlayer: MediaPlayer? = null

    /* List of Music */
    private val chinaMusicList = listOf(
        R.raw.china_alalani,
        R.raw.china_angelo,
        R.raw.china_epikhighfly,
        R.raw.china_mainchinesetrio,
        R.raw.china_superidolhardstyle,
        R.raw.china_lorelei,
        R.raw.china_jaychou
    )
    private val americaMusicList = listOf(
        R.raw.usa_icejjfish,
        R.raw.usa_newyork,
        R.raw.usa_wild,
        R.raw.usa_cowboys,
        R.raw.usa_texas,
        R.raw.usa_angel,
        R.raw.usa_shoulder,
        R.raw.usa_imagine
    )
    private val africaMusicList = listOf(
        R.raw.africa_bornfree,
        R.raw.africa_brasil,
        R.raw.africa_foulo,
        R.raw.africa_lionking,
        R.raw.africa_chuchuka,
        R.raw.africa_gav
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        /* Основное */
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        /* Инициализация базы данных */
        dbHelper = DbHelper(applicationContext)
        dbInit()

        numX = Random().nextInt(currentCountry().size)

        /* Navigation */
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(bottomNavigationView, navController)

        /* Find */
        coins = findViewById(R.id.tvMoney)
        helper = getHelper()
        coins.text = helper.coins.toString()


        /* Music */
        startMusic()
    }

    fun playLunch1(sound: List<Int>) {
        val lunchMediaPlayer =
            MediaPlayer.create(applicationContext, sound[Random().nextInt(sound.size)])
        lunchMediaPlayer.isLooping = false
        lunchMediaPlayer.start()
        lunchMediaPlayer.setOnCompletionListener {
            lunchMediaPlayer.release()
        }
    }

    fun playLunch2(sound: List<Int>) {
        val lunchMediaPlayer =
            MediaPlayer.create(applicationContext, sound[Random().nextInt(sound.size)])
        lunchMediaPlayer.isLooping = false
        lunchMediaPlayer.start()
        lunchMediaPlayer.setOnCompletionListener {
            lunchMediaPlayer.release()
        }
    }

    fun playLunch3(sound: List<Int>) {
        val lunchMediaPlayer =
            MediaPlayer.create(applicationContext, sound[Random().nextInt(sound.size)])
        lunchMediaPlayer.isLooping = false
        lunchMediaPlayer.start()
        lunchMediaPlayer.setOnCompletionListener {
            lunchMediaPlayer.release()
        }
    }

    fun alimeSound() {
        val alimeMP = MediaPlayer.create(applicationContext, R.raw.alime_dadada)
        alimeMP.isLooping = false
        alimeMP.start()
        alimeMP.setOnCompletionListener {
            alimeMP.release()
        }
    }

    fun playLose() {
        looseVisaSound = MediaPlayer.create(applicationContext, R.raw.test_china_loose)
        looseVisaSound.isLooping = false
        looseVisaSound.start()
        looseVisaSound.setOnCompletionListener {
            looseVisaSound.release()
            findViewById<View>(R.id.viewChina).visibility = View.GONE
            if (dbHelper.getHelper()[0].music) playNextSound()
        }
    }

    private fun playSound(sound: List<Int>, x: Int) {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(applicationContext, sound[x])
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.setVolume(0.4f, 0.4f)
            mMediaPlayer?.start()
            mMediaPlayer?.setOnCompletionListener {
                playNextSound()
            }
        } else {
            if (dbHelper.getHelper()[0].music) mMediaPlayer?.start()
        }
    }

    fun playNextSound() {
        if (dbHelper.getHelper()[0].music) {
            mMediaPlayer!!.release()
            numX = if (numX!! >= currentCountry().size - 1) 0 else numX!! + 1
            mMediaPlayer = MediaPlayer.create(applicationContext, currentCountry()[numX!!])
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.setVolume(0.4f, 0.4f)
            mMediaPlayer?.start()
            mMediaPlayer?.setOnCompletionListener {
                playNextSound()
            }
        } else {
            Toast.makeText(applicationContext, "Музыка отключена в настройках", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun currentCountry(): List<Int> {
        val here = dbHelper.getLocation()
        return when {
            here[0].isLocated -> africaMusicList
            here[1].isLocated -> chinaMusicList
            here[2].isLocated -> americaMusicList
            else -> africaMusicList
        }
    }

    fun startMusic() {
        if (mMediaPlayer != null) {
            if (dbHelper.getHelper()[0].music) mMediaPlayer?.start()
        } else {
            if (dbHelper.getHelper()[0].music) playSound(currentCountry(), numX!!)
        }
    }

    fun soundBuy() {
        val soundBuy = MediaPlayer.create(applicationContext, R.raw.click)
        soundBuy.isLooping = false
        soundBuy.start()
        soundBuy.setOnCompletionListener {
            soundBuy.release()
        }
    }

    fun wolfSound() {
        val wolfSound = MediaPlayer.create(applicationContext, R.raw.wolf)
        wolfSound.isLooping = false
        wolfSound.start()
        wolfSound.setOnCompletionListener {
            wolfSound.release()
        }
    }


    private fun dbInit() {
        /* Database opening */
        /* Values */
        val locationList = listOf(
            Location(
                0,
                "Mbabane",
                image = R.drawable.africarec,
                isLocated = true,
                isTested = true,
                imageBig = R.drawable.mbabane
            ),
            Location(
                1,
                "Chuxiong",
                image = R.drawable.chinarec,
                isLocated = false,
                isTested = false,
                imageBig = R.drawable.chuxiong
            ),
            Location(
                2,
                "Dallas",
                image = R.drawable.usarec,
                isLocated = false,
                isTested = false,
                imageBig = R.drawable.dallas
            )
        )
        val storeList = listOf(
            Store(
                0, "Вилка", "Вилка Гаргантюа.", 25,
                R.drawable.fork, R.drawable.water_color_aquarel, 1, true, 1
            ),
            Store(
                1, "Авель", "Авель принес дар Господу от первородных стада своего.", 200,
                R.drawable.knife, R.drawable.bordaux_aquarel, 0, true, 3
            ),
            Store(
                2, "Каин", "Каин принес дар Господу от плодов земли.", 750,
                R.drawable.smalldagger, R.drawable.bordaux_aquarel, 0, true, 5
            ),
            Store(
                3, "Йылдыз", "Арабский тесак, почему-то имеющий тюркское название.", 1500,
                R.drawable.tesak, R.drawable.pink_vertical_aquarel, 0, true, 10
            ),
            Store(
                4,
                "Катана",
                "Может спровоцировать владельца на убийство.",
                3000,
                R.drawable.katana,
                R.drawable.blue_aquarel,
                0,
                true,
                15
            ),
            Store(
                5, "Фламберг", "Меч итальянского кондотьера Джованни Агуто", 6000,
                R.drawable.long_dagger, R.drawable.blue_aquarel, 0, true, 25
            ),
            Store(
                6, "Godeater", "Из-за него судного дня до сих пор не произошло.", 10000,
                R.drawable.gow_dagger, R.drawable.tricolor_aquarel, 0, true, 40
            ),
            Store(
                7,
                "Булава",
                "Холодное оружие дробящего действия в виде металлического шара с шипами.",
                25000,
                R.drawable.chaos_bulava,
                R.drawable.orange_aquarel,
                0,
                true,
                85
            ),
            Store(
                8, "Хайтарма", "Restitution. Rückkehr. Возвращение.", 55000,
                R.drawable.steampunk_pistol, R.drawable.orange_aquarel, 0, true, 130
            ),
            Store(
                9, "Maymoon", "Назван в честь майской луны. По ошибке путают с Обезьяной.", 100000,
                R.drawable.triple_pistol, R.drawable.yellow_aquarel, 0, true, 200
            ),
            Store(
                10, "Байрам", "Эр кунь олсун.", 200000,
                R.drawable.tesla_gun, R.drawable.ranbow_aquarel, 0, true, 350
            ),
            Store(
                11,
                "Невидимка",
                "Вопреки всем предрассудкам, не невидимая. Позволяет путешествовать.",
                3500,
                R.drawable.nevidimka,
                R.drawable.water_color_aquarel,
                0,
                true,
                0
            ),
            Store(
                12, "Кусок", "Бревно. Новые анимации нажатия.", 15000,
                R.drawable.wood, R.drawable.bordaux_aquarel, 0, true, 0
            ),
            Store(
                13, "Пельмешки", "Добавляет Алиме. Казалось бы, причём здесь Алиме...", 50000,
                R.drawable.pelmen, R.drawable.pink_vertical_aquarel, 0, true, 0
            ),
            Store(
                14, "Калыван", "Добавляет возможность менять аватар.", 125000,
                R.drawable.emine_kalyvan, R.drawable.blue_aquarel, 0, true, 0
            ),
            Store(
                15, "Дакто шрек", "Добавляет гифку с полной версией Шрека.", 350000,
                R.drawable.shrek, R.drawable.yellow_aquarel, 0, true, 0
            ),
            Store(
                16, "Заколка", "Заколка с Али за 100 рублей. Новые анимации нажатия.", 600000,
                R.drawable.zakolka, R.drawable.tricolor_aquarel, 0, true, 0
            ),
            Store(
                17,
                "Уно-реверс",
                "Уно потеряли, а реверс остался. Элина чувствует себя... неоднозначно.",
                3000000,
                R.drawable.uno,
                R.drawable.orange_aquarel,
                0,
                true,
                0
            ),
            Store(
                18, "Hanged", "The Hanged Man XII. Ключ к прохождению игры", 100000000,
                R.drawable.hangedman, R.drawable.ranbow_aquarel, 0, true, 0
            ),
        )
        val helperList = listOf(Helper(0, music = true, sound = true, cheats = false, coins = 0))

        /* Initialization */
        for (store in storeList) {
            dbHelper.addStore(store)
        }

        for (loc in locationList) {
            dbHelper.addLocation(loc)
        }

        for (help in helperList) {
            dbHelper.addHelper(help)
        }
    }

    private fun getHelper(): Helper {
        return dbHelper.getHelper().first()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun onPause() {
        super.onPause()
        if (mMediaPlayer!!.isPlaying) mMediaPlayer?.pause()
        val theCoins = Integer.parseInt(coins.text.toString())
        dbHelper.updateCoins(0, theCoins)
    }

    override fun onResume() {
        super.onResume()

        /* Coin skin */
        val coinLottie = findViewById<LottieAnimationView>(R.id.lottieDollar)
        val here = dbHelper.getLocation()
        val curCoin = when {
            here[0].isLocated -> R.raw.dollar_coin
            here[1].isLocated -> R.raw.yen_coin
            here[2].isLocated -> R.raw.dollar_coin
            else -> R.raw.dollar_coin
        }
        coinLottie.setAnimation(curCoin)

        /* The BGM Thing */
        if (mMediaPlayer != null && !mMediaPlayer?.isPlaying!! && dbHelper.getHelper()[0].music) {
            mMediaPlayer!!.start()
        }
    }

}
