# KotlinNoteTaking
This is a simple Note taking app written in Kotlin for android. 
The project utilizes several of the many android architecture components such as 
Room, ViewModel, Coroutines, Live Data and Lifecycles. MVVM is the architecural design
pattern being used in this project. What made me went with this approach is because 
seperating business and data logic from the UI controller would greatly benefit myself
and others to understand the code structure with ease. Extending the project with more
functionalities as well as performing test on the code itself will also be 
uncomplicated. 

The data (notes) will be saved in the SQLite database with Room as a wrapper. Using
room to store data would be ideal because of the compile time query verification. 
ViewModel works as the gateway for the UI controller, which is the activity so that the
activity doesn't initiate any database operations.
The project's only data source would be the Room database, and the connection point to
the SQLite would be DAO. The project contains a repository class will mediate and 
retrieve data source by creating a clean API where the ViewModel can access to. This
way, I believe that the architecture is quite clean as all layouts are modular and 
decoupled from each other so every layer of the project has well defined responsibility.

Aside from the requirements stated in the assignment's pdf, I have taken the initiative
to include a priority picker for each of the notes created. This way, user can easily
filter out and sort the notes based on their importance ranging from 1 - 10 and 10 
being the most important and 1 being the least important. I have also included a button
to enable users to immediately delete all the notes available in case users find it 
tedious to delete all the notes one by one. The "kebab menu" icon located on the top 
right of the main page is the place to access the delete all notes function.

One feature I couldn't implement is the feature where users are able swipe on the note
to the left and a icon to delete the note would appear. Clicking on the icon will 
enable users to delete the note. Instead, I took a different approach where by swiping
the note all the way to the left would delete the note.

