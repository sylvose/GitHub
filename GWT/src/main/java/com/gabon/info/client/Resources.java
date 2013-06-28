package com.gabon.info.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	C:/Users/Sylvose ALLOGO/.git/GitHub/GWT/src/main/resources/com/gabon/info/client/Resources.properties'.
 */
public interface Resources extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Cancel".
   * 
   * @return translated "Cancel"
   */
  @DefaultMessage("Cancel")
  @Key("buttonCancel")
  String buttonCancel();

  /**
   * Translated "Login".
   * 
   * @return translated "Login"
   */
  @DefaultMessage("Login")
  @Key("buttonLogin")
  String buttonLogin();

  /**
   * Translated "Login".
   * 
   * @return translated "Login"
   */
  @DefaultMessage("Login")
  @Key("loginPrompt")
  String loginPrompt();

  /**
   * Translated "Password".
   * 
   * @return translated "Password"
   */
  @DefaultMessage("Password")
  @Key("passwordPrompt")
  String passwordPrompt();

  /**
   * Translated "Please Log In".
   * 
   * @return translated "Please Log In"
   */
  @DefaultMessage("Please Log In")
  @Key("pleaseLogIn")
  String pleaseLogIn();
}
