package com.example.mynotes.presentation.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.db.Note
import com.example.mynotes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    private val _allNotes = MutableStateFlow<List<Note>?>(emptyList())
    val allNotes: StateFlow<List<Note>?> get() = _allNotes

    init {
        getAllNotes()
    }

    fun getAllNotes() {
        viewModelScope.launch {
            _allNotes.value = noteRepository.getAll()
        }
    }

    fun insertNotes(note: Note) {
        viewModelScope.launch {
            noteRepository.insert(note)
            getAllNotes() // Ensure data is up-to-date
        }
    }

    fun updateNotes(note: Note) {
        viewModelScope.launch {
            noteRepository.update(note)
            getAllNotes() // Ensure data is up-to-date
        }
    }

    fun deleteNotes(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note)
            getAllNotes() // Ensure data is up-to-date
        }
    }

    fun getByIdNotes(id: Int) {
        viewModelScope.launch {
            noteRepository.getById(id)?.let { note ->
                _allNotes.value = _allNotes.value?.map {
                    if (it.id == id) note else it
                }
            }
        }
    }
}
