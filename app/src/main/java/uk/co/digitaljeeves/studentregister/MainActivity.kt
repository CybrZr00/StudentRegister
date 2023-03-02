package uk.co.digitaljeeves.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.digitaljeeves.studentregister.databinding.ActivityMainBinding
import uk.co.digitaljeeves.studentregister.db.Student
import uk.co.digitaljeeves.studentregister.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentRecyclerViewAdapter
    private lateinit var studentRecyclerView: RecyclerView
    private var isListItemSelected = false

    private lateinit var selectedStudent: Student
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {
            nameEditText = etName
            emailEditText = etEmail
            saveButton = btnSave
            clearButton = btnClear
            studentRecyclerView = rvStudents
        }


        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        saveButton.setOnClickListener {
            if (isListItemSelected){
                updateStudentData()
            }else{
                saveStudentData()
            }
            clearInputs()
        }
        clearButton.setOnClickListener {
            if (isListItemSelected){
                deleteStudent()
                clearInputs()
            }else{
                clearInputs()
            }

        }
        initRecyclerView()
    }
    private fun saveStudentData(){
        //val name = nameEditText.text.toString()
        //val email = emailEditText.text.toString()

        //val student = Student(0, name, email)
        //viewModel.insertStudent(student)
        viewModel.insertStudent(
            Student(
                0,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
    }
    private fun updateStudentData(){
        viewModel.updateStudent(Student(
            selectedStudent.id,
            nameEditText.text.toString(),
            emailEditText.text.toString()
        ))
        saveButton.text = getString(R.string.save)
        clearButton.text = getString(R.string.clear)
        isListItemSelected = false
    }
    private fun deleteStudent(){
        viewModel.deleteStudent(selectedStudent)
        saveButton.text = getString(R.string.save)
        clearButton.text = getString(R.string.clear)
        isListItemSelected = false
    }
    private fun clearInputs(){
        nameEditText.text.clear()
        emailEditText.text.clear()
    }
    private fun initRecyclerView(){
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter{selectedItem:Student->
            listItemClicked(selectedItem)
        }
        studentRecyclerView.adapter = adapter
        displayStudentsList()
    }

    private fun displayStudentsList(){
        viewModel.students.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }
    private fun listItemClicked(student: Student){
        //Toast.makeText(this, "Student name is ${student.name}", Toast.LENGTH_LONG).show()
        if (!isListItemSelected){
            selectedStudent = student
            saveButton.text = getString(R.string.update)
            clearButton.text = getString(R.string.delete)
            isListItemSelected = true

            nameEditText.setText(student.name)
            emailEditText.setText(student.email)

        }
        else if(isListItemSelected && selectedStudent.id != student.id){
            selectedStudent = student
            saveButton.text = getString(R.string.update)
            clearButton.text = getString(R.string.delete)
            isListItemSelected = true

            nameEditText.setText(student.name)
            emailEditText.setText(student.email)
        }
        else{
            saveButton.text = getString(R.string.save)
            clearButton.text = getString(R.string.clear)
            isListItemSelected = false
            clearInputs()
        }

    }
}