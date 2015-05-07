/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.studio.core.log;

import it.eng.spagobi.studio.core.Activator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class SpagoBILogger {

	/**
	 * To send a log.
	 * @param statut : the statut of the log,
	 * IStatut.SEVERE, IStatut.ERROR, IStatut.INFO, etc...
	 * @param msg : the message of this log
	 * @param exception : the exception. It can be null
	 */
	public static void log(final int statut, final String msg, final Throwable exception) {
		if(Activator.getDefault() != null) {
			Activator.getDefault().getLog().log(newStatus(statut, msg , exception));
		}
	}

	/**
	 * To send an info log message.
	 * @param msg : the message
	 */
	public static void infoLog(final String msg) {
		if(Activator.getDefault() != null) {
			Activator.getDefault().getLog().log(newStatus(IStatus.INFO, msg, null));
		}
	}

	/**
	 * To send an error log message.
	 * @param msg : the message
	 * @param exception : the exception
	 */
	public static void errorLog(final String msg, final Throwable exception) {
		if(Activator.getDefault() != null) {
			Activator.getDefault().getLog().log(newStatus(IStatus.ERROR, msg, exception));
		}
	}

	/**
	 * To send a warning log message.
	 * @param msg  :the message
	 */
	public static void warningLog(final String msg) {
		if (Activator.getDefault() != null) {
			Activator.getDefault().getLog().log(newStatus(IStatus.WARNING, msg, null));
		}
	}

	/**
	 * To send a warning log message with an exception.
	 * @param msg  :the message
	 * @param exception : the exception
	 */
	public static void warningLog(final String msg, final Throwable exception) {
		if (Activator.getDefault() != null) {
			Activator.getDefault().getLog().log(newStatus(IStatus.WARNING, msg, exception));
		}
	}

	/**
     * This method must not be called outside this class.
     * Utility method for creating status.
     * @param severity : status severity
     * @param message : message
     * @param exception : the exception
     * @return IStatus
     */
    public static IStatus newStatus(final int severity, final String message, final Throwable exception) {
        String statusMessage = message;
        if (message == null || message.trim().length() == 0) {
            if (exception.getMessage() == null) {
                statusMessage = exception.toString();
            } else {
                statusMessage = exception.getMessage();
            }
        }
        if (Activator.getDefault() != null) {
        	IStatus stat = new Status(severity,	Activator.PLUGIN_ID, severity, statusMessage, getCause(exception));
        	return stat;
        } else {
        	return null;
        }
    }

    /**
     * This class must not be called outside this class
     * Utility method to get the cause of an exception.
     * @param exception : the exception to analyze
     * @return Throwable
     */
    public static Throwable getCause(final Throwable exception) {
        // Figure out which exception should
    	//actually be logged -- if the given exception is
        // a wrapper, unwrap it
        Throwable cause = null;
        if (exception != null) {
            if (exception instanceof CoreException ) {
                // Workaround: CoreException contains
            	//a cause, but does not actually implement getCause().
                // If we get a CoreException, we need to
            	//manually unpack the cause. Otherwise, use
                // the general-purpose mechanism.
            	//Remove this branch if CoreException ever implements
                // a correct getCause() method.
                CoreException ce = (CoreException) exception;
                cause = ce.getStatus().getException();
            } else {
            	// use reflect instead of a direct call
            	//to getCause(), to allow compilation against
            	//JCL Foundation (bug 80053)
            	try {
            		Method causeMethod = exception.getClass().getMethod("getCause", new Class[0]);
            		Object o = causeMethod.invoke(exception, new Object[0]);
            		if (o instanceof Throwable) {
            			cause = (Throwable) o;
            		}
            	} catch (NoSuchMethodException e) {
            		//ignore
					cause = null;
            	} catch (IllegalArgumentException e) {
            		// ignore
					cause = null;
				} catch (IllegalAccessException e) {
            		// ignore
					cause = null;
				} catch (InvocationTargetException e) {
            		// ignore
					cause = null;
				}
            }
            if (cause == null) {
                cause = exception;
            }
        }
        return cause;
    }

	
}
