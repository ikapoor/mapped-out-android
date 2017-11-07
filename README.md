# mapped-out

ECHacks 2017 project

Mobile side of project

App which allows users to create accounts and conenct with friends and create "Events" around them

Location Based. 
In order to creat an event, user inputs the event name, description, date, time and location. The location get geocoded with google maps
api and a marker gets created on the main fragment which pinpoints the location. The user who created the event gets added to the 
attendees of the event. Other users can then view the main fragment which contains created events and join then and invite other friends. 

Uses firebase backend to integrat user login and event storage. 
