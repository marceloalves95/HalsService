package br.com.halsservice.presenter.ui.servico

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.halsservice.databinding.FragmentCadastroServicoBinding


class CadastroServicoFragment : Fragment() {

    private var _binding: FragmentCadastroServicoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?):
            View {

        _binding = FragmentCadastroServicoBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}