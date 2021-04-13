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
import br.com.halsservice.framework.viewmodel.ServicoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ServicoFragment : Fragment() {

    private lateinit var servicoViewModel: ServicoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        servicoViewModel =
                ViewModelProvider(this).get(ServicoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_servico, container, false)

        servicoViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {

            findNavController().navigate(R.id.action_navigation_servico_to_navigation_cadastro_servico)
        }

    }
}