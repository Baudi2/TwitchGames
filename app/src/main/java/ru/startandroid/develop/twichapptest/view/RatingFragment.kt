package ru.startandroid.develop.twichapptest.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import ru.startandroid.develop.twichapptest.R
import ru.startandroid.develop.twichapptest.databinding.RatingFragmentBinding

class RatingFragment : DialogFragment(R.layout.rating_fragment) {
    private lateinit var binding: RatingFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RatingFragmentBinding.bind(view)

        with(binding) {
            sendButton.setOnClickListener {
                val action = RatingFragmentDirections.actionRatingFragmentToMainFragment()
                findNavController().navigate(action)
            }
        }
    }
}