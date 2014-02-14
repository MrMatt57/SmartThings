/**
 *  On/Off Button Tile
 *
 *  Author: SmartThings
 *
 *  Date: 2013-05-01
 */
metadata {
	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles {
		standardTile("button", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'Disarmed', action: "switch.on", icon: "st.alarm.alarm.alarm", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'Armed', action: "switch.off", icon: "st.alarm.alarm.alarm", backgroundColor: "#79b821", nextState: "off"
		}
		main "button"
		details "button"
	}
}

def parse(String description) {
}

def on() {
	sendEvent(name: "switch", value: "on")
}

def off() {
	sendEvent(name: "switch", value: "off")
}

