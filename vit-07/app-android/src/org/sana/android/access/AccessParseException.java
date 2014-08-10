package org.sana.android.access;

/**
 * An Exception to throw when parsing the Acl declared in text form.
 * 
 * @author Sana Development Team
 *
 */
public class AccessParseException extends Exception {
    private static final long serialVersionUID = 1L;
    
    /**
     * A new Exception with a message.
     * 
     * @param text the message.
     */
    public AccessParseException(String text) {
        super(text);
    }
}