package com.example.lesson1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson1.databinding.ActivitySecondBinding
import java.util.TreeSet

class SecondActivity : AppCompatActivity() {

    companion object{
        fun secondActivityIntent(context: Context) = Intent(context, SecondActivity::class.java)
    }

    private var students:TreeSet<String>? = null
    private var binding:ActivitySecondBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.apply {
            secondActivity = this@SecondActivity
        }

        students = TreeSet()
    }

    override fun onDestroy() {
        super.onDestroy()
        students = null
        binding = null
    }

    /** printing all students */
    fun printStudentList(){
        if (!students.isNullOrEmpty()) {
            binding?.tvStudentsList?.text = ""
            for (student in students!!){
                binding?.tvStudentsList?.append("$student \n")
            }
        }
    }

    /** save student method */
    fun saveStudent(){
        if (binding?.etInputStudentsName.toString().isNotEmpty()){
            students?.add(binding?.etInputStudentsName?.text.toString())
            binding?.etInputStudentsName?.text?.clear()
        }
    }
}