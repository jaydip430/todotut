package com.jaydip.todotut.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaydip.todotut.R
import com.jaydip.todotut.data.models.ToDoData
import com.jaydip.todotut.data.viewmodel.ToDoViewModel
import com.jaydip.todotut.databinding.FragmentUpdateBinding
import com.jaydip.todotut.fragments.SharedViewModel


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var titleText: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var descriptionText: EditText
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoDataViewModel: ToDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding?=null
    private val binding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        _binding= FragmentUpdateBinding.inflate(inflater,container,false)
        binding.args=args
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)
        titleText = view.findViewById(R.id.current_title_et)
        prioritySpinner = view.findViewById(R.id.current_priorities_spinner)
        descriptionText = view.findViewById(R.id.current_description_et)

        titleText.setText(args.currentitem.title)
        descriptionText.setText(args.currentitem.description)
        prioritySpinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentitem.priority))
        prioritySpinner.onItemSelectedListener = mSharedViewModel.listener


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoDataViewModel.deleteData(args.currentitem)
            Toast.makeText(
                requireContext(),
                "Succesfully Deleted:'${args.currentitem.title}'!",
                Toast.LENGTH_SHORT
            ).show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentitem.title}")
        builder.setMessage("Are you sure ?")
        builder.create()
        builder.show()
    }

        private fun updateItem() {
            val title = titleText.text.toString()
            val description = descriptionText.text.toString()
            val getPriority = prioritySpinner.selectedItem.toString()

            val validation = mSharedViewModel.verifyDataFromUser(title, description)
            if (validation) {
                val updateData = ToDoData(
                    args.currentitem.id,
                    title,
                    mSharedViewModel.parsePriority(getPriority),
                    description
                )
                mToDoDataViewModel.updateData(updateData)
                Toast.makeText(requireContext(), "Succesfully Updated!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "fill all form!", Toast.LENGTH_SHORT).show()
            }

        }

    }