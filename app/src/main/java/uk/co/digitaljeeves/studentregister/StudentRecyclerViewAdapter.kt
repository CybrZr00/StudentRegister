package uk.co.digitaljeeves.studentregister

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.co.digitaljeeves.studentregister.databinding.ListItemBinding
import uk.co.digitaljeeves.studentregister.db.Student

class StudentRecyclerViewAdapter(private val clickListener:(Student)->Unit) : RecyclerView.Adapter<StudentViewHolder>() {
    private val studentList = ArrayList<Student>()
    private lateinit var binding:ListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {


        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemBinding.inflate(layoutInflater, parent, false)

        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], clickListener)
    }
    fun setList(students: List<Student>){
        studentList.clear()
        studentList.addAll(students)
    }
}

class StudentViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(student: Student, clickListener:(Student)->Unit){
        binding.apply {
            val nameTextView = tvName
            val emailTextView = tvEmail
            nameTextView.text = student.name
            emailTextView.text = student.email
            root.setOnClickListener{
                clickListener(student)
            }
        }

    }
}