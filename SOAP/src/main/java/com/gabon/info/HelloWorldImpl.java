
package com.gabon.info;

import javax.jws.WebService;

@WebService(endpointInterface = "com.gabon.info.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

