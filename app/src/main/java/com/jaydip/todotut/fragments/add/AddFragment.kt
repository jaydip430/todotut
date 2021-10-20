package com.jaydip.todotut.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jaydip.todotut.R
import com.jaydip.todotut.data.models.ToDoData
import com.jaydip.todotut.data.viewmodel.ToDoViewModel
import com.jaydip.todotut.fragments.SharedViewModel


class AddFragment : Fragment() {

   private lateinit var titleEt:EditText
    private lateinit var spinner: Spinner
   private lateinit var descritonText: EditText
   private val mToDoViewModel :ToDoViewModel by viewModels()
    private val mSharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        titleEt= view.findViewById(R.id.title_et)
        spinner=view.findViewById(R.id.current_priorities_spinner)
        descritonText=view.findViewById(R.id.current_description_et)

        spinner.onItemSelectedListener=mSharedViewModel.listener


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_add){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = titleEt.text.toString()
        val mSpinner=spinner.selectedItem.toString()
        val mDescriton=descritonText.text.toString()

        val validation=mSharedViewModel.verifyDataFromUser(mTitle,mDescriton)
        if (validation){
            val newData=ToDoData(
            0,
                mTitle,
                mSharedViewModel.parsePriority(mSpinner),
                mDescriton

            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Successfully Added",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please Fill All the Details",Toast.LENGTH_SHORT).show()
        }

    }


}
