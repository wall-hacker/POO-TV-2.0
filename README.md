````
  ____           _____       ____    ___  
 |  _ \ ___   __|_   _|_   _|___ \  / _ \ 
 | |_) / _ \ / _ \| | \ \ / / __) || | | |
 |  __/ (_) | (_) | |  \ V / / __/ | |_| |
 |_|   \___/ \___/|_|   \_/ |_____(_)___/ 
                             
  ````
  
- - -
### Description
- - -  
Poo-Tv is a backend implementation of a platform for watching
movies/series from the user's perspective. The basic functionalities
offered for this platform include logging in, registering, and moving
on a specific page available. From the movies page, the user can also
search for a specific movie, filter the movie list based on desired
actors, genres, and even sort movies based on duration and rating.
In order to watch a movie, the user has to purchase it for the
standard price (2 tokens), or for free if the user has premium
account. A user can upgrade his account to premium for 10 tokens and
get 15 free movies, then he pays 2 tokens for each purchase movie
just like standard users. After purchasing the movie, the user can
watch it, then like and rate the movie from 1 to 5. The tokens are
taken explicitly by the user from his balance. At the end, the user
can log out and the platform is restores to unauthenticated homepage.
- - -
### Structure && Implementation 
- - -
**1] actions package:**
 - The actions package contains the implementations of all
desired functionalities, be it a ChangePage, OnPage, **Back, 
Database or Recommandation type action**
 
- **The back package contains the BackAction class which contains
all the methods or as I call them actions related to reverting 
to the previous page(login, register, logut, movies, seeDetalis, 
upgrades); these methods are then called using a switch
statement by a subclass named Back**
  - **This new functionality is implemented using a sort of stack
  that keeps track of all the visited pages, and "pops" the most
  recent one when performing a Back type action**

- **The database package contains the new functionalities to add and remove
movies from the database. This functionality is implemented using 5 classes
and 3 design patterns:**
  - **The Database class is the basic abstract class called by main in order
to resolve a back type action**
  - **The DatabaseAction represents the abstract class base of the Strategy design pattern.
It holds two methods, the action method which either adds or delets a movie and the notify method.
The notify method is implemented by the principle of the Observer design pattern.
This class is exetended by two subsequent "strategies", namely DatabaseAdd and DatabaseDelete**
  - **The DatabaseActionFactory implements the Factory design pattern and is meant to
generate either an instance of DatabaseAdd either an instance of DatabaseDelete based on the
parameter given to the creation method**

- **The Recommandation class generates a notification (another sort of observer
pattern update function) based on certain criteria only if the active user is a premium user**

- The entities package just contains the CurrentPage, CurrentUser and Formatted Output
classes which are used by almost every other class

 - The changepage package conatains the ChangePageAction class
contains all the methods or as I call them actions related to
changing the current page(login, register, logut, movies,
seeDetalis, upgrades); these methods are then called using a
switch statement by a subclass named ChangePage

- The onpage package conatains the OnPageAction class contains all the methods or as I
call them actions that can happen on the current page(login,
register, search, filter, buyTokens, buyPremiumAccount,
purchase, watch, like, rate); these methods are then called
using a switch statement by a subclass named OnPage
  - **Added a subscribe method which subscribes the active user to a certain genre thus affecting the outcome
    of the recommandation functionality**

- The main abstract classes, namely: ChangePage, OnPage, **Back,
  Database, Recommandation**, are then called by the Main
method of the program in order to generate the desired output

**2] database package:**
 - The database package contains all the necessary classes
to gather all information about the users, movies and actions
   - **The class Notification and User field notifications have been added
 in order to be able to implement the Database and Recommandation functionalities**

 - They contain fields in such a way that the JSON structure
of the input files can be parsed accordingly and all information
accessed by the Main class and the classes in actions package

**3] the Main class:**
- The Main class consists of a simple structure:
  - the input file is parsed by our Input type object and the
  rest of the classes in our database package, using an
  objectmapper
  - then we set the currentPage and currentUser, objects used
  in basically all the other methods
  - then we iterate through the Actions array and call on the
  respective methods, be it a ChangePage or a OnPage type
  action
  - **then we must call the Recommandation class run method at the end of
  each program run because it will automatically check whether the logged user is
  a premium user or not and make a recommandation based on that information**
  - inside the actions' package methods the output ArrayNode
  gets filled with information, the last step in our Main
  being to write that information to the output file

- - -
### Feedback && Comments
- - -

Note: I've highlighted all the documentation regarding the second phase of the project using
bold text in markdown.

This project was way easier to understand then PooTv1.0 partially because while
having implemented most of the functionalites simply and easily understandable
the first time around, modification and adding functionality didn't come as a problem.

The challenging part was having to add 4 different design patterns, and although I know
I could have done it better I hope the "overlap" that I have created doesn't impact me negatively.

As a reminder the design patterns I used are as follows:
- Singleton - for the CurrentUser class
- Strategy - in designing the Database functionality with the DatabaseAction
class being the base class which is extended by the "specialised strategies"
DatabaseAdd and DatabaseDelete
- Factory - the class DatabaseActionFactory has the creation method called by the run
method in the Database class
- Observer - the notify methods serve the purpose of update functions following the principles of the Observer
pattern, the user being the subject that is being observed, and the specialised observers being the
DatabaseAdd and DatabaseDelete classes


