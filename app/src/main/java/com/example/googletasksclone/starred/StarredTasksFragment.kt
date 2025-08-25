package com.example.googletasksclone.starred

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googletasksclone.R



class StarredTasksFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starred_tasks, container, false)
    }
}
/*
* val categoryDao = db.categoryDao()
val taskDao = db.taskDao()

// Obtener la categoría favorita actual
val favoriteCategory = categoryDao.getFavoriteCategory()

// Si ya hay una categoría favorita, desmarcarla
if (favoriteCategory != null) {
    categoryDao.resetFavoriteCategory()
}

// Establecer una nueva categoría como favorita
categoryDao.setFavoriteCategory(newCategoryId)

// Agregar tareas a la nueva categoría favorita
val task = Task(title = "Nueva tarea", description = "Descripción de tarea", categoryId = newCategoryId)
taskDao.insertTask(task)*/