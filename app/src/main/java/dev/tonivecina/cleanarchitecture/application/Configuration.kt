package dev.tonivecina.cleanarchitecture.application

import android.app.Application
import android.arch.persistence.room.Room
import dev.tonivecina.cleanarchitecture.DLog

/**
 * @author Toni Vecina on 6/25/17.
 */
class Configuration: Application() {

    companion object {
        lateinit var instance: Configuration
        lateinit var database: AppDataBase
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        synchronized(this) {
            database = Room
                    .databaseBuilder(instance, AppDataBase::class.java, "notesDB")
                    .build()
        }

        DLog.success("Application is ready!")
    }
}