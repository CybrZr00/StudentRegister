package uk.co.digitaljeeves.studentregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.co.digitaljeeves.studentregister.db.StudentDao

class StudentViewModelFactory(
    private val dao: StudentDao
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)){
            return StudentViewModel(dao) as T
        }
        throw java.lang.IllegalArgumentException("View model class is incorrect")
    }
}