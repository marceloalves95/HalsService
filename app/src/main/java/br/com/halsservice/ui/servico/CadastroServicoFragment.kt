package br.com.halsservice.ui.servico

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.halsservice.R
import br.com.halsservice.databinding.FragmentCadastroServicoBinding
import br.com.halsservice.domain.Servico
import br.com.halsservice.utils.validator.Validador
import br.com.halsservice.utils.validator.ValidadorPadrao
import br.com.halsservice.viewmodel.cliente.ClienteViewModel
import br.com.halsservice.viewmodel.servico.ServicoViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CadastroServicoFragment : Fragment() {

    private var _binding: FragmentCadastroServicoBinding? = null
    private val binding get() = _binding!!
    lateinit var cliente: String
    private lateinit var descricaoServico: String
    private lateinit var tipoServico: String
    private lateinit var garantia: String
    private lateinit var valorServico: String
    private lateinit var dataReparo: String
    private val clienteViewModel:ClienteViewModel by viewModel()
    private val servicoViewModel:ServicoViewModel by viewModel()
    private val validadores:MutableList<Validador> = ArrayList()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?):
            View {
        _binding = FragmentCadastroServicoBinding.inflate(inflater, container, false)

        binding.calendario.setOnClickListener {
            getDataAtual()
        }
        binding.cadastroAtualizacaoServico.setOnClickListener {
            salvar()
        }

        initListas()
        initClientes()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValidadores()
    }

    private fun initListas() {

        val adapterServico = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.list_servicos))
        val adapterGarantia = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.list_garantia))

        binding.tipoServico.setAdapter(adapterServico)
        binding.garantia.setAdapter(adapterGarantia)

    }

    private fun getDataAtual() {

        val cal = Calendar.getInstance()
        val local = Locale("pt", "BR")
        val dataListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            binding.dataReparo.setText(SimpleDateFormat("dd/MM/yyyy", local).format(cal.time))


        }

        DatePickerDialog(requireContext(), dataListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()


    }

    private fun initClientes() {

        clienteViewModel.getNomeCliente()
        clienteViewModel.nomes.observe(viewLifecycleOwner, {

            val adapterCliente = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)

            binding.cliente.setAdapter(adapterCliente)


        })

    }

    private fun initValidadores() {

        validadorMaterialComplete(binding.campoCliente)
        validadorPadrao(binding.campoDescricaoServico)
        validadorMaterialComplete(binding.campoTipoServico)
        validadorMaterialComplete(binding.campoGarantia)
        validadorPadrao(binding.campoValorServico)
        validadorPadrao(binding.campoDataReparo)

    }

    private fun initCampos(){

        cliente = binding.cliente.text.toString().trim()
        descricaoServico = binding.servico.text.toString().trim()
        tipoServico = binding.tipoServico.text.toString().trim()
        valorServico = binding.valorServico.text.toString().trim()
        garantia = binding.garantia.text.toString().trim()
        dataReparo = binding.dataReparo.text.toString().trim()

    }

    private fun salvar() {


        val estaValido = validarTodosCampos()

        if (estaValido){

            initCampos()

            clienteViewModel.getIdCliente(cliente)

            clienteViewModel.ids.observe(viewLifecycleOwner, { fk_cliente->

                val servico = Servico(0, descricaoServico, tipoServico, valorServico.toDouble(), garantia, dataReparo,fk_cliente,false)
                servicoViewModel.adicionarServico(servico)
                cadastroRealizado()

            })

        }

    }

    private fun cadastroRealizado() {
        Snackbar.make(requireView(), "Serviço cadastrado com sucesso", Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_navigation_cadastro_servico_to_navigation_servico)
    }

    private fun validarTodosCampos(): Boolean {
        var estaValido = true
        validadores.forEach { validador -> if (!validador.estaValido()) estaValido = false }
        return estaValido
    }

    private fun validadorPadrao(textInputLayout: TextInputLayout){

        val campo = textInputLayout.editText

        val validado = ValidadorPadrao(textInputLayout, campo as TextInputEditText)
        validadores.add(validado)

        campo.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus){
                if (!validado.estaValido()){
                    return@setOnFocusChangeListener
                } else{
                    validado.removeError(textInputLayout)
                }

            }

        }

    }
    private fun validadorMaterialComplete(textInputLayout: TextInputLayout){

        val campo = textInputLayout.editText

        val validado = ValidadorPadrao(textInputLayout, campo as MaterialAutoCompleteTextView)
        validadores.add(validado)
        campo.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus){

                if (!validado.estaValido()){
                    return@setOnFocusChangeListener
                } else{
                    validado.removeError(textInputLayout)
                }

            }

        }
    }

}