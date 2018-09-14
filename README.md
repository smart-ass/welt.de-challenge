## welt.de challenge

The solution is an web app based on play framework.

To run the app, execute: `sbt run`

API:
- `/users`
- `/users/:userId`

Get the data of a specific user: `curl localhost:9000/users/1` 

Get the data of all users: `curl localhost:9000/users`

To run tests, execute `sbt test`

### Some comments about the solution

Play framework is obviously overkill for tasks like this. I only chose it for the sake of saving time, since I'm familiar 
with it better than with other frameworks.

`UserControllerSpec` is not a real unit test, because a third party api is involved. It should be either an integration 
test (when we can't control api), or api service should be run inside a container like docker (when it's developed by us).

Due to lack of time for the task, I didn't create any model classes for user and post objects and didn't validate
responses from the api properly. In a real app, of course, it should be done differently.