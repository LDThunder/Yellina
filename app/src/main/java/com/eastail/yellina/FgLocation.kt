package com.eastail.yellina

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.eastail.yellina.Database.DbHelper
import com.eastail.yellina.databinding.FragmentFgLocationBinding
import com.google.android.material.button.MaterialButton
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import pl.droidsonroids.gif.GifImageView
import java.util.concurrent.TimeUnit


class FgLocation : Fragment(R.layout.fragment_fg_location) {
    /* Lateinits */
    private lateinit var bind: FragmentFgLocationBinding
    private lateinit var recViewLocation: RecyclerView
    private lateinit var winVisaSound: MediaPlayer
    private lateinit var bgVisaSoundChina: MediaPlayer
    private lateinit var bgVisaSoundUsa: MediaPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentFgLocationBinding.bind(view)
        recViewLocation = view.findViewById(R.id.recViewLocation)
        /* Init */
        initAdapter()
        requireActivity().findViewById<GifImageView>(R.id.shrekGif).visibility = View.INVISIBLE

    }

    /* Initializing */
    private fun initAdapter() {
        /* Database */
        val dbHelper = DbHelper(context)
        val locList = dbHelper.getLocation()
        val storeList = dbHelper.getStore()
        val adapter = LocationRecAdapter(locList)

        /* Soundbar */
        fun playBgChina() {
            bgVisaSoundChina = MediaPlayer.create(context, R.raw.china_superidolhardstyle)
            bgVisaSoundChina.isLooping = true
            bgVisaSoundChina.start()
        }
        fun playBgUsa() {
            bgVisaSoundUsa = MediaPlayer.create(context, R.raw.usa_newyork)
            bgVisaSoundUsa.isLooping = true
            bgVisaSoundUsa.start()
        }
        fun playWin() {
            winVisaSound = MediaPlayer.create(context, R.raw.test_china_win)
            winVisaSound.isLooping = false
            winVisaSound.start()
            winVisaSound.setOnCompletionListener {
                winVisaSound.release()
            }
        }

        /* Relocation */
        fun relocateTo(country: Int) {
            when (country) {
                0 -> {
                    requireActivity().findViewById<LottieAnimationView>(R.id.lottieDollar).apply {
                        this.setAnimation(R.raw.dollar_coin)
                        this.loop(true)
                        this.speed = 0.5f
                        this.playAnimation()
                    }
                    dbHelper.updateLocation(0, 1, 1)
                    dbHelper.updateLocation(1, 0, 322)
                    dbHelper.updateLocation(2, 0, 322)
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_fgLocation_to_fgPlay)
                    if (dbHelper.getHelper()[0].music) (activity as MainActivity).playNextSound()
                }
                1 -> {
                    requireActivity().findViewById<LottieAnimationView>(R.id.lottieDollar).apply {
                        this.setAnimation(R.raw.yen_coin)
                        this.loop(true)
                        this.speed = 0.5f
                        this.playAnimation()
                    }
                    dbHelper.updateLocation(0, 0, 1)
                    dbHelper.updateLocation(1, 1, 322)
                    dbHelper.updateLocation(2, 0, 322)
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_fgLocation_to_fgPlay)
                    if (dbHelper.getHelper()[0].music) (activity as MainActivity).playNextSound()
                }
                2 -> {
                    requireActivity().findViewById<LottieAnimationView>(R.id.lottieDollar).apply {
                        this.setAnimation(R.raw.dollar_coin)
                        this.loop(true)
                        this.speed = 0.5f
                        this.playAnimation()
                    }
                    dbHelper.updateLocation(0, 0, 1)
                    dbHelper.updateLocation(1, 0, 322)
                    dbHelper.updateLocation(2, 1, 322)
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_fgLocation_to_fgPlay)
                    if (dbHelper.getHelper()[0].music) (activity as MainActivity).playNextSound()
                }
            }
        }

        /* Dialog China */
        fun initTestDialogChina() {
            var position = 0
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_china, null)
            val dialog = context?.let { x -> Dialog(x) }
            var variant1 = false
            var variant2 = false
            var variant3 = false
            var variant4 = false

            with(dialog) {
                this?.setContentView(dialogBinding)
                this?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                this?.setCancelable(false)
                this?.show()
                playBgChina()

                /* Find */
                val otvetit = this?.findViewById<MaterialButton>(R.id.otvetitChina)
                val vnimanie = this?.findViewById<TextView>(R.id.tvVoprosChina)
                val question = this?.findViewById<TextView>(R.id.voprosChina)
                val chinaCV = this?.findViewById<CardView>(R.id.superChinaCV)
                val var1 = this?.findViewById<MaterialButton>(R.id.variant1China)
                val var2 = this?.findViewById<MaterialButton>(R.id.variant2China)
                val var3 = this?.findViewById<MaterialButton>(R.id.variant3China)
                val var4 = this?.findViewById<MaterialButton>(R.id.variant4China)

                /* Extension */
                fun setColorVariant(variant: Int) {
                    when (variant) {
                        1 -> {
                            var1?.setStrokeColorResource(R.color.yellow)
                            var1?.setTextColor(Color.parseColor("#FFFB00"))
                            listOf(var2, var3, var4).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                        2 -> {
                            var2?.setStrokeColorResource(R.color.yellow)
                            var2?.setTextColor(Color.parseColor("#FFFB00"))
                            listOf(var1, var3, var4).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                        3 -> {
                            var3?.setStrokeColorResource(R.color.yellow)
                            var3?.setTextColor(Color.parseColor("#FFFB00"))
                            listOf(var1, var2, var4).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                        4 -> {
                            var4?.setStrokeColorResource(R.color.yellow)
                            var4?.setTextColor(Color.parseColor("#FFFB00"))
                            listOf(var1, var2, var3).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                    }
                }

                /* Set Listeners */
                var1?.setOnClickListener {
                    variant1 = true
                    variant2 = false
                    variant3 = false
                    variant4 = false
                    setColorVariant(1)
                }
                var2?.setOnClickListener {
                    variant1 = false
                    variant2 = true
                    variant3 = false
                    variant4 = false
                    setColorVariant(2)
                }
                var3?.setOnClickListener {
                    variant1 = false
                    variant2 = false
                    variant3 = true
                    variant4 = false
                    setColorVariant(3)
                }
                var4?.setOnClickListener {
                    variant1 = false
                    variant2 = false
                    variant3 = false
                    variant4 = true
                    setColorVariant(4)
                }
                otvetit?.setOnClickListener {
                    when (position) {
                        0 -> {
                            /* Верхушка */
                            chinaCV?.visibility = View.VISIBLE
                            /* Вопрос */
                            vnimanie?.text = "Вопрос"
                            question?.text = "Тайвань - это..."
                            otvetit.text = "Подтвердить"
                            /* Варианты */
                            var1?.text = "Страна"; var2?.text = "Китай"
                            var3?.text = "США"; var4?.text = "Япония"
                            /* Видимость */
                            var1?.visibility = View.VISIBLE; var2?.visibility = View.VISIBLE
                            var3?.visibility = View.VISIBLE; var4?.visibility = View.VISIBLE
                            position = 1
                        }
                        1 -> {
                            /* Проверка */
                            if (!variant1 && !variant2 && !variant3 && !variant4) {
                                Toast.makeText(
                                    context,
                                    "Выберите хотя бы один вариант!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                if (variant2) {
                                    playWin()
                                    /* Вопрос */
                                    question?.text =
                                        "Что случилось на площади Тяньаньмэнь в 1989 году?"
                                    /* Варианты */
                                    var1?.text = "Ничего"; var2?.text = "天安場"
                                    var3?.text = "中國共產"; var4?.text = "Что-то"
                                    position = 2
                                } else {
                                    (activity as MainActivity).playLose()
                                    requireActivity().findViewById<View>(R.id.viewChina).visibility =
                                        View.VISIBLE
                                    bgVisaSoundChina.release()
                                    this?.dismiss()
                                }
                            }

                        }
                        2 -> {
                            if (variant1) {
                                playWin()
                                /* Вопрос */
                                question?.text = "中国共产党第八次代表大会是在哪一年、哪一天召开的？"
                                /* Варианты */
                                var1?.text = "1956年9月"; var2?.text = "1952年4月"
                                var3?.text = "1992年8月"; var4?.text = "1968年4月"
                                position = 3
                            } else {
                                (activity as MainActivity).playLose()
                                requireActivity().findViewById<View>(R.id.viewChina).visibility =
                                    View.VISIBLE
                                bgVisaSoundChina.release()
                                this?.dismiss()
                            }
                        }
                        3 -> {
                            if (variant2) {
                                playWin()
                                requireActivity().findViewById<KonfettiView>(R.id.konfetti).start(
                                    listOf(
                                        Party(
                                            emitter = Emitter(
                                                duration = 100,
                                                TimeUnit.MILLISECONDS
                                            ).max(100), position = Position.Relative(0.5, 0.3)
                                        )
                                    )
                                )
                                /* Поздравления */
                                vnimanie?.text = "Поздравляем!"
                                question?.text =
                                    "Вы полчуили визу! Нажмите на кнопку, чтобы посетить Китай!"
                                otvetit.text = "Перейти"
                                /* Варианты */
                                var1?.visibility = View.GONE; var2?.visibility = View.GONE
                                var3?.visibility = View.GONE; var4?.visibility = View.GONE
                                position = 4
                            } else {
                                (activity as MainActivity).playLose()
                                requireActivity().findViewById<View>(R.id.viewChina).visibility =
                                    View.VISIBLE
                                bgVisaSoundChina.release()
                                this?.dismiss()
                            }
                        }
                        4 -> {
                            this?.dismiss()
                            bgVisaSoundChina.release()
                            dbHelper.updateLocation(1, 1, 1)
                            relocateTo(1)
                        }
                    }
                }
            }
        }

        /* Dialog USA */
        fun initTestDialogUsa() {
            var position = 0
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_usa, null)
            val dialog = context?.let { x -> Dialog(x) }
            var variant1 = false
            var variant2 = false
            var variant3 = false
            var variant4 = false
            requireActivity().findViewById<View>(R.id.viewChina).visibility = View.GONE

            with(dialog) {
                this?.setContentView(dialogBinding)
                this?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                this?.setCancelable(false)
                this?.show()
                playBgUsa()

                /* Find */
                val otvetit = this?.findViewById<MaterialButton>(R.id.otvetitUsa)
                val vnimanie = this?.findViewById<TextView>(R.id.tvVoprosUsa)
                val question = this?.findViewById<TextView>(R.id.voprosUsa)
                val usaCV = this?.findViewById<CardView>(R.id.superUsaCV)
                val var1 = this?.findViewById<MaterialButton>(R.id.variant1Usa)
                val var2 = this?.findViewById<MaterialButton>(R.id.variant2Usa)
                val var3 = this?.findViewById<MaterialButton>(R.id.variant3Usa)
                val var4 = this?.findViewById<MaterialButton>(R.id.variant4Usa)

                /* Extension */
                fun setColorVariant(variant: Int) {
                    when (variant) {
                        1 -> {
                            var1?.setStrokeColorResource(R.color.red)
                            var1?.setTextColor(Color.parseColor("#D63737"))
                            listOf(var2, var3, var4).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                        2 -> {
                            var2?.setStrokeColorResource(R.color.red)
                            var2?.setTextColor(Color.parseColor("#D63737"))
                            listOf(var1, var3, var4).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                        3 -> {
                            var3?.setStrokeColorResource(R.color.red)
                            var3?.setTextColor(Color.parseColor("#D63737"))
                            listOf(var1, var2, var4).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                        4 -> {
                            var4?.setStrokeColorResource(R.color.red)
                            var4?.setTextColor(Color.parseColor("#D63737"))
                            listOf(var1, var2, var3).forEach {
                                it?.setStrokeColorResource(R.color.white)
                                it?.setTextColor(Color.parseColor("#F9FFF6"))
                            }
                        }
                    }
                }

                /* Set Listeners */
                var1?.setOnClickListener {
                    variant1 = true
                    variant2 = false
                    variant3 = false
                    variant4 = false
                    setColorVariant(1)
                }
                var2?.setOnClickListener {
                    variant1 = false
                    variant2 = true
                    variant3 = false
                    variant4 = false
                    setColorVariant(2)
                }
                var3?.setOnClickListener {
                    variant1 = false
                    variant2 = false
                    variant3 = true
                    variant4 = false
                    setColorVariant(3)
                }
                var4?.setOnClickListener {
                    variant1 = false
                    variant2 = false
                    variant3 = false
                    variant4 = true
                    setColorVariant(4)
                }
                otvetit?.setOnClickListener {
                    when (position) {
                        0 -> {
                            /* Верхушка */
                            usaCV?.visibility = View.VISIBLE
                            /* Вопрос */
                            vnimanie?.text = "Вопрос"
                            question?.text = "Страной с первой экономикой в мире является..."
                            otvetit.text = "Подтвердить"
                            /* Варианты */
                            var1?.text = "Россия"; var2?.text = "Китай"
                            var3?.text = "США"; var4?.text = "Индия"
                            /* Видимость */
                            var1?.visibility = View.VISIBLE; var2?.visibility = View.VISIBLE
                            var3?.visibility = View.VISIBLE; var4?.visibility = View.VISIBLE
                            position = 1
                        }
                        1 -> {
                            /* Проверка */
                            if (!variant1 && !variant2 && !variant3 && !variant4) {
                                Toast.makeText(
                                    context,
                                    "Выберите хотя бы один вариант!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                if (variant3) {
                                    if ((activity as MainActivity).mMediaPlayer!!.isPlaying) (activity as MainActivity).mMediaPlayer!!.stop()
                                    playWin()
                                    /* Вопрос */
                                    question?.text = "Что случилось с Джорджом Флойдом?"
                                    /* Варианты */
                                    var1?.text = "Умер"; var2?.text = "Упау"
                                    var3?.text = "Смертъ"; var4?.text = "РАСИЗМ"
                                    position = 2
                                } else {
                                    bgVisaSoundUsa.release()
                                    Toast.makeText(
                                        context,
                                        "Вам отказано в получении визы, попробуйте позже",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    this?.dismiss()
                                }
                            }

                        }
                        2 -> {
                            if (variant4) {
                                if ((activity as MainActivity).mMediaPlayer!!.isPlaying) (activity as MainActivity).mMediaPlayer!!.stop()
                                playWin()
                                /* Вопрос */
                                question?.text = "Кто победил в холодной войне?"
                                /* Варианты */
                                var1?.text = "Китай"; var2?.text = "США"
                                var3?.text = "СССР"; var4?.text = "Индия"
                                position = 3
                            } else {
                                if (dbHelper.getHelper()[0].music) (activity as MainActivity).playNextSound()
                                bgVisaSoundUsa.release()
                                Toast.makeText(
                                    context,
                                    "Вам отказано в получении визы, попробуйте позже",
                                    Toast.LENGTH_SHORT
                                ).show()
                                this?.dismiss()
                            }
                        }
                        3 -> {
                            if (variant2) {
                                if ((activity as MainActivity).mMediaPlayer!!.isPlaying) (activity as MainActivity).mMediaPlayer!!.stop()
                                playWin()
                                requireActivity().findViewById<KonfettiView>(R.id.konfetti).start(
                                    listOf(
                                        Party(
                                            emitter = Emitter(
                                                duration = 100,
                                                TimeUnit.MILLISECONDS
                                            ).max(100), position = Position.Relative(0.5, 0.3)
                                        )
                                    )
                                )
                                /* Поздравления */
                                vnimanie?.text = "Поздравляем!"
                                question?.text =
                                    "Вы полчуили визу! Нажмите на кнопку, чтобы посетить США!"
                                otvetit.text = "Перейти"
                                /* Варианты */
                                var1?.visibility = View.GONE; var2?.visibility = View.GONE
                                var3?.visibility = View.GONE; var4?.visibility = View.GONE
                                position = 4
                            } else {
                                if (dbHelper.getHelper()[0].music) (activity as MainActivity).playNextSound()
                                bgVisaSoundUsa.release()
                                Toast.makeText(
                                    context,
                                    "Вам отказано в получении визы, попробуйте позже",
                                    Toast.LENGTH_SHORT
                                ).show()
                                this?.dismiss()
                            }
                        }
                        4 -> {
                            if ((activity as MainActivity).mMediaPlayer!!.isPlaying) (activity as MainActivity).mMediaPlayer!!.stop()
                            bgVisaSoundUsa.release()
                            this?.dismiss()
                            dbHelper.updateLocation(2, 1, 1)
                            relocateTo(2)
                            if (dbHelper.getHelper()[0].music) (activity as MainActivity).playNextSound()
                        }
                    }
                }
            }

        }


        /* Listener */
        adapter.setListener(object : OnLocationClickListener {
            override fun onLocationClick(isLocated: Boolean, isTested: Boolean, position: Int) {
                if (isTested && !isLocated && !storeList[11].isAble) {
                    if (position == 0) {
                        (activity as MainActivity).soundBuy()
                        relocateTo(0)
                    }
                    if (position == 1) {
                        (activity as MainActivity).soundBuy()
                        relocateTo(1)
                    }
                    if (position == 2) {
                        (activity as MainActivity).soundBuy()
                        relocateTo(2)
                    }
                } else if (isLocated) {
                    Toast.makeText(context, "Вы уже тут!", Toast.LENGTH_SHORT).show()
                } else if (storeList[11].isAble) {
                    Toast.makeText(context, "Чтобы путешествовать, купите в магазине 'Невидимку'", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (dbHelper.getHelper()[0].music) (activity as MainActivity).mMediaPlayer?.pause()
                    if (position == 1) {
                        (activity as MainActivity).soundBuy()
                        initTestDialogChina()
                    }
                    if (position == 2) {
                        (activity as MainActivity).soundBuy()
                        initTestDialogUsa()
                    }
                }
            }
        })

        /* RecView */
        recViewLocation.adapter = adapter
        recViewLocation.layoutManager = LinearLayoutManager(activity)
    }

}
