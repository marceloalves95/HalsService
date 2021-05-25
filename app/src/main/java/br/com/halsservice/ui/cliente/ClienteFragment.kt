 package br.com.halsservice.ui.cliente

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.halsservice.R
import br.com.halsservice.adapters.ClienteAdapter
import br.com.halsservice.databinding.FragmentClienteBinding
import br.com.halsservice.utils.relatorio.PDF
import br.com.halsservice.utils.relatorio.RelatorioCliente
import br.com.halsservice.viewmodel.cliente.ClienteViewModel
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import org.koin.android.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

 class ClienteFragment : Fragment(),EasyPermissions.PermissionCallbacks, PDF {

    private var _binding:FragmentClienteBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter:ClienteAdapter
    var actionMode: ActionMode? = null
    private val listarId = mutableListOf<Long>()
    private val viewModel:ClienteViewModel by viewModel()
    private val relatorioCliente = RelatorioCliente()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClienteBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_cliente_to_navigation_cadastro_cliente)
        }

        atualizarLayout()
        binding.swipeLayout.setOnRefreshListener {

            binding.swipeLayout.isRefreshing = false

            mensagemAtualizada("Atualização Concluida")

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.relatorio -> {

                gerarRelatorio()

            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun gerarRelatorio() {

        permitirArquivo()

        try {
            createPdf()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: DocumentException) {
            e.printStackTrace()
        }
    }
    @AfterPermissionGranted(123)
    fun permitirArquivo() {

        if (hasWhitePermission()) {

            createPdf()

        } else {

            EasyPermissions.requestPermissions(this@ClienteFragment, "Precisamos de permissão para esse aplicativo", 123, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }

    }
    private fun hasWhitePermission(): Boolean {

        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this@ClienteFragment)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("Permissions Granted", requestCode.toString())
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this@ClienteFragment, perms)) {

            AppSettingsDialog.Builder(this@ClienteFragment).build().show()


        }
    }

    override fun createPdf() {

        val docsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val nomeDocumento = "Relatorio de Clientes.pdf"
        val pdfFile = File(docsFolder.absolutePath, nomeDocumento)
        val output: OutputStream = FileOutputStream(pdfFile)

        if (!docsFolder.exists()) {

            docsFolder.mkdir()
            relatorioCliente.criarDocumento(output)

        }

        if (docsFolder.exists()) {

            val byteArray = byteArrayOf(docsFolder.length().toByte())

            output.write(byteArray)
            relatorioCliente.alterarDocumento(output, gerarLinhas(relatorioCliente.tabelaCliente()))
            output.close()
            previewPDF(pdfFile)

        }

    }
    override fun previewPDF(file: File) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.type = "application/pdf"

        val packageManager: PackageManager = requireContext().packageManager
        val list: List<ResolveInfo> = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.isNotEmpty()) {

            val uri: Uri = FileProvider.getUriForFile(requireContext(), "br.com.halsservice.fileprovider", file)
            intent.setDataAndType(uri, "application/pdf")
            startActivity(intent)

        } else {

            Toast.makeText(requireContext(), "Instale um aplicativo de PDF que possa gerar este documento", Toast.LENGTH_LONG).show()
        }


    }

    private fun gerarLinhas(tabela: PdfPTable):PdfPTable{


        viewModel.atualizarListaCliente()
        viewModel.listAll.observe(viewLifecycleOwner, {

            it.forEach { listarClientes->

                tabela.addCell(relatorioCliente.linhasRelatorio(listarClientes.nome))
                tabela.addCell(relatorioCliente.linhasRelatorio(listarClientes.endereco))
                tabela.addCell(relatorioCliente.linhasRelatorio(listarClientes.numero.toString()))
                tabela.addCell(relatorioCliente.linhasRelatorio(listarClientes.bairro))
                tabela.addCell(relatorioCliente.linhasRelatorio(listarClientes.telefone))
            }

        })

        return tabela


    }

    private fun setupRecyclerView(){

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        adapter.onItemLongClick= {

            enableActionMode(it)

        }

    }
    private fun atualizarLayout() {

        viewModel.atualizarListaCliente()
        viewModel.listAll.observe(viewLifecycleOwner, {

            adapter = ClienteAdapter(it,this)

            Log.d("Teste", it.toString())

            setupRecyclerView()

        })

    }

    private fun mensagemAtualizada(mensagem: String) {

        Snackbar.make(requireView(), mensagem, Snackbar.LENGTH_SHORT).show()

    }

    fun deletarClientes(){

        val list = adapter.listaClientes.filter { it.selected }

        list.forEach { cliente ->

            listarId.add(cliente.clienteId)

        }
        viewModel.deletarTodasReceitas(listarId)
        adapter.deletarClientes()
        mensagemAtualizada("Cliente deletado com sucesso")

    }

    private fun enableActionMode(position: Int) {

        if (actionMode == null) actionMode = activity?.startActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                mode?.title = "Deletar Cliente"

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.action_delete){
                    deletarClientes()
                    mode?.finish()
                    return true

                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                adapter.selectedItems.clear()
                adapter.listaClientes
                        .filter { it.selected }
                        .forEach { it.selected = false }

                adapter.notifyDataSetChanged()
                actionMode = null
            }

        })

        adapter.toggleSelection(position)
        val size = adapter.selectedItems.size()
        if (size == 0){
            actionMode?.finish()
        }else{
            actionMode?.subtitle = "$size cliente(s) selecionada(s)"
            actionMode?.invalidate()

        }



    }

}