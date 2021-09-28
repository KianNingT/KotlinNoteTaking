package com.example.kotlinnotetaking.application

import android.app.Application
import com.example.kotlinnotetaking.repository.NoteRepository
import com.example.kotlinnotetaking.roomDatabase.NoteKotlinDatabase
import com.example.kotlinnotetaking.viewModel.NoteKotlinViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NoteApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NoteApplication))
        bind() from singleton { NoteKotlinDatabase(instance()) }
        bind() from singleton { NoteRepository(instance()) }
        bind() from provider { NoteKotlinViewModelFactory(instance())}
    }

}

