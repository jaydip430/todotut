package com.jaydip.todotut.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jaydip.todotut.R
import com.jaydip.todotut.data.viewmodel.ToDoViewModel
import com.jaydip.todotut.databinding.FragmentListBinding
import com.jaydip.todotut.fragments.SharedViewModel


class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var _binding:FragmentListBinding? =null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.mSharedViewModel=mSharedViewModel



       //Setup RecyclerView
        setupRecyclerView()



        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
            mSharedViewModel.checkIfDatabaseEmpty(data)
        })



        //set menu
        setHasOptionsMenu(true)



        return binding.root
    }

    private fun setupRecyclerView() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> confirmRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Succesfully Deleted All Notes!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All")
        builder.setMessage("Are you sure ?")
        builder.create()
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
