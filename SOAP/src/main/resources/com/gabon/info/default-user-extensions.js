/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

//
// $Id: default-user-extensions.js,v 1.5 2012/07/11 21:08:21 syallo1 Exp $
//

//
// Incorporated from: http://wiki.openqa.org/display/SEL/removeCookie
//

function createCookie(doc, name, value, path, days) {
    if (!path) {
        path = "/";
    }

    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires=" + date.toGMTString();
    }
    else {
        var expires = "";
    }
    
    doc.cookie = name + "=" + value + expires + "; path=" + path;
}

/**
 * Removes the cookie with the given name.
 *  text - the cookie name
 *  path - the cookie path
 */
Selenium.prototype.removeCookie = function(text, path) {
    createCookie(this.page().currentDocument, text, "", path, -1);
};

//
// Incorporated from user-extensions.js.sample from Selenium RC 0.8.1
//

// The following examples try to give an indication of how Selenium can be extended with javascript.

/**
 * All do* methods on the Selenium prototype are added as actions.
 * Eg add a typeRepeated action to Selenium, which types the text twice into a text box.
 * The typeTwiceAndWait command will be available automatically
 */
Selenium.prototype.doTypeRepeated = function(locator, text) {
    // All locator-strategies are automatically handled by "findElement"
    var element = this.page().findElement(locator);

    // Create the text to type
    var valueToType = text + text;

    // Replace the element text with the new text
    this.page().replaceText(element, valueToType);
};

/**
 * All assert* methods on the Selenium prototype are added as checks.
 * Eg add a assertValueRepeated check, that makes sure that the element value
 * consists of the supplied text repeated.
 * The verify version will be available automatically.
 */
Selenium.prototype.assertValueRepeated = function(locator, text) {
    // All locator-strategies are automatically handled by "findElement"
    var element = this.page().findElement(locator);

    // Create the text to verify
    var expectedValue = text + text;

    // Get the actual element value
    var actualValue = element.value;

    // Make sure the actual value matches the expected
    Assert.matches(expectedValue, actualValue);
};

/**
 * All get* methods on the Selenium prototype result in
 * store, assert, assertNot, verify, verifyNot, waitFor, and waitForNot commands.
 * E.g. add a getTextLength method that returns the length of the text
 * of a specified element.
 * Will result in support for storeTextLength, assertTextLength, etc.
 */
Selenium.prototype.getTextLength = function(locator) {
	return this.getText(locator).length;
};

/**
 * All locateElementBy* methods are added as locator-strategies.
 * Eg add a "valuerepeated=" locator, that finds the first element with the supplied value, repeated.
 * The "inDocument" is a the document you are searching.
 */
PageBot.prototype.locateElementByValueRepeated = function(text, inDocument) {
    // Create the text to search for
    var expectedValue = text + text;

    // Loop through all elements, looking for ones that have a value === our expected value
    var allElements = inDocument.getElementsByTagName("*");
    for (var i = 0; i < allElements.length; i++) {
        var testElement = allElements[i];
        if (testElement.value && testElement.value === expectedValue) {
            return testElement;
        }
    }
    return null;
};

