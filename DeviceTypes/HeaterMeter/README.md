SmartThings HeaterMeter Device
===========
SmartThings - http://smartthings.com

HeaterMeter Project - https://github.com/CapnBry/HeaterMeter/wiki

## Installation
1. Add custom device type
2. Add Device with local device (put anything for device id, it is set withing the device)
3. Set Local IP address device preference

## Limitation
1. Subject to SmartThings poll interval.  On average I am seeing every 5-10 minutes.
2. Capabilities are exposed through custom attributes and can't be subscribed too like native device capabilities.  
3. Lan Only Monitoring, Heatermeter must be on the same network as the Smart Things hub.  Can be monitoroed thorugh SmartThings anywhere on the internet.

## TODO

- Design.  Currently it just shows data.
- Add setpoint capabilities.  Slider control currently only support 0-100 values :(  Consider adding to settings but can't find the same event chain (installed, setup, updated as with smartapps
- Leverage device capabilities so that they can easily be subscribed to from apps (currently using custom attributes) Really should be a device for each probe with temperature capability.  Need to explore other capabilities too.
  
