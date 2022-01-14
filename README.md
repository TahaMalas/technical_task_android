# Sliide Android developer challenge 
## Congratulations, you have reached the next stage which is solving a Sliide practical test.
We’d like to you to write simple Android application for managing users.

### Description
When we have reviewed your test, and any accompanying documents you feel necessary, if we like what we see, we’ll invite you to join us for a video conversation during which we’ll ask you to go through your test, explaining any decisions that you made.

### Implementation
For implementation we use https://gorest.co.in/ public API

### Functional requirement
Feel free to use whatever flare you can to show off your skills.

You shouldn't spend more than 1 day on implementation, but if you need more time to show the best quality, feel free to use it. We prefer finished, clean, production ready implementation with unit tests, rather than half done solution.

#### 1 Displaying list of users
- After app is open list of users is displayed (only users from last page of the endpoint)
- Each entry contains name, email address and creation time (relative to now)
- Loading and error state are welcome

#### 2 Adding new user
- After + button is clicked pop up dialog is displayed with name and email entries
- After confirmation and successful user creation (201 response code) item is added to the list

#### 3 Removing existing user
- After item long press pop up dialog is displayed with question “Are you sure you want to remove this user?“
- After OK is clicked and user is removed (204 response code) item is deleted from the list

### Technical requirements
- Application must be developed in Kotlin with minimum Android SDK version of 21
- You are free to use whatever frameworks or tools you see fit
- Application needs to support device rotation
- Design should follow Material design guidelines
- RxJava or Coroutines
- Architecture one of MVP/MVVM/MVI
- Dependency injection with Dagger 2 or Hilt
- Unit tests

### Evaluation Criteria
- You create testable code
- You pay attention to detail
- Code should be production ready

### Deliverables
- The forked version of this repo


