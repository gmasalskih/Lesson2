package ru.gmasalskih.lesson2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.gmasalskih.lesson2.databinding.FragmentExercise1Binding

class Exercise1 : Fragment() {

    private lateinit var binding: FragmentExercise1Binding
    private val channel = Channel<CharSequence>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercise1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener(){
        binding.e1et.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    coroutineScope.launch {
                        channel.send(it)
                    }
                }
            }
        })
        coroutineScope.launch {
            channel.consumeEach {
                binding.e1tv.text = it
            }
        }
    }

    override fun onPause() {
        super.onPause()
        coroutineScope.cancel()
        channel.close()
    }
}
