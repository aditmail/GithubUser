package com.demo.task.submissiontwodicoding.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.models.FileModel
import com.demo.task.submissiontwodicoding.utils.FileHelper
import kotlinx.android.synthetic.main.fragment_note.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_new.setOnClickListener(this)
        button_open.setOnClickListener(this)
        button_save.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun saveFile() {
        when {
            edit_file.text.toString().isEmpty() -> edit_file.error = "Masukkan Text Data"
            edit_title.text.toString().isEmpty() -> edit_title.error = "Masukkan Text Title"
            else -> {
                val title = edit_title.text.toString()
                val text = edit_file.text.toString()

                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text

                context?.let {
                    FileHelper.writeToFile(fileModel, it)
                    Toast.makeText(
                        context,
                        "Data Saving: ${fileModel.filename} Data: ${fileModel.data}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showList() {
        val arrayList = ArrayList<String>()
        val path: File = requireContext().filesDir
        Collections.addAll(arrayList, *path.list() as Array<String>)
        val items = arrayList.toTypedArray<CharSequence>()

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Files")
        builder.setItems(items) { _, i ->
            loadData(context, items[i].toString())
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(context: Context?, title: String) {
        val fileModel = context?.let { FileHelper.readFromFile(it, title) }
        edit_title.setText(fileModel?.filename)
        edit_file.setText(fileModel?.data)

        Toast.makeText(
            context,
            "Loading... ${fileModel?.filename} data: ${fileModel?.data}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun newFile() {
        edit_file.setText("")
        edit_title.setText("")
        Toast.makeText(context, "Create New File", Toast.LENGTH_SHORT).show()
    }

}
