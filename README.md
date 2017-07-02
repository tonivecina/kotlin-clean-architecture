# Android clean architecture

This guide will show how to build a Android project with a clean architecture with Kotlin. It's very important we have a project with packages and subpackages ordered by services, implementations and android functionalities.

## Table of contents

* [Packages](#packages)
	* [Activities](#activities-package)
	* [Application](#application-package)
	* [Entities](#entities-package)
	* [Patterns](#patterns-package)
	* [Services](#services-package)
	* [Views](#views)

* [Activities and fragments](#activities-and-fragments)
	* [View](#view)
	* [Processor](#processor)
	* [Listeners](#listener)
	* [Routes](#routes)
	* [Interactor](#interactor)

* [Entities](#entities)
	* [Database](#database-entity)
	* [Dynamic](#dynamic-entity)
	* [Local](#local-entity)
	
* [Patterns](#patterns)

* [Services](#services)

* [Views](#views)

## Packages

The packages and subpackages must be ordered alphabetically for a fast localization (Automatic function of Android Studio). The valid packages will show hereunder.

### Activities package

Activities package must contains every activity of project divided in sub-packages. Each package will have Activity class and other class with services and implementations. If the activity has fragments, this fragments will be contained in sub-packages inside of this package.

Each interface used by Activity or Fragment must be separated by class (as service) and file.

This packages must be named with Activity name or Fragment name without key '*Activity*' or '*Fragment*'. For example, package of **MainActivity** will be *main* or  **LoginFragment** will be *login*.

<!-- ![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_activities.png) -->


### Application package

If you wish manage application class of your project, Configuration file is your file and it will be contained in this package. If your project contains a local database, create the clase here, for example: AppDataBase.

<!-- ![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_application.png) -->

### Entities package

All entities must be contained here and must be categorized like Database, Dynamic or Local.

* Dynamic for generic entities, for example entities for an Api.
* Database entities.
* Local for entities with memory storage, for example: Credentials.

<!-- ![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_entities.png) -->

### Patterns package

The global patterns that it can be used at any class are contained here like Boolean, String, etc. File of class must be object name with Pattern; StringPattern, BooleanPattern...

<!-- ![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_patterns.png) -->

### Services package

The services package must contains subpackages and the class files to execute services like Api connections, Location...

Please, you must not implement services like Location, Bus, Tracking... in Activities. The best is create a global service with manage control, the Activities will use this services.

<!-- <img src="https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_services.png" width="400"> -->

More info of class structures in [classes](#classes) section.

### Views package

The views classes must be ordered in subpackages and the name file ends with view type. For example, a editText for forms would can called **FormEditText** inside of *EditTexts* package.

<!-- <img src="https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_package_views.png" width="400"> -->

This is a simple full structure of project ordered in packages, subpackages and files:

<!-- ![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_project_packages.png) -->

## Activities and fragments

The Activity and Fragment cycles are segmented with different elements:

### View

View element is the Activity or Fragment class. All UI layer is contained here. It's important that this class doesn't implement interfaces because this package will have Listener elements to do it.

In this class the Process element will be initialized to manage all elements in this pacakge except the view. In addition, It only must contain Getters and Setters for UI.

This is a simple Activity example:

```Android
class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var addButton: Button? = null

    private val processor = MainProcessor(this)
    private val noteAdapter = MainNoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.activity_main_recyclerView) as RecyclerView
        recyclerView?.adapter = noteAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.itemAnimator = DefaultItemAnimator()

        val onClickListener = processor.onClickListener

        addButton = findViewById(R.id.activity_main_button_add) as Button
        addButton?.setOnClickListener(onClickListener)

        processor.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            NewNoteActivity.REQUEST_CODE -> {
                processor.onNewNoteActivityResult(resultCode, data)
            }
        }
    }

    fun addNote(note: Note) {
        runOnUiThread {
            noteAdapter.notes.add(0, note)
            noteAdapter.notifyDataSetChanged()
        }
    }

    fun setNotes(notes: List<Note>) {
        runOnUiThread {
            noteAdapter.notes.clear()
            noteAdapter.notes.addAll(notes)
            noteAdapter.notifyDataSetChanged()
        }
    }
}
```

### Processor

The Process class will manage all elements in this package. This class is the communication channel with the view. Furthermore all elements in this package except the view will be initialized here.

This is a Process example with Listener and Interactor:

```Android
class MainProcessor(val view: MainActivity): MainListeners.ActionListener {

    val routes: MainRoutes by lazy {
        MainRoutes(view)
    }

    val onClickListener: MainOnClickListener by lazy {
        MainOnClickListener(this)
    }

    val notesInteractor: MainInteractoreNotes by lazy {
        MainInteractoreNotes()
    }

    fun onCreate() {
        getNotes()
    }

    private fun getNotes() {
        notesInteractor.getAll {
            notes: List<Note> -> view.setNotes(notes)
        }
    }

    fun onNewNoteActivityResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_CANCELED -> {
                DLog.info("User cancelled the action.")
            }

            Activity.RESULT_OK -> {
                val note: Note? = data?.getSerializableExtra(NewNoteActivity.BUNDLE_NOTE) as? Note

                if (note == null) {
                    DLog.warning("Note not found")
                    return
                }

                view.addNote(note)
            }
        }
    }

    //region ActionListener
    override fun onAddNoteClick() {
        routes.intentForNoteAddActivity()
    }
    //endregion
}
```

### Listeners

All communication between processor and interactors must be bidirectional through listeners. All listeners needs by interactors will be declared in Listener (abstract class).

```Android
abstract class MainListeners {

    interface ActionListener {
        fun onAddNoteClick()
    }
}
```

### Interactors

The Interactors can communicate directly with entities to manage data or services.

```Android
class MainInteractorNotes {

    private val noteDao = Configuration.database.noteDao

    fun getAll(finished: (notes: List<Note>) -> Unit) {
        AsyncTask.execute {
            val notes = noteDao.getAll()
            Collections.reverse(notes)
            finished(notes)
        }
    }
}
```

Implementations like OnClickListener be able to be considered as special interactor.

```Android
class MainOnClickListener(val listener: MainListeners.ActionListener): View.OnClickListener {

    override fun onClick(v: android.view.View?) {
        val id = v?.id

        when (id) {
            R.id.activity_main_button_add -> listener.onAddNoteClick()
        }
    }
}
```

In view must be declared:

```Android
val onClickListener = processor.onClickListener

addButton = findViewById(R.id.activity_main_button_add) as Button
addButton?.setOnClickListener(onClickListener)
```

### Routes

All navigations and connections with other activites or fragments are defined here. You apply intents here. Look at this example:

```Android
class MainRoutes(val view: MainActivity) {

    fun intentForNoteAddActivity() {
        val intent = Intent(view, NewNoteActivity::class.java)
        view.startActivityForResult(intent, NewNoteActivity.REQUEST_CODE, null)
    }
}
```

This architecture paradigm is:

![](https://raw.githubusercontent.com/tonivecina/android-clean-architecture/master/images/screen_activity_paradigm.png)

## Entities

The entities are localized in two categories, **Local** for memory resources and **Dynamic** for API resources (for example).

### Local entity

A local entity could be Credentials. Look at this example code:

```Android

```

### Dynamic entity

This example is for simple dynamic entity:

```Android

```

### Database entity

This example with Room service:

```Android
@Entity(tableName = "notes")
class Note: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "createdAt")
    var createdAt: Long? = null

    constructor(): super()

    constructor(title: String, description: String, createdAt: Date) {
        this.title = title
        this.description = description

        val milliseconds = createdAt.time
        this.createdAt = milliseconds
    }

    @Ignore
    fun createdAtDate(): Date? {

        if (createdAt == null) {
            return null
        }

        val dateUpdated = createdAt!!

        return Date(dateUpdated)
    }

}
```

## Patterns

The folder contains custom properties for primitives elements like booleans validators, formatters...

For example, If we want a validator of email format:

```Android
object StringPattern {

    fun fromDate(date: Date, format: String): String {
        val simpleFormatter = SimpleDateFormat(format, Locale.getDefault())
        return simpleFormatter.format(date)
    }
}
```

## Services

If your project needs location services, network services... Please, not duplicate code. We create shared services for views that need it.

This example is a location service:

```Android

```

## Views

For a good design is necessary that we have custom views like EditText, TextViews, etc. This custom classes must be localized here.

This is a simple example for an EditText of login view:

```Android

```

## Recommendations

* Always to use region tags for Getters and Setters inside of class.

```android
//region GettersReg

...

//end

//region SettersReg

...

///end
```

* The classes must not contains more than 250 lines.
* Not use nomenclature for parameters or methods with more than 80 characters.

## References