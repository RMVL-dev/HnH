package com.example.lesson1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson1.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    companion object{
        fun thirdActivityIntent (context: Context) = Intent(context, ThirdActivity::class.java)
    }

    private var students: HashMap<Long,Student>? = null
    private var binding: ActivityThirdBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.apply {
            thirdActivity = this@ThirdActivity
        }
        students = hashMapOf()
        binding?.etInputStudentsName?.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                val student =
                    binding?.etInputStudentsName?.text?.toString()?.split(" ")
                try {
                    students!![System.currentTimeMillis()] = Student(
                        id = System.currentTimeMillis(),
                        name = student?.get(0).toString(),
                        surname = student?.get(1).toString(),
                        grade = student?.get(2).toString(),
                        birthdayYear = student?.get(3).toString()
                    )
                }catch (e:IndexOutOfBoundsException){
                    Toast.makeText(this, "invalid data", Toast.LENGTH_LONG).show()
                }catch (e:NullPointerException){
                    Toast.makeText(this, "trying access to null", Toast.LENGTH_LONG).show()
                }
                binding?.etInputStudentsName?.text?.clear()
                return@setOnKeyListener true
            }
            false
        }
    }

    fun printStudents(){
        if (!students.isNullOrEmpty()) {
            binding?.tvStudentsList?.text = ""
            for (student in students!!){
                binding?.tvStudentsList
                    ?.append("id = ${student.value.id} \nname = ${student.value.name} \nsurname = ${student.value.surname} \ngrade = ${student.value.grade} \nbirthday year = ${student.value.birthdayYear} \n")
            }
        }
    }

}