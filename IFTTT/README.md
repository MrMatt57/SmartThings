SmartThings IFTTT Integration
===========
To extend more functionality than just being able to manipulate one device at a time, virtual devices and smart apps can be used.

1. Add Virtual Device Type [Virtual Switch](https://github.com/MrMatt57/SmartThings/blob/master/DeviceTypes/VirtualAlarmMaster.groovy)
2. Add Devices with type of the Virtual Device for each action you plan to initiate through IFTT
3. Add SmartApps to do stuff based on swith changing.  For example change Hello Home Mode: [Mode Change by Switch](https://github.com/MrMatt57/SmartThings/blob/master/SmartApps/ModeChangeSwitch.groovy) Note: The app returns the swith back to off, so that in the IFTTT recipes you only need to add "turn on device" actions.  
4. Add devices to Smartthings IFTTT channel
5. Add IFTTT recipes to turn on virtual swith.
6. Profit
