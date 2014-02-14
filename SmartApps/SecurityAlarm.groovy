/**
 *  Security Alarm
 *
 *  Author: mwwalker@gmail.com
 *  Date: 2014-01-09
 */
preferences {
    section("Master switch") {
        input "masterSwitch", "capability.switch", title: "Master?", required: false
    }
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
        input "alarmSwitchTimeoutSeconds", "number", title: "Switch Duration Seconds? (default 30)", required:false
    }
    
    section( "Send these notifications..." ) {
        input "sendPushMessage", "enum", title: "Send a push notification?", metadata:[values:["No","Yes"]]
        input "messageText", "text", title: "Message text?", required: false
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
    if (masterSwitch) {
        subscribe(masterSwitch, "switch", toggleAlarm)
    }
}

def triggerAlarm(evt) {

    def msg = "[$evt.displayName] Alarm triggered"
    if (messageText) {
        msg = "[$evt.displayName] $messageText"
    }
    
    log.debug(masterSwitch.latestValue("switch"))
    
    if (masterSwitch && masterSwitch.latestValue("switch") == "off") {
        msg = "Master Alarm Switch OFF - $msg"
        sendMsg(msg)
        log.debug(msg)
        return;
    }
    
    // Signal Alarm
    alarm?.both()
    alarmSwitch?.on()
    
    //Send Notification
    sendMsg(msg)
    log.info "$msg"
    
    // Schedule Alarm to turn Off
    def alarmTimeoutSeconds = alarmTimeoutSeconds ?: 30
    runIn(alarmTimeoutSeconds, resetAlarm)
    log.debug "Reset Alarm in $alarmTimeoutSeconds Seconds"
    
    def alarmSwitchTimeoutSeconds = alarmSwitchTimeoutSeconds ?: 30
    runIn(alarmSwitchTimeoutSeconds, resetAlarmSwitch)
    log.debug "Reset Switch in $alarmSwitchTimeoutSeconds Seconds"
}

def sendMsg(msg) {
    if (sendPushMessage == "Yes") {
        sendPush(msg)
        log.debug "Push Notification Sent"
    }
    if (phone) {
        sendSms(phone, msg)
        log.debug "SMS sent to $phone"
    }
}

def toggleAlarm(evt) {
    if (evt.value == "off") {
        resetAlarmSwitch()
        alarm?.off()
        log.info("Master switch turned off alarm.")
    }
    else {
        log.info("Master swtich turned on alarm.")
    }
}

def resetAlarmSwitch() {
    alarmSwitch?.off()
    log.debug "Alarm Switch Reset"
}

def resetAlarm() {
    alarm?.off()
    log.debug "Alarm Reset"
}

