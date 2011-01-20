/**
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Eclipse Public License (EPL).
 * Please see the license.txt included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package org.python.pydev.core.callbacks;

import java.util.ArrayList;

import org.python.pydev.core.log.Log;

public class CallbackWithListeners<X> implements ICallbackWithListeners<X>{

	private ArrayList<ICallbackListener<X>> listeners;

	public CallbackWithListeners() {
		this.listeners = new ArrayList<ICallbackListener<X>>();
	}
	
	public Object call(X obj) {
		Object result = null;
		for(ICallbackListener<X> listener:this.listeners){
			try {
				Object callResult = listener.call(obj);
				if(callResult != null){
					result = callResult;
				}
			} catch (Throwable e) {
				//Should never fail!
				Log.log(e);
			}
		}
		return result;
	}

	public void registerListener(ICallbackListener<X> listener) {
		this.listeners.add(listener);
	}

}
