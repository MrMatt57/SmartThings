/**
 *  Heatermeter
 *
 *  Copyright 2015 Matthew Walker
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
 preferences {
	input("ipaddress", "text", title: "IP Address", description: "Your Heatermeter's internal ip address")
}
 
metadata {
	definition (name: "Heatermeter", namespace: "mrmatt57", author: "Matthew Walker") {
		capability "Polling"
		capability "Temperature Measurement"
        attribute "available", "string"
        attribute "setpoint", "string"
        attribute "fanSpeed", "string"
        attribute "lid", "string"
        attribute "probe1_name", "string"
        attribute "probe1_value", "number"
        attribute "probe1_dph", "number"
        attribute "probe2_name", "string"
        attribute "probe2_value", "number"
        attribute "probe2_dph", "number"
        attribute "probe3_name", "string"
        attribute "probe3_value", "number"
        attribute "probe3_dph", "number"
        attribute "probe4_name", "string"
        attribute "probe4_value", "number"
        attribute "probe4_dph", "number"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
    	valueTile("setpoint", "device.setpoint", decoration: "flat") {
            state "setpoint", label:'${currentValue}°'
        }
        valueTile("fanSpeed", "device.fanSpeed", decoration: "flat") {
            state "fanSpeed", label:'Fan ${currentValue}%'
        }
        valueTile("lid", "device.lid", decoration: "flat") {
            state("lid", label:"Lid ${currentValue}")
        }
       
        
        valueTile("probe1_name", "device.probe1_name", decoration: "flat") {
            state "probe1_name", label:'${currentValue}'
        }
        valueTile("probe1_value", "device.probe1_value", decoration: "flat") {
            state "probe1_value", label:'${currentValue}°'
        }
        valueTile("probe1_dph", "device.probe1_dph", decoration: "flat") {
            state "probe1_dph", label:'${currentValue}° /hr'
        }
        
        valueTile("probe2_name", "device.probe2_name", decoration: "flat") {
            state "probe2_name", label:'${currentValue}'
        }
        valueTile("probe2_value", "device.probe2_value", decoration: "flat") {
            state "probe2_value", label:'${currentValue}°'
        }
        valueTile("probe2_dph", "device.probe2_dph", decoration: "flat") {
            state "probe2_dph", label:'${currentValue}° /hr'
        }
        
        valueTile("probe3_name", "device.probe3_name", decoration: "flat") {
            state "probe3_name", label:'${currentValue}'
        }
        valueTile("probe3_value", "device.probe3_value", decoration: "flat") {
            state "probe3_value", label:'${currentValue}°'
        }
        valueTile("probe3_dph", "device.probe3_dph", decoration: "flat") {
            state "probe3_dph", label:'${currentValue}° /hr'
        }
        
        valueTile("probe4_name", "device.probe4_name", decoration: "flat") {
            state "probe4_name", label:'${currentValue}'
        }
        valueTile("probe4_value", "device.probe4_value", decoration: "flat") {
            state "probe4_value", label:'${currentValue}°'
        }
        valueTile("probe4_dph", "device.probe4_dph", decoration: "flat") {
            state "probe4_dph", label:'${currentValue}° /hr'
        }
        
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat") {
			state "default", action:"polling.poll", icon:"st.secondary.refresh"
		}
        main(["setpoint", "probe1_value", "probe2_value", "probe3_value", "probe4_value"])
        details(["lid", "setpoint", "fanSpeed", "probe1_name", "probe1_value", "probe1_dph", "probe2_name", "probe2_value", "probe2_dph", "probe3_name", "probe3_value", "probe3_dph", "probe4_name", "probe4_value", "probe4_dph", "refresh"])
	}
}

// parse events into attributes
def parse(String description) {
    def msg = parseLanMessage(description)
    def json = msg.json
	log.debug "Heatermeter Response: $json"
    def setpoint = json.set.toInteger()
    def fan = json.fan.f.toInteger()
    def fanState = "off"
    if(fan.toInteger() > 0){
    	fanState = "on"
    }
    
    def lidText = "closed"
    if(json.lid.toInteger() > 0){
    	lidText = "open"
    }
    	
   
    def probe1 = json.temps[0]
    def probe2 = json.temps[1]
    def probe3 = json.temps[2]
    def probe4 = json.temps[3]
    
  
    sendCustomEvent([name: "setpoint", value: "$setpoint", unit: "fahrenheit", isStateChange: true])
    sendCustomEvent([name: "fanSpeed", value: "$fan", state: fanState, isStateChange: true])
    sendCustomEvent([name: "lid", value: lidText, state: lidText, isStateChange: true])
    
    sendCustomEvent([name: "probe1_name", value: "$probe1.n", isStateChange: true])
    sendCustomEvent([name: "probe1_value", value: probe1.c.toInteger(), unit: "fahrenheit", isStateChange: true])
    sendCustomEvent([name: "probe1_dph", value: probe1.dph.toInteger(), unit: "fahrenheit", isStateChange: true])
    
    sendCustomEvent([name: "probe2_name", value: "$probe2.n", isStateChange: true])
    sendCustomEvent([name: "probe2_value", value: probe2.c.toInteger(), unit: "fahrenheit", isStateChange: true])
    sendCustomEvent([name: "probe2_dph", value: probe2.dph.toInteger(), unit: "fahrenheit", isStateChange: true])
    
    sendCustomEvent([name: "probe3_name", value: "$probe3.n", isStateChange: true])
    sendCustomEvent([name: "probe3_value", value: probe3.c.toInteger(), unit: "fahrenheit", isStateChange: true])
    sendCustomEvent([name: "probe3_dph", value: probe3.dph.toInteger(), unit: "fahrenheit", isStateChange: true])
    
    sendCustomEvent([name: "probe4_name", value: "$probe4.n", isStateChange: true])
    sendCustomEvent([name: "probe4_value", value: probe4.c.toInteger(), unit: "fahrenheit", isStateChange: true])
    sendCustomEvent([name: "probe4_dph", value: probe4.dph.toInteger(), unit: "fahrenheit", isStateChange: true])
}

def sendCustomEvent(params) {
    if(isStateChange(device, params.name, params.value.toString())){
    	log.debug("sendEvent $params")
    	sendEvent(params)
    }
}


// handle commands
def poll() {
	log.debug "Executing 'poll'"
   
    def host = ipaddress
    def port = "80"
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(port)
    device.deviceNetworkId = "$hosthex:$porthex" 
    log.debug "The device id $host:$port configured is: $device.deviceNetworkId"

    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
        path: "/luci/lm/hmstatus",
        headers: [
        HOST: "$host:$port"
        ]
        )
    log.debug hubAction
    return hubAction
}

def setSetpoint(temp) {
	log.debug temp
}


private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex

}

private String convertPortToHex(port) {
    String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport
}

private Integer convertHexToInt(hex) {
    Integer.parseInt(hex,16)
}

private String convertHexToIP(hex) {
    [convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}