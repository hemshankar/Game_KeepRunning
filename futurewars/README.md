=====================Story=====================


Man mad calamities has struck the country because of the certain virus contamination. There are various decontamination centres have the antidote.
You need to reach to these centres one by one within specified time, and release the antidote to save the city and nation.

Rules:

1. You need to start from one city and reach to other city within specified time.
2. Sooner you complete a city, large time you have to save the country.
3. If you fail to reach the targets in time, some other city will get contaminated, and needs decontamination again, hence requiring more time and effort.
4. If all the cities are saved Country is saved, and once all the Countries are saved world is saved, game is finished
5. New virus will break and the game restarts again.





================Tasks================

1. Bots action automation - Done
2. Music - Done
3. HUD -- Not required ? 
4. Test on Andriod -- Done
5. In-Aap purchase -- Done
6. Advertisement addition
7. Multiple stage design -- Outline Done, minimal effort required
8. Storing data in DB --  Done. may be not required, data are stored in preferences
9. Controls for andriod -- Done
10. Special Powers -- Not to be done/To be done in later stages / Not in version one
11. User can transform between heroes -- Not in version1  
12. Heroes can pick-up enemies and throw them. -- Done
13. Enemies will be all sorts of enemies... plants, animals, fishes, zombies, humans, --- TBD (for now only animals are there, more needs to be added)
14. There will be healers let say sun, and other extra things --- Out of version 1 (Energy drink is the only option)
15. Special powers of each hero. --- TBD in version 2
16. Flying Kalieen --- Done Needs finishing
17. Launcher(s) -- May be not required
18. Sticky Bomb --- Done
19. Coins/Coin Bags  --- Done
20. Snake/Dragon - Later
21. Runs only Forward -- Done
22. No Flying Only jumping./Change the controle --- Done
23. Water Baloons --- Done
24. Bricks --- Done
=============================================================================25. Use Asset manager --- TBD
=============================================================================26. Appearance of the player should change with health --- TBD
27. Speed display --- Done
28. Health Bar Display --- Done
29. Rocket Fuel Display --- Done
30. Skates -- Done
=============================================================================31. Transparent or Shield state (Objects will collide, but will not lose health) -- TBD

33. Magnet (should attract coins only) -- Done
34. Coin score --- Done
=============================================================================35. Different sounds: Metal collide, brick broken, wood broken, explosion, Gun Fire -- TBD
=============================================================================36. Obstacles: TBD
	1. Reflector(if you fire, bullet will reflect), 
	2. water Holes (if submerged for 3 secs, game over), 
	3. Fire, 
	4.Mines (can be detected only if mine detector is present)  

=============================================================================37. Story Animation --TBD
=============================================================================38. Loading screen --TBD
39. Welcome Screen -- Done
=============================================================================40. Stage decision: what powers and enemies to be unlocked in each stage --TBD
41. Clean code related to map(make sure to retain in a separate place as might be required for later games) -- Done (check in older check-in in repository)
42. Pause Screen. -- Done
43. Change to jumping -- Done
44. Add Flying Kit -- Done
45. Magnet ---  Done
=============================================================================46. Purchase Screen -- TBD
47. Random movement of the hanging/flying objects- Done
48. Slow the speed of the bombs, increase as the speed of the object increases. -- Done
=============================================================================49. Coin Sounds --TBD
=============================================================================50. Make Stamina modifiable: Heath rapidly decreases initailly and when the stamina increases Health decreases slowly 
51. Update the jumping kit to affect only when jump button is clicked. -- jumping is out version 1 (may not even be supported later)
52. Game End Screen -- Done
53. Destroy/Remove all the objects which are not required (as the player moves on) :  ================Done 
54. Increase the Screen width when speed increases: ================Done 
============================================================================= 55. Animation/Art work --TBD
56. Rocket -- Done
60. Gift Pack -- Done
61. One girl character -- Later may be
62. Horse Ride -- Done
63. When the birds/pushers fly fast they should flap fast -- TBD
64. Make a generic method for fly. Since most of the game objects will be flying, they can use this -- Done
65. Change the all the game objects to dynamic bodies as they need to move. Brick may be static -- Done
=============================================================================67. there is possible a memory Leak... check if all the items are disposed properly... Monitor using memory monitor provided in android studios --TBD
68. Character should talk --out of version one
=============================================================================69. Auto aim/ auto tracking of the bullet can be done by setting the bullet position. Can be used for locking the target. -- Done
=============================================================================70. Highlight speed when player crosses the speed limits. --TBD
71. The tararara sound when player does something amazing --May be not
72. Ground will have mix of sand and thorns... which will either reduce the speed or health --out of version 1
73. Handle background separately... too much of code is required otherwise -- Done
74. Michal jacson moon walk... ================Out of version 1
75. Lots of skins... can be used for creating menus 
76. Coin should come smoothly to the user. -- Done
=============================================================================77. resize all the images.. and use different images for different camera angle. -TBD
78. Good resource for game image processing.
79. Include sawastik sign
80. Improve kaaleen -- Done
81. Cut videos from youtube http://hesetube.com/watch?v=bru7H11qA4Y
82. 10 new challenges daily with different/random difficulty. -- Out of version one
83. Underwater theme for crossing the ocean. -- Out of version one
84. Make sure that there are no objects in the world, when the game is started or restarted (i.e. setScreen is called again for the GameScreen) : ================Done 
85. Slow motion effect is tough: ================Done 
86. Player can be drunk. -- Out of version one
=============================================================================88. Flying horse, normal horse, bulls, kites, -- TBD
89. Gun/Bullet should be auto fire to enemies, and when bullets are up, gun is thrown... -- Out of version one
=============================================================================90. Take something from the present play that will help in future or in the next match. -- For now it is Gift
92. All the expression of the face should be at the Left/Right - Top of the screen. -- may in the later release.
=============================================================================93. Spring objects in ground to throw up. -- TBD
=============================================================================94. Shooter objects, for shooting speed bombs and water balloons --TBD
95. Clicking on the character should catch them. -- Done
=============================================================================96. Make Kite as eagle, and change the colour to black --TBD
97. Power of generating game objects while playing. -- May be later
98. Gold Biscuits and Cash -- Out of version one
99. Make on-line version... Different people all over the world are helping each other to clean the world. -- Version 2


101. Mission based earning + in-game earning. --Out of version one
	Type of missions: 
		1. Find the Key (every stage will have a key to unlock the target door)
		2. Same the Man/Animal(s)
		3. Catch the thief.
		4. Destroy the wall
		5. Catch the Animal
		6. Clear the route
		7. Generate food for animals (there are 10 points in map which when hit will release the food)
		8. Collect 10 items. (for some reason/person)
		9. Bring medicine/other imp thing. (start from a place and return)
		10. Spray Antidote. (mission play like type 7)
		11. Escort someone to safety.
		12. Special levels for Coins/Biscuits.
		13. Stay in the air for 60 seconds
		
102. Purchase weapons and other tools or upgrade(skill/tools). -- Done
103. Ranking based on mission skills --Out of version one
104. On-line mission update. --Out of version one
105. Types of generic Objects. -- Done
	1. Burst on collide
	2. Follow on collide
	3. Rope Joint
	4. Weld Joint
	
=============================================================================106. Gain points in every activity, points will be converted to coins. -- TBD
=============================================================================107. Cars, trucks, Skates (to collect), Normal Man(to save), Robber (to catch), Bad Drones (to destroy), Good Drones (it protects the player)) rockets (to fly)
=============================================================================108. Jet Packs to collect. --TBD
110. Arrow pointing towards the target -- Out of versio one
111. Auto fire to the nearest enemy. Toggle on fire and auto fire. -- Done
112. Points to catch and accelerate should always be present. -- Done
=============================================================================113. Different bullets, single, double, spower -- TBD
114. Things mentioned in the properties files should evenly distributed to the distance given in the properties file. --Done
115. Have parachute if user wanted to use while falling. -- Done


116. Google play publisher account -- Done
117. Google Payments merchant account. --Done
116. 

============================BUGS================================
1. Each object is creating its own atlas. Fix this to use cache to store each atlas. --Done
2. Backgrounds are coming in foreground... Solution will be to use 2 array of game objects one for background and other for foreground or add the background to the bottom while add the foreground to the top --Done
3. background of an object lags behind when an object moves -- Done 
