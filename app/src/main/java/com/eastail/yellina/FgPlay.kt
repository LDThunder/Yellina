package com.eastail.yellina

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.eastail.yellina.Database.DbHelper
import com.eastail.yellina.Database.Store
import com.eastail.yellina.databinding.FragmentFgPlayBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifImageView
import java.util.*
import kotlin.math.roundToInt

class FgPlay : Fragment(R.layout.fragment_fg_play) {

    /* List of Backgrounds */
    private val chinaBgList = listOf(
        R.drawable.china1,
        R.drawable.china2,
        R.drawable.china3,
        R.drawable.china4,
        R.drawable.china5
    )
    private val americaBgList = listOf(
        R.drawable.america1,
        R.drawable.america2,
        R.drawable.america3,
        R.drawable.america4,
        R.drawable.america5
    )
    private val africaBgList = listOf(
        R.drawable.africa2,
        R.drawable.africa3,
        R.drawable.africa4,
        R.drawable.africa5
    )

    /* List of Techniques */
    private var yoyoList1 = listOf(
        Techniques.Shake, Techniques.Bounce, Techniques.Wave
    )
    private var yoyoList2 = listOf(
        Techniques.Shake, Techniques.Bounce, Techniques.Wave,
        Techniques.Pulse, Techniques.Swing, Techniques.Tada
    )
    private var yoyoList3 = listOf(
        Techniques.Shake, Techniques.Bounce, Techniques.Wave,
        Techniques.Pulse, Techniques.Swing, Techniques.Tada,
        Techniques.RubberBand, Techniques.Wobble, Techniques.StandUp
    )

    /* List of Click Sounds */
    private val africaClickSounds = listOf(
        R.raw.dadadada,
        R.raw.lalala,
        R.raw.lalalala,
        R.raw.lalalalala,
        R.raw.lalalalalala,
        R.raw.nanana,
        R.raw.noneed,
        R.raw.noney,
        R.raw.nyanyanaa,
        R.raw.oh,
        R.raw.ohkoko
    )
    private val chinaClickSounds = listOf(
        R.raw.tridcattri,
        R.raw.eshe,
        R.raw.gusiguis,
        R.raw.lave,
        R.raw.nuvan,
        R.raw.byla,
        R.raw.vlesu,
        R.raw.zdyhaet,
        R.raw.zimoiletom,
        R.raw.zushimoshi
    )
    private val europeClickSounds = listOf(
        R.raw.alyomanait,
        R.raw.because,
        R.raw.gdezhti,
        R.raw.minussily,
        R.raw.roslazimoiletom,
        R.raw.thisalrayt,
        R.raw.tipridi,
        R.raw.watchmebaut,
        R.raw.youlove,
        R.raw.zaryu
    )

    /* Lateinit */
    private var store: List<Store>? = null
    private lateinit var bind: FragmentFgPlayBinding
    private lateinit var play: TextView
    private lateinit var background: ImageView
    private lateinit var lottieSettings: View
    private lateinit var lottiePlay: LottieAnimationView
    private lateinit var lunch: ImageView
    private lateinit var coin: TextView
    private lateinit var dbHelper: DbHelper
    private lateinit var tvPlus: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* DbHelper */
        dbHelper = DbHelper(context)
        store = dbHelper.getStore()

        /* Bind */
        bind = FragmentFgPlayBinding.bind(view)

        /* Find */
        background = view.findViewById(R.id.fgPlayBackground)
        lottieSettings = view.findViewById(R.id.lottieSettings)
        lottiePlay = view.findViewById(R.id.lottiePlay)
        lunch = view.findViewById(R.id.igLunch)
        coin = requireActivity().findViewById(R.id.tvMoney)
        play = view.findViewById(R.id.tvPlay)
        tvPlus = requireActivity().findViewById(R.id.tvPlus)

        /* Call The Main Activity Methods */
        view.findViewById<MaterialButton>(R.id.songButton).setOnClickListener {
            (activity as MainActivity).playNextSound()
        }

        /* YoYo */
        YoYo.with(Techniques.BounceIn)
            .duration(500)
            .repeat(0)
            .playOn(lunch)

        YoYo.with(Techniques.Pulse)
            .duration(45000)
            .repeat(YoYo.INFINITE)
            .playOn(background)

        /* Background && Face && tvPlay && Flag */
        val here = dbHelper.getLocation()
        background.setImageResource(currentCountry().random())
        val curFace = when {
            here[0].isLocated -> R.drawable.africa
            here[1].isLocated -> R.drawable.asia
            here[2].isLocated -> R.drawable.europe
            else -> R.drawable.africa
        }
        play.text = when {
            here[0].isLocated -> "Африка"
            here[1].isLocated -> "Китай"
            here[2].isLocated -> "США"
            else -> "Космос"
        }
        val curFlag = when {
            here[0].isLocated -> R.raw.africaflag
            here[1].isLocated -> R.raw.chinaflagwave
            here[2].isLocated -> R.raw.usaflagwave
            else -> R.raw.africaflag
        }
        lottiePlay.apply {
            this.setAnimation(curFlag)
            this.loop(true)
            this.playAnimation()
        }
        lunch.setImageResource(curFace)
        if ((activity as MainActivity).curAvatar != null) {
            when ((activity as MainActivity).curAvatar) {
                1 -> lunch.setImageResource(R.drawable.alime_catch)
                2 -> lunch.setImageResource(R.drawable.alime_krut)
                3 -> lunch.setImageResource(R.drawable.africa)
                4 -> lunch.setImageResource(R.drawable.asia)
                5 -> lunch.setImageResource(R.drawable.europe)
                6 -> lunch.setImageResource(R.drawable.emine_kalyvan)
                7 -> lunch.setImageResource(R.drawable.spakuha)
                8 -> lunch.setImageResource(R.drawable.letit)
            }
        }

        // Shrek
        val curStore = store
        val shrekView = requireActivity().findViewById<GifImageView>(R.id.shrekGif)
        if (!curStore!![15].isAble) {
            shrekView.visibility = View.VISIBLE
        }

        // Reverse
        if (!curStore[17].isAble) {
            lunch.rotationX = 45f
            lunch.rotationY = 45f
            view.findViewById<MaterialButton>(R.id.songButton).rotationX = 180f
            if (shrekView.visibility == View.VISIBLE) {
                shrekView.rotationX = 180f
            }
            play.rotationX = 180f
        }

        /* Settings */
        lottieSettings.setOnClickListener {
            settingsPlay()
        }

        /* Вычисление прибавки */
        val isBought = dbHelper.getStore().filter { it.isBought > 0 }
        val concatinate = if (isBought.isNotEmpty()) {
            isBought.map {
                when {
                    it.isBought in 10..24 -> ((it.isBought * it.idleUp) * 1.25).roundToInt()
                    it.isBought in 25..49 -> ((it.isBought * it.idleUp) * 1.5).roundToInt()
                    it.isBought in 50..74 -> ((it.isBought * it.idleUp) * 1.75).roundToInt()
                    it.isBought in 75..99 -> ((it.isBought * it.idleUp) * 2.0).roundToInt()
                    it.isBought in 100..249 -> ((it.isBought * it.idleUp) * 2.25).roundToInt()
                    it.isBought in 250..499 -> ((it.isBought * it.idleUp) * 2.5).roundToInt()
                    it.isBought >= 500 -> ((it.isBought * it.idleUp) * 3)
                    else -> it.isBought * it.idleUp
                }
            }.reduce { x, y -> x + y }
        } else {
            0
        }

        /* Какая страна сейчас? */
        val whatCountry = dbHelper.getLocation().filter { it.isLocated }

        /* Какие звуки использовать? */
        val chrome = when (whatCountry[0].id) {
            0 -> africaClickSounds
            1 -> chinaClickSounds
            2 -> europeClickSounds
            else -> africaClickSounds
        }

        /* LunchSetClick */
        lunch.setOnClickListener {
            shake()
            /* Вероятность звуков  */
            GlobalScope.launch {
                if (dbHelper.getHelper()[0].sound) {
                    val rand = Random().nextInt(12)
                    if (rand == 0) (activity as MainActivity).playLunch1(chrome)
                    if (rand == 1) (activity as MainActivity).playLunch2(chrome)
                    if (rand == 2) (activity as MainActivity).playLunch3(chrome)
                }
            }

            /* Прибавка голды */
            val theCoins = Integer.parseInt(coin.text.toString())

            if (!dbHelper.getHelper()[0].cheats) {
                coin.text = (theCoins + concatinate).toString()
                tvPlus.text = "+${concatinate}"
            } else {
                coin.text = (theCoins + (concatinate * 3)).toString()
                tvPlus.text = "+${concatinate * 3}"
            }

            if (!store!![13].isAble) {
                val rand = Random().nextInt(33)
                if (rand == 0) alimeAppearance(concatinate, theCoins)
            }
            /* Анимации прибавки */
            tvPlus.visibility = View.VISIBLE
            YoYo.with(Techniques.BounceInDown)
                .duration(350)
                .interpolate(DecelerateInterpolator())
                .playOn(tvPlus)
            YoYo.with(Techniques.FadeOut)
                .duration(550)
                .interpolate(AccelerateDecelerateInterpolator())
                .playOn(tvPlus)
        }
        lunch.setOnLongClickListener {
            if (!store!![14].isAble) {
                avatarDialog()
            } else {
                shouldBuyAvatar()
            }
        }
    }

    private fun shouldBuyAvatar(): Boolean {
        Toast.makeText(
            context,
            "Купите 'Калывана' в Магазине, чтобы менять аватарки!",
            Toast.LENGTH_SHORT
        ).show()
        return true
    }

    private fun avatarDialog(): Boolean {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_avatar, null)
        val dialog = context?.let { x -> Dialog(x) }
        /* Dialog logic */
        with(dialog) {
            /* Init parameters */
            this?.setContentView(dialogBinding)
            this?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this?.setCancelable(true)
            this?.show()
            /* Find */
            val alime1 = this?.findViewById<ImageView>(R.id.imageView10)
            val alime2 = this?.findViewById<ImageView>(R.id.imageView11)
            val alime3 = this?.findViewById<ImageView>(R.id.imageView17)
            val alime4 = this?.findViewById<ImageView>(R.id.imageView18)
            val elina1 = this?.findViewById<ImageView>(R.id.imageView12)
            val elina2 = this?.findViewById<ImageView>(R.id.imageView14)
            val elina3 = this?.findViewById<ImageView>(R.id.imageView13)
            val emine1 = this?.findViewById<ImageView>(R.id.imageView15)
            /* Применение */
            alime1?.setOnClickListener {
                lunch.setImageResource(R.drawable.alime_catch)
                (activity as MainActivity).curAvatar = 1
                (activity as MainActivity).alimeSound()
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
            alime2?.setOnClickListener {
                lunch.setImageResource(R.drawable.alime_krut)
                (activity as MainActivity).curAvatar = 2
                (activity as MainActivity).alimeSound()
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
            alime3?.setOnClickListener {
                lunch.setImageResource(R.drawable.spakuha)
                (activity as MainActivity).curAvatar = 7
                (activity as MainActivity).alimeSound()
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
            alime4?.setOnClickListener {
                lunch.setImageResource(R.drawable.letit)
                (activity as MainActivity).curAvatar = 8
                (activity as MainActivity).alimeSound()
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
            elina1?.setOnClickListener {
                lunch.setImageResource(R.drawable.africa)
                (activity as MainActivity).curAvatar = 3
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
            elina2?.setOnClickListener {
                lunch.setImageResource(R.drawable.asia)
                (activity as MainActivity).curAvatar = 4
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
            elina3?.setOnClickListener {
                lunch.setImageResource(R.drawable.europe)
                (activity as MainActivity).curAvatar = 5
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
            emine1?.setOnClickListener {
                lunch.setImageResource(R.drawable.emine_kalyvan)
                (activity as MainActivity).curAvatar = 6
                YoYo.with(Techniques.BounceIn)
                    .duration(350)
                    .repeat(0)
                    .playOn(lunch)
                this?.dismiss()
            }
        }
        return true
    }

    /* Settings */
    private fun settingsPlay() {
        /* DbHelper */
        val dbHelperSettings = DbHelper(context)
        val currentHelper = dbHelperSettings.getHelper()[0]

        /* Dialog inflation */
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_settings, null)
        val dialog = context?.let { x -> Dialog(x) }
        /* Dialog logic */
        with(dialog) {
            /* Init parameters */
            this?.setContentView(dialogBinding)
            this?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this?.setCancelable(true)
            this?.show()

            /* Find */
            val buttonPrimenit = this?.findViewById<MaterialButton>(R.id.otvetitUsa)
            val lottie1 = this?.findViewById<LottieAnimationView>(R.id.lottie1)
            val lottie2 = this?.findViewById<LottieAnimationView>(R.id.lottie2)
            val lottie3 = this?.findViewById<LottieAnimationView>(R.id.lottie3)
            val lottie4 = this?.findViewById<LottieAnimationView>(R.id.lottie4)
            val lottie5 = this?.findViewById<LottieAnimationView>(R.id.lottie5)

            /* View */
            lottie4?.progress = 0.5f
            lottie5?.progress = 0.5f
            if (currentHelper.music) lottie1?.progress = 0.5f
            if (currentHelper.sound) lottie2?.progress = 0.5f
            if (currentHelper.cheats) lottie3?.progress = 0.5f

            /* Set Listeners */
            lottie1?.setOnClickListener {
                if (lottie1.progress > 0f && lottie1.progress <= 0.5f) doAnimationTo(
                    lottie1,
                    false
                ) else doAnimationTo(lottie1, true)
            }
            lottie2?.setOnClickListener {
                if (lottie2.progress > 0f && lottie2.progress <= 0.5f) doAnimationTo(
                    lottie2,
                    false
                ) else doAnimationTo(lottie2, true)
            }
            lottie3?.setOnClickListener {
                if (lottie3.progress > 0f && lottie3.progress <= 0.5f) doAnimationTo(
                    lottie3,
                    false
                ) else doAnimationTo(lottie3, true)
            }
            lottie4?.setOnClickListener {
                if (lottie4.progress > 0f && lottie4.progress <= 0.5f) doAnimationTo(
                    lottie4,
                    false
                ) else doAnimationTo(lottie4, true)
            }
            lottie5?.setOnClickListener {
                if (lottie5.progress > 0f && lottie5.progress <= 0.5f) doAnimationTo(
                    lottie5,
                    false
                ) else doAnimationTo(lottie5, true)
            }

            /* Применение */
            buttonPrimenit?.setOnClickListener {
                val music = if (lottie1?.progress!! > 0f && lottie1.progress <= 0.5f) 1 else 0
                val sound = if (lottie2?.progress!! > 0f && lottie2.progress <= 0.5f) 1 else 0
                val cheat = if (lottie3?.progress!! > 0f && lottie3.progress <= 0.5f) 1 else 0
                val coins =
                    Integer.parseInt(requireActivity().findViewById<TextView>(R.id.tvMoney).text.toString())
                val helper = DbHelper(context)
                helper.updateHelper(0, music, sound, cheat, coins)
                if (music == 0) (activity as MainActivity).mMediaPlayer?.pause()
                if (music == 1) (activity as MainActivity).startMusic()
                this?.dismiss()
            }

        }
    }

    /* Функция анимации Lottie */
    private fun doAnimationTo(lottie: LottieAnimationView, force: Boolean) {
        if (force) {
            lottie.setMinAndMaxProgress(0.0f, 0.5f)
            lottie.playAnimation()
        }
        if (!force) {
            lottie.setMinAndMaxProgress(0.5f, 1.0f)
            lottie.playAnimation()
        }
    }

    /* Background */
    private fun currentCountry(): List<Int> {
        val here = dbHelper.getLocation()
        return when {
            here[0].isLocated -> africaBgList
            here[1].isLocated -> chinaBgList
            here[2].isLocated -> americaBgList
            else -> africaBgList
        }
    }

    /* Background */
    private fun alimeAppearance(concatinate: Int, theCoins: Int) {
        val x = Random().nextInt(5)
        var alime: ImageView? = null
        when (x) {
            0 -> alime = view?.findViewById(R.id.imageView4)
            1 -> alime = view?.findViewById(R.id.imageView5)
            2 -> alime = view?.findViewById(R.id.imageView6)
            3 -> alime = view?.findViewById(R.id.imageView7)
            4 -> alime = view?.findViewById(R.id.imageView8)
            5 -> alime = view?.findViewById(R.id.imageView9)
        }
        alime?.visibility = View.VISIBLE
        val listLaTechqwdi = listOf(
            Techniques.SlideOutUp,
            Techniques.SlideOutLeft,
            Techniques.SlideOutRight,
            Techniques.SlideOutDown
        )
        YoYo.with(listLaTechqwdi.random())
            .duration(1400)
            .playOn(alime)
        alime?.setOnClickListener {
            coin.text = (theCoins + (concatinate * 5)).toString()
            tvPlus.text = "+${concatinate * 5}"
            (activity as MainActivity).alimeSound()
            alime.visibility = View.GONE
            // TvPlus
            tvPlus.visibility = View.VISIBLE
            YoYo.with(Techniques.BounceInDown)
                .duration(550)
                .interpolate(DecelerateInterpolator())
                .playOn(tvPlus)
            YoYo.with(Techniques.FadeOut)
                .duration(750)
                .interpolate(AccelerateDecelerateInterpolator())
                .playOn(tvPlus)
        }
    }

    /* Шейкер */
    private fun shake() {
        if (store != null) {
            when {
                !store!![12].isAble -> yoyoAnim(yoyoList2)
                !store!![16].isAble -> yoyoAnim(yoyoList3)
                else -> yoyoAnim(yoyoList1)
            }
        }
    }

    /* Функция анимации монетки и Элины */
    private fun yoyoAnim(list: List<Techniques>) {
        YoYo.with(list.random())
            .duration(300)
            .playOn(lunch)

        YoYo.with(Techniques.BounceIn)
            .duration(280)
            .interpolate(DecelerateInterpolator())
            .playOn(coin)
    }

}