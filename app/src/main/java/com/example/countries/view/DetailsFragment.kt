package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.countries.databinding.FragmentDetailsBinding
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeHolderProgressBar
import com.example.countries.viewmodel.DetailsViewModel
import java.util.UUID

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var countryUuid = 0

    private lateinit var  viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = DetailsFragmentArgs.fromBundle(it).countryUUID
        }

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.getDataFromDetailsVM(countryUuid)

        observeLiveData()
    }


    private fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.country.text = it.countryName
                binding.city.text = it.countryCapital
                binding.language.text = it.countryLanguage
                binding.currency.text = it.countryCurrency
                binding.region.text = it.countryRegion

                context?.let {context ->
                    binding.imageView2.downloadFromUrl(it.countryImageUrl, placeHolderProgressBar(context))
                }

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}