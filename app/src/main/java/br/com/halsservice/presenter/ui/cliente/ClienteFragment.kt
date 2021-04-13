package br.com.halsservice.presenter.ui.cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.halsservice.R
import br.com.halsservice.framework.viewmodel.ClienteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ClienteFragment : Fragment() {

    private lateinit var clienteViewModel: ClienteViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        clienteViewModel =
                ViewModelProvider(this).get(ClienteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cliente, container, false)
        clienteViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {

           findNavController().navigate(R.id.action_navigation_cliente_to_navigation_cadastro_cliente)
        }

    }
}