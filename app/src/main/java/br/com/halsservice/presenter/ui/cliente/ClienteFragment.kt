package br.com.halsservice.presenter.ui.cliente

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.halsservice.R
import br.com.halsservice.databinding.FragmentClienteBinding
import br.com.halsservice.framework.viewmodel.cliente.ClienteViewModel
import br.com.halsservice.framework.viewmodel.cliente.ClienteViewModelFactory
import br.com.halsservice.presenter.adapters.ClienteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ClienteFragment : Fragment() {

    private var _binding:FragmentClienteBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter:ClienteAdapter
    lateinit var application: Application

    val viewModel by lazy {

        ViewModelProvider(this, ClienteViewModelFactory(application)).get(ClienteViewModel::class.java)

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        application = requireActivity().application!!
        _binding = FragmentClienteBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_cliente_to_navigation_cadastro_cliente)
        }

        atualizarLayout()


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView(){

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

    }
    fun atualizarLayout() {

        viewModel.atualizarListaCliente()
        viewModel.listAll.observe(viewLifecycleOwner, {

            adapter = ClienteAdapter(it)

            setupRecyclerView()

        })

    }




}