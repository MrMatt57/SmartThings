SmartThings
===========
This is a place where I plan to collect my [Smart Things](http://www.smartthings.com/) SmartApps and home automation ideas.

## Design Philosophies:
- Presence based actions
- Little or no interaction required
- Notification will have priority

## Modes

All modes are controlled by Mode Control smart app or manually through the mobile application interface.  Other applications subscribe to mode changes and coordinate their specific funtionality.

  - Home
  - Away
  - Sleeping
  - Offline

## Security System

Need to write up... using an instance of Security Alarm for each mode


## Design Notes

Wake up (How to best detect?)
  - Lights On (based on sunrise and/or light sensor)
  - Coffee Maker On

- Everyone Sleeping
  - All lights not in Security group off
  - Arm Perimeter
  - Set Thermostat

- Everyone Away
  - Arm Security
  - Set Thermostat
  
- Someone Arrives
  - Disarm Security
  - Set Thermostat
  - Lights On (based on sunrise and/or light sensor)

- Security Alert
  - High Priority Notifications
  - Sound Alarm
  - Lights On for X minutes (based on sunrise and/or light sensor)

- Sunrise 
  - Turn Security Lights Off (Offset)

- Sunset
  - Turn Security Lights On (Offset)

- Left Open 
  - Permimeter Doors (< 60f and > 80f)

- Hall Motion
  - Dim on Light
  - Set Home Status?

- Watching Movie
  - Light Scene

Need to figure out:
- Coffee machine interface (Keurig B150)
  
