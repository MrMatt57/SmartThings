/**
 *  Security Lights
 *
 *  Author: mwwalker@gmail.com
 *  Date: 2014-01-19
 */
preferences {
	section("Devices to turn on/off...") {
		input "switchDevice", "capability.switch", title: "Switch?", required: false, multiple: true
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
	schedule("0 0 0 * * ?", scheduleSecurityLights)
    scheduleSecurityLights()
}

def scheduleSecurityLights() {

  def sunInfo = getSunriseAndSunset()
  
  log.info("Sunrise: ${sunInfo.sunrise}")
  log.info("Sunset: ${sunInfo.sunset}")
  
  runOnce(sunInfo.sunrise, sunriseEvent)
  runOnce(sunInfo.sunset, sunsetEvent)
  
}

def sunriseEvent() {
	switchDevice?.off()
}

def sunsetEvent() {
	switchDevice?.on()
}