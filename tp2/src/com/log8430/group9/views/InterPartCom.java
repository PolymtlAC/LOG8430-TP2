package com.log8430.group9.views;
/**
 * cette interface permet aux differentes classe necessitant une communication avec d'autres vue d'acceder au elements de communication
 * @author Alexandre
 *
 */
public interface InterPartCom {
	/**
	 * elements de communication accessibles
	 */
	final SharedRessources ressource = SharedRessources.getInstance();
}
