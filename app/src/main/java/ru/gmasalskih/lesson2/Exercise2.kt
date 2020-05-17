package ru.gmasalskih.lesson2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.gmasalskih.lesson2.databinding.FragmentExercise2Binding

class Exercise2 : Fragment() {

    private lateinit var binding: FragmentExercise2Binding
    private val eventBus = Channel<String>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val secondsGenerator = ticker(5000L)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercise2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initObserve(){
        binding.btn1.setOnClickListener {
            coroutineScope.launch {
                eventBus.send("press btn1")
            }
        }
        binding.btn2.setOnClickListener {
            coroutineScope.launch {
                eventBus.send("press btn2")
            }
        }
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                secondsGenerator.consumeEach {
                    eventBus.send("Custom event")
                }
            }
        }
        coroutineScope.launch {
            eventBus.receiveAsFlow().transform {event->
                secondsGenerator.consumeEach {
                    emit("Custom event")
                }
                emit(event)
                }
            eventBus.consumeEach {
                addText(binding.tv1, it)
                addText(binding.tv2, it)
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }

    }

    private fun addText(tv: TextView, eventText: String){
        tv.text = StringBuilder(tv.text)
            .append("\n")
            .append(eventText)
    }
}
