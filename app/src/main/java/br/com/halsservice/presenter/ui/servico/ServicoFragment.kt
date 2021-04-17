package br.com.halsservice.presenter.ui.servico

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.halsservice.R
import br.com.halsservice.databinding.FragmentClienteBinding
import br.com.halsservice.databinding.FragmentServicoBinding
import br.com.halsservice.framework.viewmodel.servico.ServicoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ServicoFragment : Fragment() {

    private var _binding: FragmentServicoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentServicoBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {

            findNavController().navigate(R.id.action_navigation_servico_to_navigation_cadastro_servico)

        }


    }

}