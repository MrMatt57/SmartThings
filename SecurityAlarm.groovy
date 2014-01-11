/**
 *  Security Alarm
 *
 *  Author: mwwalker@gmail.com
 *  Date: 2014-01-09
 */
preferences {
	section("When any of these devices trigger...") {
		input "contact", "capability.contactSensor", title: "Contact Sensor?", multiple: true, required:false
		input "accelerationSensor", "capability.accelerationSensor", title: "Acceleration Sensor?", multiple: true, required:false
    	input "motionSensor", "capability.motionSensor", title: "Motion Sensor?", multiple: true, required:false
		input "switchDevice", "capability.switch", title: "Switch?", required: false, multiple: true
		input "presenceDevice", "capability.presenceSensor", title: "Presence Sensor?", required: false, multiple: true
    }
    section("Set off these Alarms...") {
		input "alarm", "capability.alarm", title: "Alarm Device?", required: false, multiple: true
        input "alarmSwitch", "capability.switch", title: "Switch Device?", required: false, multiple: true
        input "alarmTimeoutSeconds", "number", title: "Alarm Duration Seconds? (default 30)", required:false
	}
    section( "Send these notifications..." ) {
		input "sendPushMessage", "enum", title: "Send a push notification?", metadata:[values:["No","Yes"]]
		input "phone", "phone", title: "Send a Text Message?", required: false
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	if (accelerationSensor) {
		subscribe(accelerationSensor, "acceleration.active", triggerAlarm)
    }
    if (contact) {
    	subscribe(contact, "contact.open", triggerAlarm)
    }
    if (motionSensor) {
    	subscribe(motionSensor, "motion.active", triggerAlarm)
    }
    if (switchDevice) {
    	subscribe(switchDevice, "switch.on", triggerAlarm)
    }
    if (presenceDevice) {
    	subscribe(presenceDevice, "presence", triggerAlarm)
    }
}

def triggerAlarm(evt) {

	// Signal Alarm
    if (alarm) {
    	alarm?.both()
    }
    if (alarmSwitch) {
    	alarmSwitch?.on()
    }
    log.debug "Alarm triggered: $evt.name - $evt.value"
    
    //Send Notification
	def msg = "Alarm triggered by $evt.displayName"
    if (sendPushMessage == "Yes") {
    	sendPush(msg)
        log.debug "Push Notification Sent"
    }
    if (phone) {
    	sendSms(phone, msg)
        log.debug "SMS sent to $phone"
    }
    log.debug(msg)
    
	// Schedule Alarm to turn Off
    def alarmTimeoutSeconds = alarmTimeoutSeconds ?: 30
    runIn(alarmTimeoutSeconds, resetAlarm)
    log.debug "Reset in $alarmTimeoutSeconds Seconds"
}

def resetAlarm() {
    if (alarm) {
    	alarm?.off()
    }
    if (alarmSwitch) {
    	alarmSwitch?.off()
    }
	log.debug "Alarm reset"
}

